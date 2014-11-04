package br.edu.utfpr.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Session;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.constants.ContentType;
import br.edu.utfpr.constants.MessageName;
import br.edu.utfpr.dao.TokenCadastroDao;
import br.edu.utfpr.dao.UsuarioDao;
import br.edu.utfpr.email.Email;
import br.edu.utfpr.email.EmailHandler;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.TokenCadastro;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.utils.PasswordHandler;

@Named
@Stateless
public class UsuarioService {

	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private TokenCadastroDao tokenCadastroDao;
	
	@Resource(lookup = "java:jboss/mail/pep")
	private Session session;

	public List<Usuario> retornarUsuarios() {
		return usuarioDao.retornarUsuarios();
	}
	
	public Usuario retornarUsuario(Long id) {
		Usuario usuario = usuarioDao.getById(id);
		usuario.getPerfisUsuario().size();
		return usuario;
	}
	
	public void removerUsuario(Usuario usuario) {
		usuarioDao.removeById(usuario.getId());
	}
	
	public Usuario retornarUsuarioPorEmail(String email) {
		try {
			return usuarioDao.retornarUsuarioPorEmail(email);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarUsuario(Usuario usuario) throws AppException {
		String email = StringUtils.trimToNull(usuario.getEmail());
		usuario.setEmail(email);
		
		Usuario usuarioBase = this.retornarUsuarioPorEmail(email);
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
			
			//inicializa a qtde de acessos errados com 0
			usuario.setQtdeAcessosErrados((short)0);
		}
		
		usuarioDao.save(usuario);
	}
	
	public Usuario autenticarUsuario(String username, String password) throws AppException {
		try {
			Usuario usuario = usuarioDao.retornarUsuarioPorEmail(username);
			
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
				usuario.getNome(), usuario.getEmail(), senha);

		email.setMensagem(msg);

		emailHandler.sendEmail(email);
	}
	
	public void reiniciarSenha(String username) throws AppException {
		Usuario usuario = null;
		try {
			usuario = usuarioDao.retornarUsuarioPorEmail(username);
		} catch (NoResultException e) {
			throw new AppException("login.solicitaralteracaosenha.error.usuarionaoexiste", username);
		}
		
		//validade do token eh de 01 dia
		Calendar dataValidade = Calendar.getInstance();
		dataValidade.add(Calendar.DAY_OF_MONTH, -1);

		//inativa token anterior se existe
		try {
			TokenCadastro tokenCadastro = tokenCadastroDao.retornarTokenAtivoPorUsuario(username, dataValidade.getTime());
			tokenCadastro.setChkAtivo(Boolean.FALSE);
			tokenCadastroDao.save(tokenCadastro);
		} catch (NoResultException e) {}
		
		/* Criacao do Hash - Unique Identifier */
		UUID hashUUID = UUID.randomUUID();
		String token = hashUUID.toString().replaceAll("-", "").trim();
		
		TokenCadastro tokenCadastro = new TokenCadastro();
		tokenCadastro.setTxEmailUsuario(username);
		tokenCadastro.setChkAtivo(Boolean.TRUE);
		tokenCadastro.setTxTokenValue(token);
		tokenCadastro.setDtGeracao(new Date());
		tokenCadastroDao.save(tokenCadastro);
		
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
				usuario.getNome(),
				bundle.getString("solicitaralteracaosenha.email.link.solicitacaocadastro"), token);

		email.setMensagem(msg);

		emailHandler.sendEmail(email);
	}
}
