package br.edu.utfpr.mbean.exame;

import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Exame;
import br.edu.utfpr.service.ExameService;

@ManagedBean
@ViewScoped
public class EditarExameMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private ExameService exameService;
	
	private Exame exameSelecionado;
	
	@PostConstruct
	public void init() {
		String idExame = getRequest().getParameter("idExame");
		
		if (StringUtils.isNotEmpty(idExame)) {
			if (new Scanner(idExame).hasNextLong()) {
				this.exameSelecionado = exameService.retornarExame(Long.parseLong(idExame));
			}
			
			if (this.exameSelecionado == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("exame.erro.naoencontrado"), idExame));
				return;
			}
		} else {
			this.exameSelecionado = new Exame();
			this.exameSelecionado.setChkAtivo(Boolean.TRUE);
		}
	}
	
	public String salvarExame() {
		boolean isNew = exameSelecionado.isNew();
		try {
			exameService.salvarExame(exameSelecionado);
			exameSelecionado = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("exame.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("exame.editar.sucesso"), true);
			}
			
			return "/secure/exame/exames.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}

	public Exame getExameSelecionado() {
		return exameSelecionado;
	}

	public void setExameSelecionado(Exame exameSelecionado) {
		this.exameSelecionado = exameSelecionado;
	}

}
