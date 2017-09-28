package br.edu.utfpr.mbean.convenio;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Convenio;
import br.edu.utfpr.service.ConvenioService;

@ManagedBean
@ViewScoped
public class ConvenioMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private ConvenioService convenioService;
	
	private List<Convenio> convenioList;
	private Convenio convenioSelecionado;
	
	@PostConstruct
	public void init() {
		this.listarConvenios();
	}
	
	private void listarConvenios() {
		this.convenioList = convenioService.retornarConvenios();
	}
	
	public String novoConvenio() {
		return "/secure/convenio/editarConvenio.xhtml?faces-redirect=true";
	}
	
	public String editarConvenio(Long idConvenio) {
		return "/secure/convenio/editarConvenio.xhtml?faces-redirect=true&idConvenio=" + idConvenio;
	}
	
	public void removerConvenio() {
		try {
			convenioService.removerConvenio(convenioSelecionado);
			convenioSelecionado = null;
			
			this.listarConvenios();
			addInfoMessage(getMsgs().getString("convenio.remover.sucesso"));
		} catch (AppException e) {
			addressException(e);
		}
	}

	public List<Convenio> getConvenioList() {
		return convenioList;
	}

	public void setConvenioList(List<Convenio> convenioList) {
		this.convenioList = convenioList;
	}

	public Convenio getConvenioSelecionado() {
		return convenioSelecionado;
	}

	public void setConvenioSelecionado(Convenio convenioSelecionado) {
		this.convenioSelecionado = convenioSelecionado;
	}

}
