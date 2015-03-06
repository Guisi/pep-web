package br.edu.utfpr.mbean.doenca;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.service.DoencaService;

@ManagedBean
@ViewScoped
public class DoencaMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private DoencaService doencaService;
	
	private List<Doenca> doencaList;
	private String pesquisa;
	
	@PostConstruct
	public void init() {
		this.listarDoencas();
	}
	
	public void listarDoencas() {
		this.doencaList = doencaService.retornarDoencas(pesquisa, null);
	}
	
	public String novaDoenca() {
		return "/secure/doenca/editarDoenca.xhtml?faces-redirect=true";
	}
	
	public String editarDoenca(Long idDoenca) {
		return "/secure/doenca/editarDoenca.xhtml?faces-redirect=true&idDoenca=" + idDoenca;
	}
	
	public List<Doenca> getDoencaList() {
		return doencaList;
	}

	public void setDoencaList(List<Doenca> doencaList) {
		this.doencaList = doencaList;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
