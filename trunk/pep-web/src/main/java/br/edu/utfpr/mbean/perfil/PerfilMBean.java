package br.edu.utfpr.mbean.perfil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DualListModel;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Autorizacao;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.service.AutorizacaoService;
import br.edu.utfpr.service.PerfilService;

@ManagedBean
@ViewScoped
public class PerfilMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilService perfilService;
	@Inject
	private AutorizacaoService autorizacaoService;
	
	private List<Perfil> perfilList;
	private Perfil perfilSelecionado;
	private DualListModel<Autorizacao> autorizacoesPickList;
	
	@PostConstruct
	public void init() {
		this.listarPerfis();
	}
	
	private void listarPerfis() {
		this.perfilList = perfilService.retornarPerfis();
	}
	
	private void listarAutorizacoes() {
		List<Autorizacao> autorizacoes = autorizacaoService.retornarAutorizacoes();
		List<Autorizacao> autorizacoesPerfil = perfilSelecionado.getAutorizacoes();
		if (autorizacoesPerfil != null) {
			for (Autorizacao autorizacao : autorizacoesPerfil) {
				autorizacoes.remove(autorizacao);
			}
		} else {
			autorizacoesPerfil = new ArrayList<Autorizacao>();
		}
		autorizacoesPickList = new DualListModel<Autorizacao>(autorizacoes, autorizacoesPerfil);
	}
	
	public void novoPerfil() {
		this.perfilSelecionado = new Perfil();
		this.listarAutorizacoes();
	}
	
	public void editarPerfil(Perfil perfil) {
		this.perfilSelecionado = perfilService.retornarPerfil(perfil.getId());
		this.listarAutorizacoes();
	}
	
	public void cancelarEdicao() {
		this.perfilSelecionado = null;
	}
	
	public void removerPerfil() {
		perfilService.removerPerfil(perfilSelecionado);
		perfilSelecionado = null;
		
		this.listarPerfis();
		
		addInfoMessage(getMsgs().getString("perfil.remover.sucesso"));
	}
	
	public void salvarPerfil() {
		if (perfilService.retornarPerfilPorNome(perfilSelecionado.getNome()) != null) {
			addErrorMessage(MessageFormat.format(getMsgs().getString("perfil.salvar.erro.nomeexistente"), perfilSelecionado.getNome()));
			return;
		}
		
		boolean isNew = perfilSelecionado.isNew();
		perfilSelecionado.setAutorizacoes(autorizacoesPickList.getTarget());
		perfilService.salvarPerfil(perfilSelecionado);
		perfilSelecionado = null;

		if (isNew) {
			addInfoMessage(getMsgs().getString("perfil.criar.sucesso"));
		} else {
			addInfoMessage(getMsgs().getString("perfil.editar.sucesso"));
		}
		this.listarPerfis();
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

	public DualListModel<Autorizacao> getAutorizacoesPickList() {
		return autorizacoesPickList;
	}

	public void setAutorizacoesPickList(DualListModel<Autorizacao> autorizacoesPickList) {
		this.autorizacoesPickList = autorizacoesPickList;
	}

}
