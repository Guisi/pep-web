package br.edu.utfpr.mbean.habito;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Habito;
import br.edu.utfpr.service.HabitoService;

@ManagedBean
@ViewScoped
public class HabitoMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private HabitoService habitoService;
	
	private List<Habito> habitoList;
	private String pesquisa;
	
	@PostConstruct
	public void init() {
		this.listarHabitos();
	}
	
	public void listarHabitos() {
		this.habitoList = habitoService.retornarHabitos(pesquisa, null);
	}
	
	public String novoHabito() {
		return "/secure/habito/editarHabito.xhtml?faces-redirect=true";
	}
	
	public String editarHabito(Long idHabito) {
		return "/secure/habito/editarHabito.xhtml?faces-redirect=true&idHabito=" + idHabito;
	}
	
	public List<Habito> getHabitoList() {
		return habitoList;
	}

	public void setHabitoList(List<Habito> habitoList) {
		this.habitoList = habitoList;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
