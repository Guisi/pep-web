package br.edu.utfpr.mbean.especialidade;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Especialidade;
import br.edu.utfpr.service.EspecialidadeService;

@ManagedBean
@ViewScoped
public class EspecialidadeMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private EspecialidadeService especialidadeService;
	
	private List<Especialidade> especialidadeList;
	private Especialidade especialidadeSelecionada;
	
	@PostConstruct
	public void init() {
		this.listarEspecialidades();
	}
	
	private void listarEspecialidades() {
		this.especialidadeList = especialidadeService.retornarEspecialidades();
	}
	
	public String novaEspecialidade() {
		return "/secure/especialidade/editarEspecialidade.xhtml?faces-redirect=true";
	}
	
	public String editarEspecialidade(Long idEspecialidade) {
		return "/secure/especialidade/editarEspecialidade.xhtml?faces-redirect=true&idEspecialidade=" + idEspecialidade;
	}
	
	public void removerEspecialidade() {
		try {
			especialidadeService.removerEspecialidade(especialidadeSelecionada);
			especialidadeSelecionada = null;
			
			this.listarEspecialidades();
			addInfoMessage(getMsgs().getString("especialidade.remover.sucesso"));
		} catch (AppException e) {
			addressException(e);
		}
	}

	public List<Especialidade> getEspecialidadeList() {
		return especialidadeList;
	}

	public void setEspecialidadeList(List<Especialidade> especialidadeList) {
		this.especialidadeList = especialidadeList;
	}

	public Especialidade getEspecialidadeSelecionada() {
		return especialidadeSelecionada;
	}

	public void setEspecialidadeSelecionada(Especialidade especialidadeSelecionada) {
		this.especialidadeSelecionada = especialidadeSelecionada;
	}

}
