package br.edu.utfpr.mbean.vacina;

import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Vacina;
import br.edu.utfpr.service.VacinaService;

@ManagedBean
@ViewScoped
public class EditarVacinaMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private VacinaService vacinaService;
	
	private Vacina vacinaSelecionada;
	
	@PostConstruct
	public void init() {
		String idVacina = getRequest().getParameter("idVacina");
		
		if (StringUtils.isNotEmpty(idVacina)) {
			if (new Scanner(idVacina).hasNextLong()) {
				this.vacinaSelecionada = vacinaService.retornarVacina(Long.parseLong(idVacina));
			}
			
			if (this.vacinaSelecionada == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("vacina.erro.naoencontrado"), idVacina));
				return;
			}
		} else {
			this.vacinaSelecionada = new Vacina();
			this.vacinaSelecionada.setChkAtivo(Boolean.TRUE);
		}
	}
	
	public String salvarVacina() {
		boolean isNew = vacinaSelecionada.isNew();
		try {
			vacinaService.salvarVacina(vacinaSelecionada);
			vacinaSelecionada = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("vacina.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("vacina.editar.sucesso"), true);
			}
			
			return "/secure/vacina/vacinas.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}

	public Vacina getVacinaSelecionada() {
		return vacinaSelecionada;
	}

	public void setVacinaSelecionada(Vacina vacinaSelecionada) {
		this.vacinaSelecionada = vacinaSelecionada;
	}

}
