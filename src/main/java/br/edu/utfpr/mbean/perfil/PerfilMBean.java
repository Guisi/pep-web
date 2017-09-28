package br.edu.utfpr.mbean.perfil;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.service.PerfilService;

@ManagedBean
@ViewScoped
public class PerfilMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilService perfilService;
	
	private List<Perfil> perfilList;
	private Perfil perfilSelecionado;
	
	@PostConstruct
	public void init() {
		this.listarPerfis();
	}
	
	private void listarPerfis() {
		this.perfilList = perfilService.retornarPerfis();
	}
	
	public String novoPerfil() {
		return "/secure/perfil/editarPerfil.xhtml?faces-redirect=true";
	}
	
	public String editarPerfil(Long idPerfil) {
		return "/secure/perfil/editarPerfil.xhtml?faces-redirect=true&idPerfil=" + idPerfil;
	}
	
	public void removerPerfil() {
		try {
			perfilService.removerPerfil(perfilSelecionado);
			perfilSelecionado = null;
			
			this.listarPerfis();
			addInfoMessage(getMsgs().getString("perfil.remover.sucesso"));
		} catch (AppException e) {
			addressException(e);
		}
	}
	
	public List<Perfil> getPerfilList() {
		return perfilList;
	}

	public Perfil getPerfilSelecionado() {
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}

}
