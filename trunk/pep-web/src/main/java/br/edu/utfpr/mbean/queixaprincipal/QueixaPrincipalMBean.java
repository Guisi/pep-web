package br.edu.utfpr.mbean.queixaprincipal;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.QueixaPrincipal;
import br.edu.utfpr.service.QueixaPrincipalService;

@ManagedBean
@ViewScoped
public class QueixaPrincipalMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private QueixaPrincipalService queixaPrincipalService;
	
	private List<QueixaPrincipal> queixaPrincipalList;
	private String pesquisa;
	
	@PostConstruct
	public void init() {
		this.listarQueixasPrincipais();
	}
	
	public void listarQueixasPrincipais() {
		this.queixaPrincipalList = queixaPrincipalService.retornarQueixasPrincipais(pesquisa, null);
	}
	
	public String novaQueixaPrincipal() {
		return "/secure/queixaprincipal/editarQueixaPrincipal.xhtml?faces-redirect=true";
	}
	
	public String editarQueixaPrincipal(Long idQueixaPrincipal) {
		return "/secure/queixaprincipal/editarQueixaPrincipal.xhtml?faces-redirect=true&idQueixaPrincipal=" + idQueixaPrincipal;
	}
	
	public List<QueixaPrincipal> getQueixaPrincipalList() {
		return queixaPrincipalList;
	}

	public void setQueixaPrincipalList(List<QueixaPrincipal> queixaPrincipalList) {
		this.queixaPrincipalList = queixaPrincipalList;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
