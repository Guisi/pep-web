package br.edu.utfpr.email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import br.edu.utfpr.exception.EmailException;

/**
 * Handler para enviar emails.
 * 
 * @author douglas.guisi
 */
public class EmailHandler {
	
	/**
	 * Sessao para envio e recebimento de emails
	 */
	private Session mailSession;
	
	/**
	 * Construtor com envio da sessao de email.
	 * <p>
	 * Este construtor pode ser utilizado quando e utilizado o recurso JNDI do Application Server. Neste caso, deve-se
	 * recuperar a sessao do servidor para reaproveitar as configuracoes de acesso a conta de email.
	 * </p>
	 * 
	 * @param session
	 *            <p>
	 *            Sessao resgatada do Application server.
	 *            </p>
	 */
	public EmailHandler(Session session) {
		this.mailSession = session;
	}

	/**
	 * Metodo para envio de emails no formato de texto ou em HTML.
	 * 
	 * @param email
	 *            <p>
	 *            Objeto com os dados do email que devera ser encaminhado.
	 *            </p>
	 * @throws EmailException
	 *             <p>
	 *             Excecao gerada no envio do email.
	 *             </p>
	 */
	public boolean sendEmail(Email email) throws EmailException {
		Message msg;
		Address remetente;
		Multipart multiPart;
		MimeBodyPart mimeBodyPart;
		Address[] destinatarios;
		Address[] destinatariosCC;
		Address[] destinatariosCCO;

		try {
			// Cria a MimeMessage que sera enviada
			msg = new MimeMessage(mailSession);

			// Assunto
			msg.setSubject(email.getAssunto());

			/*
			 * Destinatarios da Mensagem --> Gera array de Address
			 */
			if (email.getDestinatarios() == null || email.getDestinatarios().size() == 0) {
				throw new EmailException("email.code.error.1101");
			} else {
				destinatarios = new InternetAddress[email.getDestinatarios().size()];

				for (int i = 0; i < email.getDestinatarios().size(); i++) {
					Address address = new InternetAddress(email.getDestinatarios().get(i));
					destinatarios[i] = address;
				}
			}

			// Adiciona na mensagem os destinatarios
			msg.setRecipients(RecipientType.TO, destinatarios);

			/*
			 * Destinatarios com copia --> Se informado CC, gera array de AddressCC
			 */
			if (email.getDestinatariosCC() != null && email.getDestinatariosCC().size() > 0) {
				destinatariosCC = new InternetAddress[email.getDestinatariosCC().size()];
				for (int i = 0; i < email.getDestinatariosCC().size(); i++) {
					Address address = new InternetAddress(email.getDestinatariosCC().get(i));
					destinatariosCC[i] = address;
				}
				msg.setRecipients(RecipientType.CC, destinatariosCC);
			}

			/*
			 * Destinatarios com copia Oculta - Blind Copy --> Se informado CCO, gera array de AddressCCO
			 */
			if (email.getDestinatariosCCO() != null && email.getDestinatariosCCO().size() > 0) {
				destinatariosCCO = new InternetAddress[email.getDestinatariosCCO().size()];
				for (int i = 0; i < email.getDestinatariosCCO().size(); i++) {
					Address address = new InternetAddress(email.getDestinatariosCCO().get(i));
					destinatariosCCO[i] = address;
				}
				msg.setRecipients(RecipientType.BCC, destinatariosCCO);
			}

			/* Email de retorno - Enviado por */
			if (email.getRemetentes() == null || email.getRemetentes().size() == 0) {
				String sessionRemetente = null;
				
				if (mailSession != null) {
					sessionRemetente = mailSession.getProperty("mail.from");
				}
				
				// Se nao consta na session, acusar erro
				if (sessionRemetente == null || sessionRemetente.isEmpty()) {
					throw new EmailException("email.code.error.1102");
				} else { // Caso contrario  
					email.addRemetente(sessionRemetente);
				}
			}

			remetente = new InternetAddress(email.getRemetentes().get(0));
			msg.setFrom(remetente);

			/*
			 * Conteudo do Email
			 */
			mimeBodyPart = new MimeBodyPart();
			
			if (email.getContentType() == null || email.getContentType().isEmpty()) {
				throw new EmailException("email.code.error.1103");
			}
			
			mimeBodyPart.setContent(email.getMensagem(), email.getContentType());
			
			// Adiciona o conteudo do email no multipart
			multiPart = new MimeMultipart();
			multiPart.addBodyPart(mimeBodyPart);
			
			// Se possui anexos
			if (email.getAnexos() != null && !email.getAnexos().isEmpty()) {
				for (Anexo anexo : email.getAnexos()) {
					MimeBodyPart attachment = new MimeBodyPart();
					DataSource ds = new ByteArrayDataSource(anexo.getContent(), anexo.getMimeType());
					
					attachment.setDataHandler(new DataHandler(ds));
					attachment.setFileName(anexo.getFullFilename());
					multiPart.addBodyPart(attachment);
				}
			}
			
			msg.setContent(multiPart);
			
			/* Envia e-mail */
			Transport.send(msg);

			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EmailException("email.code.error.1104");
		}
	}
}
