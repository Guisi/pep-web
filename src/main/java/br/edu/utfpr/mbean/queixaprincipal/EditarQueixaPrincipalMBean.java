package br.edu.utfpr.mbean.queixaprincipal;

import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.QueixaPrincipal;
import br.edu.utfpr.service.QueixaPrincipalService;

@ManagedBean
@ViewScoped
public class EditarQueixaPrincipalMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private QueixaPrincipalService queixaPrincipalService;
	
	private QueixaPrincipal queixaPrincipalSelecionada;
	
	@PostConstruct
	public void init() {
		String idQueixaPrincipal = getRequest().getParameter("idQueixaPrincipal");
		
		if (StringUtils.isNotEmpty(idQueixaPrincipal)) {
			if (new Scanner(idQueixaPrincipal).hasNextLong()) {
				this.queixaPrincipalSelecionada = queixaPrincipalService.retornarQueixaPrincipal(Long.parseLong(idQueixaPrincipal));
			}
			
			if (this.queixaPrincipalSelecionada == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("queixaprincipal.erro.naoencontrada"), idQueixaPrincipal));
				return;
			}
		} else {
			this.queixaPrincipalSelecionada = new QueixaPrincipal();
			this.queixaPrincipalSelecionada.setChkAtivo(Boolean.TRUE);
		}
	}
	
	public String salvarQueixaPrincipal() {
		boolean isNew = queixaPrincipalSelecionada.isNew();
		try {
			queixaPrincipalService.salvarQueixaPrincipal(queixaPrincipalSelecionada);
			queixaPrincipalSelecionada = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("queixaprincipal.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("queixaprincipal.editar.sucesso"), true);
			}
			
			return "/secure/queixaprincipal/queixasprincipais.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}

	public QueixaPrincipal getQueixaPrincipalSelecionada() {
		return queixaPrincipalSelecionada;
	}

	public void setQueixaPrincipalSelecionada(QueixaPrincipal queixaPrincipalSelecionada) {
		this.queixaPrincipalSelecionada = queixaPrincipalSelecionada;
	}

}
