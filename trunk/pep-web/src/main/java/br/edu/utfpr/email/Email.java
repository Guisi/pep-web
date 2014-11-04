package br.edu.utfpr.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Flags.Flag;

/**
 * Bean para representar e fazer as operacoes de Email.
 * 
 * @author douglas.guisi
 */
public class Email {
	
	/**
	 * Id da mensagem na Pasta de mensagens
	 */
	private int idEmail;
	/**
	 * Assunto do email
	 */
	private String assunto;
	/**
	 * Tipo do conteudo do body do email. Exemplo: text/plain
	 */
	private String contentType;
	/**
	 * Conteudo da mensagem de email -> body
	 */
	private String mensagem;
	/**
	 * Data de envio do email
	 */
	private Date dataEnvio;
	/**
	 * Data de recebimento do email
	 */
	private Date dataRecebimento;
	/**
	 * Lista de remetentes.
	 * 		<p> 
	 * 			É necessario informar o remetente
	 * 			somente quando o session nao for 
	 * 			repassado do Application Server. 
	 * 		</p>
	 */
	private List<String> remetentes;
	/**
	 * Lista de destinatarios --> to
	 */
	private List<String> destinatarios;
	/**
	 * Lista de destinatarios com copia --> cc
	 */
	private List<String> destinatariosCC;
	/**
	 * Lista de destinatarios com copia oculta --> cc0
	 */
	private List<String> destinatariosCCO;
	/**
	 * Lista de anexos do email
	 */
	private List<Anexo> anexos;
	/**
	 * Flag do e-mail - exemplo: ANSWERED, DELETED, DRAFT, etc.
	 */
	private Flags statusMensagem;


	/**
	 * Getter de assunto
	 * @return o valor de assunto
	 */
	public String getAssunto() {
		return assunto;
	}

	/**
	 * Getter de idEmail
	 * @return o valor de idEmail
	 */
	public int getIdEmail() {
		return idEmail;
	}

	/**
	 * Setter de idEmail
	 * @param idEmail
	 *             Valor a ser atribuido a idEmail
	 */
	public void setIdEmail(int idEmail) {
		this.idEmail = idEmail;
	}

	/**
	 * Getter de statusMensagem
	 * @return o valor de statusMensagem
	 */
	public Flags getStatusMensagem() {
		return statusMensagem;
	}

	/**
	 * Setter de statusMensagem
	 * @param statusMensagem
	 *             Valor a ser atribuido a statusMensagem
	 */
	public void setStatusMensagem(Flags statusMensagem) {
		this.statusMensagem = statusMensagem;
	}

	/**
	 * Adiciona um novo status para a mensagem de email
	 * @param statusMensagem
	 *             Valor a ser atribuido a statusMensagem
	 */
	public void addStatusMensagem(Flag statusMensagem) {
		this.statusMensagem.add(statusMensagem);
	}
	
	/**
	 * Remove um status da mensagem de e-mail
	 * @param statusMensagem
	 * @return
	 */
	public boolean removeStatusMensagem(Flag statusMensagem) {
		try {
			this.statusMensagem.remove(statusMensagem);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	

	/**
	 * Setter de assunto
	 * @param assunto
	 *             Valor a ser atribuido a assunto
	 */
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	/**
	 * Getter de contentType
	 * @return o valor de contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Setter de contentType
	 * @param contentType
	 *             Valor a ser atribuido a contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Getter de mensagem
	 * @return o valor de mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * Setter de mensagem
	 * @param mensagem
	 *             Valor a ser atribuido a mensagem
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * Getter de dataEnvio
	 * @return o valor de dataEnvio
	 */
	public Date getDataEnvio() {
		return dataEnvio;
	}

	/**
	 * Setter de dataEnvio
	 * @param dataEnvio
	 *             Valor a ser atribuido a dataEnvio
	 */
	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	/**
	 * Getter de dataRecebimento
	 * @return o valor de dataRecebimento
	 */
	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	/**
	 * Setter de dataRecebimento
	 * @param dataRecebimento
	 *             Valor a ser atribuido a dataRecebimento
	 */
	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	/**
	 * Getter de remetentes
	 * @return o valor de remetentes
	 */
	public List<String> getRemetentes() {
		return remetentes;
	}

	/**
	 * Setter de remetentes
	 * @param remetentes
	 *             Valor a ser atribuido a remetentes
	 */
	public void setRemetentes(List<String> remetentes) {
		this.remetentes = remetentes;
	}

	/**
	 * Getter de destinatarios
	 * @return o valor de destinatarios
	 */
	public List<String> getDestinatarios() {
		return destinatarios;
	}

	/**
	 * Setter de destinatarios
	 * @param destinatarios
	 *             Valor a ser atribuido a destinatarios
	 */
	public void setDestinatarios(List<String> destinatarios) {
		this.destinatarios = destinatarios;
	}

	/**
	 * Getter de anexos
	 * @return o valor de anexos
	 */
	public List<Anexo> getAnexos() {
		return anexos;
	}

	/**
	 * Setter de anexos
	 * @param anexos
	 *             Valor a ser atribuido a anexos
	 */
	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	/**
	 * Adiciona um destinario na lista de destinatarios.
	 * 
	 * @param address
	 */
	public void addDestinatario(String address) {
		if (destinatarios == null) {
			destinatarios = new ArrayList<String>();
		}

		destinatarios.add(address);
	}

	/**
	 * Limpa a lista de destinatarios
	 */
	public void clearDestinatarios() {
		if (destinatarios != null) {
			destinatarios.clear();
		}
	}

	/**
	 * Getter de destinatariosCC
	 * @return o valor de destinatariosCC
	 */
	public List<String> getDestinatariosCC() {
		return destinatariosCC;
	}

	/**
	 * Setter de destinatariosCC
	 * @param destinatariosCC
	 *             Valor a ser atribuido a destinatariosCC
	 */
	public void setDestinatariosCC(List<String> destinatariosCC) {
		this.destinatariosCC = destinatariosCC;
	}

	/**
	 * Adiciona um destinario na lista de destinatarios.
	 * 
	 * @param address
	 */
	public void addDestinatarioCC(String address) {
		if (destinatariosCC == null) {
			destinatariosCC = new ArrayList<String>();
		}

		destinatariosCC.add(address);
	}

	/**
	 * Limpa a lista de destinatarios
	 */
	public void clearDestinatariosCC() {
		if (destinatariosCC != null) {
			destinatariosCC.clear();
		}
	}
	
	/**
	 * Getter de destinatariosCCO
	 * @return o valor de destinatariosCCO
	 */
	public List<String> getDestinatariosCCO() {
		return destinatariosCCO;
	}

	/**
	 * Setter de destinatariosCCO
	 * @param destinatariosCCO
	 *             Valor a ser atribuido a destinatariosCCO
	 */
	public void setDestinatariosCCO(List<String> destinatariosCCO) {
		this.destinatariosCCO = destinatariosCCO;
	}

	/**
	 * Adiciona um destinario na lista de destinatarios.
	 * 
	 * @param address
	 */
	public void addDestinatarioCCO(String address) {
		if (destinatariosCCO == null) {
			destinatariosCCO = new ArrayList<String>();
		}

		destinatariosCCO.add(address);
	}

	/**
	 * Limpa a lista de destinatarios
	 */
	public void clearDestinatariosCCO() {
		if (destinatariosCCO != null) {
			destinatariosCCO.clear();
		}
	}
	
	/**
	 * Adiciona o email do Remetente
	 * 
	 * @param address
	 */
	public void addRemetente(String address) {
		if (remetentes == null) {
			remetentes = new ArrayList<String>();
		}

		remetentes.add(address);
	}

	/**
	 * Limpa a lista de remetentes
	 */
	public void clearRemetentes() {
		if (remetentes != null) {
			remetentes.clear();
		}
	}

	/**
	 * Adiciona um novo anexo ao email
	 * 
	 * @param anexo
	 */
	public void addAnexo(Anexo anexo) {
		if (anexos == null) {
			anexos = new ArrayList<Anexo>();
		}
		
		anexos.add(anexo);
	}
	
	/**
	 * Limpa a lista de anexos
	 */
	public void clearAnexos(){
		anexos.clear();
	}
	
}
