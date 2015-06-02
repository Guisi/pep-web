package br.edu.utfpr.mbean.vacina;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Vacina;
import br.edu.utfpr.service.VacinaService;

@ManagedBean
@ViewScoped
public class VacinaMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private VacinaService vacinaService;
	
	private List<Vacina> vacinaList;
	private String pesquisa;
	
	@PostConstruct
	public void init() {
		this.listarVacinas();
	}
	
	public void listarVacinas() {
		this.vacinaList = vacinaService.retornarVacinas(pesquisa, null);
	}
	
	public String novaVacina() {
		return "/secure/vacina/editarVacina.xhtml?faces-redirect=true";
	}
	
	public String editarVacina(Long idVacina) {
		return "/secure/vacina/editarVacina.xhtml?faces-redirect=true&idVacina=" + idVacina;
	}
	
	public List<Vacina> getVacinaList() {
		return vacinaList;
	}

	public void setVacinaList(List<Vacina> vacinaList) {
		this.vacinaList = vacinaList;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
