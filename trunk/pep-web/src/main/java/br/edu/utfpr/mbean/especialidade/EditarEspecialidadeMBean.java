package br.edu.utfpr.mbean.especialidade;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Especialidade;
import br.edu.utfpr.service.EspecialidadeService;

@ManagedBean
@ViewScoped
public class EditarEspecialidadeMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private EspecialidadeService especialidadeService;
	
	private Especialidade especialidadeSelecionada;
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		String idEspecialidade = request.getParameter("idEspecialidade");
		
		if (StringUtils.isNotEmpty(idEspecialidade)) {
			if (new Scanner(idEspecialidade).hasNextLong()) {
				this.especialidadeSelecionada = especialidadeService.retornarEspecialidade(Long.parseLong(idEspecialidade));
			}
			
			if (this.especialidadeSelecionada == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("especialidade.erro.naoencontrada"), idEspecialidade));
				return;
			}
		} else {
			this.especialidadeSelecionada = new Especialidade();
		}
	}
	
	public String salvarEspecialidade() {
		boolean isNew = especialidadeSelecionada.isNew();
		try {
			especialidadeService.salvarEspecialidade(especialidadeSelecionada);
			especialidadeSelecionada = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("especialidade.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("especialidade.editar.sucesso"), true);
			}
			
			return "/secure/especialidade/especialidades.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}

	public Especialidade getEspecialidadeSelecionada() {
		return especialidadeSelecionada;
	}

	public void setEspecialidadeSelecionada(Especialidade especialidadeSelecionada) {
		this.especialidadeSelecionada = especialidadeSelecionada;
	}

}
