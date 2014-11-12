package br.edu.utfpr.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Session;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;

import br.edu.utfpr.authentication.PepUser;
import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.constants.ContentType;
import br.edu.utfpr.constants.MessageName;
import br.edu.utfpr.dao.UsuarioDao;
import br.edu.utfpr.email.Email;
import br.edu.utfpr.email.EmailHandler;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.TokenCadastro;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.utils.PasswordHandler;
import br.edu.utfpr.utils.UserThreadLocal;

@Named
@Stateless
public class UsuarioService {

	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private TokenCadastroService tokenCadastroService;
	
	@Resource(lookup = "java:jboss/mail/pep")
	private Session session;

	public List<Usuario> retornarUsuarios(String textoPesquisa, Boolean chkAtivo) {
		return usuarioDao.retornarUsuarios(textoPesquisa, chkAtivo);
	}
	
	public Usuario retornarUsuario(Long id) {
		Usuario usuario = usuarioDao.getById(id);
		usuario.getPerfisUsuario().size();
		return usuario;
	}
	
	public void inativarUsuario(PepUser usuarioLogado, Usuario usuario) {
		UserThreadLocal.getThreadLocal().set(usuarioLogado.getUsername());
		
		usuario.setChkAtivo(Boolean.FALSE);
		usuarioDao.save(usuario);
	}
	
	public Usuario retornarUsuarioPorEmail(String email, Boolean chkAtivo) {
		try {
			return usuarioDao.retornarUsuarioPorEmail(email, chkAtivo);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarUsuario(PepUser usuarioLogado, Usuario usuario) throws AppException {
		UserThreadLocal.getThreadLocal().set(usuarioLogado.getUsername());
		
		String email = StringUtils.trimToNull(usuario.getEmail());
		usuario.setEmail(email);
		
		Usuario usuarioBase = this.retornarUsuarioPorEmail(email, null);
		if (usuarioBase != null && !usuarioBase.getId().equals(usuario.getId())) {
			throw new AppException("usuario.salvar.erro.emailexistente", email);
		}
		
		if (usuario.isNew()) {
			//se usuario eh novo, define senha e envia por email
			String senha = PasswordHandler.generateRandomPassword();
			this.enviarSenhaPorEmail(usuario, senha);

			//criptografa a senha para guardar na base
			senha = PasswordHandler.encryptPassword(senha);
			usuario.setSenha(senha);
			usuario.setChkSenhaProvisoria(Boolean.TRUE);
			
			//inicializa a qtde de acessos errados com 0
			usuario.setQtdeAcessosErrados((short)0);
			
			//inicializa como ativo
			usuario.setChkAtivo(Boolean.TRUE);
		}
		
		try {
			usuarioDao.save(usuario);
		} catch (PersistenceException e) {
			throw new AppException(e);
		}
	}
	
	public void alterarSenhaUsuario(String username, String senha) throws AppException {
		//valida forca da senha
		if (StringUtils.isBlank(senha)
				|| !GenericValidator.isInRange(senha.length(), Constantes.PASSWD_MIN_LENGHT, Constantes.PASSWD_MAX_LENGHT)
				|| !PasswordHandler.validatePasswordStrength(senha)) {
			throw new AppException("alterarsenha.msg.info.complexidadesenha");
		}
		
		Usuario usuario = this.retornarUsuarioPorEmail(username, Boolean.TRUE);
		
		//criptografa a senha para guardar na base
		senha = PasswordHandler.encryptPassword(senha);
		usuario.setSenha(senha);
		usuario.setChkSenhaProvisoria(Boolean.FALSE);
		
		//volta a qtde de acessos errados para 0
		usuario.setQtdeAcessosErrados((short)0);
		
		usuarioDao.save(usuario);
		
		//inativa o token
		tokenCadastroService.inativarTokensUsuario(username);
	}
	
	public Usuario autenticarUsuario(String username, String password) throws AppException {
		UserThreadLocal.getThreadLocal().set(username);
		
		try {
			Usuario usuario = usuarioDao.retornarUsuarioPorEmail(username, Boolean.TRUE);
			
			if (Constantes.QTDE_MAXIMA_ERROS_ACESSO <= usuario.getQtdeAcessosErrados()) {
				throw new AppException("login.error.bloqueadoporquantidade");
			}
			
			//se errou a senha, incrementa um acesso errado e lanca excecao
			String senhaBase = usuario.getSenha();
			if (!senhaBase.equals(password)) {
				usuario.setQtdeAcessosErrados((short)(usuario.getQtdeAcessosErrados() + 1));
				usuarioDao.save(usuario);
				throw new AppException("login.error.usuariosenhainvalido");
			}
			
			usuario.setQtdeAcessosErrados((short)0);
			usuarioDao.save(usuario);
			
			//se autenticou com sucesso, faz o fetch dos perfis e autorizacoes
			for (Perfil perfil : usuario.getPerfisUsuario()) {
				perfil.getAutorizacoes().size();
			}
			return usuario;
		} catch (NoResultException e) {
			throw new AppException("login.error.usuariosenhainvalido");
		}
	}
	
	private void enviarSenhaPorEmail(Usuario usuario, String senha) throws AppException {
		ResourceBundle bundle = ResourceBundle.getBundle(MessageName.MESSAGES.value(), Constantes.LOCALE_PT_BR);
		
		EmailHandler emailHandler = new EmailHandler(session);
		
		Email email = new Email();
		email.setAssunto(bundle.getString("usuario.emailsenhaprovisoria.assunto"));
		email.setContentType(ContentType.TEXT_HTML.value());
		email.setDataEnvio(Calendar.getInstance().getTime());

		// Destinatario email
		List<String> destinatariosEmail = new ArrayList<>();
		destinatariosEmail.add(usuario.getEmail());
		email.setDestinatarios(destinatariosEmail);

		// Remetente Email
		/*List<String> remetentesEmail = new ArrayList<>();
		remetentesEmail.add(bundle.getString("usuariobean.reiniciarsenha.email.sender"));
		email.setRemetentes(remetentesEmail);*/

		// Mensagem Solicitacao de Cadastro
		String msg = MessageFormat.format(bundle.getString("usuario.emailsenhaprovisoria.mensagem"),
				usuario.getNomeFantasia(), usuario.getEmail(), senha);

		email.setMensagem(msg);

		emailHandler.sendEmail(email);
	}
	
	public void reiniciarSenha(String username) throws AppException {
		Usuario usuario = null;
		try {
			usuario = usuarioDao.retornarUsuarioPorEmail(username, Boolean.TRUE);
		} catch (NoResultException e) {
			throw new AppException("login.solicitaralteracaosenha.error.usuarionaoexiste", username);
		}
		
		//Cria o token para o usuario
		TokenCadastro tokenCadastro = tokenCadastroService.criarTokenUsuario(username);
		
		//Envia email com link do token
		ResourceBundle bundle = ResourceBundle.getBundle(MessageName.MESSAGES.value(), Constantes.LOCALE_PT_BR);
		
		EmailHandler emailHandler = new EmailHandler(session);
		
		Email email = new Email();
		email.setAssunto(bundle.getString("solicitaralteracaosenha.email.assunto"));
		email.setContentType(ContentType.TEXT_HTML.value());
		email.setDataEnvio(Calendar.getInstance().getTime());

		// Destinatario email
		List<String> destinatariosEmail = new ArrayList<>();
		destinatariosEmail.add(usuario.getEmail());
		email.setDestinatarios(destinatariosEmail);

		// Remetente Email
		/*List<String> remetentesEmail = new ArrayList<>();
		remetentesEmail.add(bundle.getString("usuariobean.reiniciarsenha.email.sender"));
		email.setRemetentes(remetentesEmail);*/

		// Mensagem Solicitacao de Cadastro
		String msg = MessageFormat.format(bundle.getString("solicitaralteracaosenha.email.mensagem"),
				usuario.getNomeFantasia(),
				bundle.getString("solicitaralteracaosenha.email.link.solicitacaocadastro"), tokenCadastro.getTxTokenValue());

		email.setMensagem(msg);

		emailHandler.sendEmail(email);
	}
}
