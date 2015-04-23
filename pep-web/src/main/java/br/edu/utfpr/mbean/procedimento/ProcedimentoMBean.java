package br.edu.utfpr.mbean.procedimento;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Procedimento;
import br.edu.utfpr.service.ProcedimentoService;

@ManagedBean
@ViewScoped
public class ProcedimentoMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProcedimentoService procedimentoService;
	
	private List<Procedimento> procedimentoList;
	private String pesquisa;
	
	@PostConstruct
	public void init() {
		this.listarProcedimentos();
	}
	
	public void listarProcedimentos() {
		this.procedimentoList = procedimentoService.retornarProcedimentos(pesquisa, null, null);
	}
	
	public String novoProcedimento() {
		return "/secure/procedimento/editarProcedimento.xhtml?faces-redirect=true";
	}
	
	public String editarProcedimento(Long idProcedimento) {
		return "/secure/procedimento/editarProcedimento.xhtml?faces-redirect=true&idProcedimento=" + idProcedimento;
	}
	
	public List<Procedimento> getProcedimentoList() {
		return procedimentoList;
	}

	public void setProcedimentoList(List<Procedimento> procedimentoList) {
		this.procedimentoList = procedimentoList;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
