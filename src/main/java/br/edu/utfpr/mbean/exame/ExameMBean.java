package br.edu.utfpr.mbean.exame;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Exame;
import br.edu.utfpr.service.ExameService;

@ManagedBean
@ViewScoped
public class ExameMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private ExameService exameService;
	
	private List<Exame> exameList;
	private String pesquisa;
	
	@PostConstruct
	public void init() {
		this.listarExames();
	}
	
	public void listarExames() {
		this.exameList = exameService.retornarExames(pesquisa, null);
	}
	
	public String novoExame() {
		return "/secure/exame/editarExame.xhtml?faces-redirect=true";
	}
	
	public String editarExame(Long idExame) {
		return "/secure/exame/editarExame.xhtml?faces-redirect=true&idExame=" + idExame;
	}
	
	public List<Exame> getExameList() {
		return exameList;
	}

	public void setExameList(List<Exame> exameList) {
		this.exameList = exameList;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
