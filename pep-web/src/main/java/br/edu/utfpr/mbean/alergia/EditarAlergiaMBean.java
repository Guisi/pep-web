package br.edu.utfpr.mbean.alergia;

import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Alergia;
import br.edu.utfpr.service.AlergiaService;

@ManagedBean
@ViewScoped
public class EditarAlergiaMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlergiaService alergiaService;
	
	private Alergia alergiaSelecionada;
	
	@PostConstruct
	public void init() {
		String idAlergia = getRequest().getParameter("idAlergia");
		
		if (StringUtils.isNotEmpty(idAlergia)) {
			if (new Scanner(idAlergia).hasNextLong()) {
				this.alergiaSelecionada = alergiaService.retornarAlergia(Long.parseLong(idAlergia));
			}
			
			if (this.alergiaSelecionada == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("alergia.erro.naoencontrado"), idAlergia));
				return;
			}
		} else {
			this.alergiaSelecionada = new Alergia();
			this.alergiaSelecionada.setChkAtivo(Boolean.TRUE);
		}
	}
	
	public String salvarAlergia() {
		boolean isNew = alergiaSelecionada.isNew();
		try {
			alergiaService.salvarAlergia(alergiaSelecionada);
			alergiaSelecionada = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("alergia.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("alergia.editar.sucesso"), true);
			}
			
			return "/secure/alergia/alergias.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}

	public Alergia getAlergiaSelecionada() {
		return alergiaSelecionada;
	}

	public void setAlergiaSelecionada(Alergia alergiaSelecionada) {
		this.alergiaSelecionada = alergiaSelecionada;
	}

}
