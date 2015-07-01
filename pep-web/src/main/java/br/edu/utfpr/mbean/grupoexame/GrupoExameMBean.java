package br.edu.utfpr.mbean.grupoexame;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.GrupoExame;
import br.edu.utfpr.service.GrupoExameService;

@ManagedBean
@ViewScoped
public class GrupoExameMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private GrupoExameService grupoExameService;
	
	private List<GrupoExame> grupoExameList;
	private String pesquisa;
	
	@PostConstruct
	public void init() {
		this.listarGruposExames();
	}
	
	public void listarGruposExames() {
		this.grupoExameList = grupoExameService.retornarGruposExames(pesquisa, null, Boolean.FALSE);
	}
	
	public String novoGrupoExame() {
		return "/secure/grupoexame/editarGrupoExame.xhtml?faces-redirect=true";
	}
	
	public String editarGrupoExame(Long idGrupoExame) {
		return "/secure/grupoexame/editarGrupoExame.xhtml?faces-redirect=true&idGrupoExame=" + idGrupoExame;
	}
	
	public List<GrupoExame> getGrupoExameList() {
		return grupoExameList;
	}

	public void setGrupoExameList(List<GrupoExame> grupoExameList) {
		this.grupoExameList = grupoExameList;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
