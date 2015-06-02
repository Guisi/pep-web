package br.edu.utfpr.mbean.alergia;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Alergia;
import br.edu.utfpr.service.AlergiaService;

@ManagedBean
@ViewScoped
public class AlergiaMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlergiaService alergiaService;
	
	private List<Alergia> alergiaList;
	private String pesquisa;
	
	@PostConstruct
	public void init() {
		this.listarAlergias();
	}
	
	public void listarAlergias() {
		this.alergiaList = alergiaService.retornarAlergias(pesquisa, null);
	}
	
	public String novaAlergia() {
		return "/secure/alergia/editarAlergia.xhtml?faces-redirect=true";
	}
	
	public String editarAlergia(Long idAlergia) {
		return "/secure/alergia/editarAlergia.xhtml?faces-redirect=true&idAlergia=" + idAlergia;
	}
	
	public List<Alergia> getAlergiaList() {
		return alergiaList;
	}

	public void setAlergiaList(List<Alergia> AlergiaList) {
		this.alergiaList = AlergiaList;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
