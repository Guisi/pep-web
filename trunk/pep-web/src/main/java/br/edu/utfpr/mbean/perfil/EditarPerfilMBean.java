package br.edu.utfpr.mbean.perfil;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.DualListModel;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Autorizacao;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.service.AutorizacaoService;
import br.edu.utfpr.service.PerfilService;

@ManagedBean
@ViewScoped
public class EditarPerfilMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilService perfilService;
	@Inject
	private AutorizacaoService autorizacaoService;
	
	private Perfil perfilSelecionado;
	private DualListModel<Autorizacao> autorizacoesPickList;
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		String idPerfil = request.getParameter("idPerfil");
		
		if (StringUtils.isNotEmpty(idPerfil) && StringUtils.isNumeric(idPerfil)) {
			this.perfilSelecionado = perfilService.retornarPerfil(Long.parseLong(idPerfil));
		} else {
			this.perfilSelecionado = new Perfil();
		}
		
		this.listarAutorizacoes();
	}
	
	private void listarAutorizacoes() {
		List<Autorizacao> autorizacoes = autorizacaoService.retornarAutorizacoes();
		Set<Autorizacao> autorizacoesPerfil = perfilSelecionado.getAutorizacoes();
		if (autorizacoesPerfil != null) {
			for (Autorizacao autorizacao : autorizacoesPerfil) {
				autorizacoes.remove(autorizacao);
			}
		} else {
			autorizacoesPerfil = new LinkedHashSet<>();
		}
		autorizacoesPickList = new DualListModel<Autorizacao>(autorizacoes, new ArrayList<>(autorizacoesPerfil));
	}
	
	public String salvarPerfil() {
		boolean isNew = perfilSelecionado.isNew();
		perfilSelecionado.setAutorizacoes(new LinkedHashSet<>(autorizacoesPickList.getTarget()));
		try {
			perfilService.salvarPerfil(perfilSelecionado);
			perfilSelecionado = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("perfil.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("perfil.editar.sucesso"), true);
			}
			
			return "/secure/perfil/perfis.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}
	
	public Perfil getPerfilSelecionado() {
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}

	public DualListModel<Autorizacao> getAutorizacoesPickList() {
		return autorizacoesPickList;
	}

	public void setAutorizacoesPickList(DualListModel<Autorizacao> autorizacoesPickList) {
		this.autorizacoesPickList = autorizacoesPickList;
	}

}
