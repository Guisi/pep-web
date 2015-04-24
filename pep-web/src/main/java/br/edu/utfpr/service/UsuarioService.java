package br.edu.utfpr.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import br.edu.utfpr.constants.PerfilEnum;
import br.edu.utfpr.dao.UsuarioDao;
import br.edu.utfpr.email.Email;
import br.edu.utfpr.email.EmailHandler;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.AlteracaoAtributo;
import br.edu.utfpr.model.Auditoria;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.TokenCadastro;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.utils.AtributoAuditoria;
import br.edu.utfpr.utils.AuditoriaUtils;
import br.edu.utfpr.utils.PasswordHandler;
import br.edu.utfpr.utils.UserThreadLocal;

@Named
@Stateless
public class UsuarioService {
	
	private static Logger logger = Logger.getLogger(UsuarioService.class.getName()); 

	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private TokenCadastroService tokenCadastroService;
	
	//@Resource(lookup = "java:jboss/mail/pep")
	private Session session;

	public List<Usuario> retornarUsuarios(String textoPesquisa, Boolean chkAtivo) {
		return this.retornarUsuarios(textoPesquisa, chkAtivo, null);
	}
	
	public List<Usuario> retornarUsuarios(String textoPesquisa, Boolean chkAtivo, PerfilEnum perfil) {
		return usuarioDao.retornarUsuarios(textoPesquisa, chkAtivo, perfil);
	}
	
	public Usuario retornarUsuario(Long id) {
		try {
			return usuarioDao.retornarUsuarioPorId(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void inativarUsuario(PepUser usuarioLogado, Usuario usuario) {
		UserThreadLocal.getThreadLocal().set(usuarioLogado.getIdUsuario());
		
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
		UserThreadLocal.getThreadLocal().set(usuarioLogado.getIdUsuario());
		
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
			
			logger.log(Level.INFO, "Usuario " + username + " autenticado na aplicacao.");
			
			return usuario;
		} catch (NoResultException e) {
			logger.log(Level.WARNING, "Usuario ou senha invalida na autenticacao do usuario " + username);
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

		// Mensagem Solicitacao de Cadastro
		String msg = MessageFormat.format(bundle.getString("usuario.emailsenhaprovisoria.mensagem"),
				usuario.getNomeFantasia(), usuario.getEmail(), senha);

		email.setMensagem(msg);

		emailHandler.sendEmail(email);
		
		logger.log(Level.INFO, "Enviou email de senha provisoria para usuario " + usuario.getEmail());
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

		// Mensagem Solicitacao de Cadastro
		String msg = MessageFormat.format(bundle.getString("solicitaralteracaosenha.email.mensagem"),
				usuario.getNomeFantasia(),
				bundle.getString("solicitaralteracaosenha.email.link.solicitacaocadastro"), tokenCadastro.getTxTokenValue());

		email.setMensagem(msg);

		emailHandler.sendEmail(email);
		
		logger.log(Level.INFO, "Link para alteracao de senha enviado por email para usuario " + username);
	}
	
	public List<AlteracaoAtributo> retornarHistoricoAlteracaoUsuario(Long idUsuario) {
		List<Auditoria> list = usuarioDao.retornarHistoricoAlteracaoUsuario(Usuario.class, idUsuario);
		
		ResourceBundle bundle = ResourceBundle.getBundle(MessageName.MESSAGES.value(), Constantes.LOCALE_PT_BR);
		
		List<AtributoAuditoria> atributos = new ArrayList<>();
		atributos.add(new AtributoAuditoria("numeroProntuario", bundle.getString("usuario.numeroprontuario")));
		atributos.add(new AtributoAuditoria("nomeCompleto", bundle.getString("usuario.nome")));
		atributos.add(new AtributoAuditoria("nomeFantasia", bundle.getString("usuario.apelido")));
		atributos.add(new AtributoAuditoria("cpf", bundle.getString("usuario.cpf")));
		atributos.add(new AtributoAuditoria("rg", bundle.getString("usuario.rg")));
		atributos.add(new AtributoAuditoria("email", bundle.getString("usuario.email")));
		atributos.add(new AtributoAuditoria("telefone", bundle.getString("usuario.telefone")));
		atributos.add(new AtributoAuditoria("celular", bundle.getString("usuario.celular")));
		atributos.add(new AtributoAuditoria("dataNascimento", bundle.getString("usuario.datanascimento"), Constantes.FORMATO_DATA));
		atributos.add(new AtributoAuditoria("estadoCivil", bundle.getString("usuario.estadocivil")));
		
		Map<String, String> dominiosSexo = new HashMap<String, String>();
		dominiosSexo.put("M", "Masculino");
		dominiosSexo.put("F", "Feminino");
		atributos.add(new AtributoAuditoria("sexo", bundle.getString("usuario.sexo"), dominiosSexo));
		
		atributos.add(new AtributoAuditoria("profissao", bundle.getString("usuario.profissao")));
		String endereco = bundle.getString("usuario.endereco") + " - ";
		atributos.add(new AtributoAuditoria("cep", endereco + bundle.getString("usuario.cep")));
		atributos.add(new AtributoAuditoria("logradouro", endereco + bundle.getString("usuario.logradouro")));
		atributos.add(new AtributoAuditoria("numero", endereco + bundle.getString("usuario.numero")));
		atributos.add(new AtributoAuditoria("complemento", endereco + bundle.getString("usuario.complemento")));
		atributos.add(new AtributoAuditoria("bairro", endereco + bundle.getString("usuario.bairro")));
		atributos.add(new AtributoAuditoria("cidade", endereco + bundle.getString("usuario.cidade")));
		atributos.add(new AtributoAuditoria("uf", endereco + bundle.getString("usuario.estado")));
		
		return AuditoriaUtils.montarListaAlteracoesAtributos(atributos, list);
	}
}
