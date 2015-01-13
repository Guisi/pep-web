package br.edu.utfpr.mbean.atendimento;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.service.AtendimentoService;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class AtendimentoMBean extends BaseAtendimentoMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private AtendimentoService atendimentoService;
	@Inject
	private UsuarioService usuarioService;
	
	private List<Atendimento> atendimentoList;
	private Atendimento atendimentoSelecionado;
	
	@PostConstruct
	public void init() {
		String idPaciente = getRequest().getParameter("idPaciente");
		
		if (StringUtils.isNotEmpty(idPaciente) && new Scanner(idPaciente).hasNextLong()) {
			this.pacienteSelecionado = usuarioService.retornarUsuario(Long.parseLong(idPaciente));
		}
		
		//se nao achou usuario ou usuario nao eh paciente, retorna msg de erro
		if (this.pacienteSelecionado == null || !this.pacienteSelecionado.isPaciente()) {
			addErrorMessage(MessageFormat.format(getMsgs().getString("paciente.erro.naoencontrado"), idPaciente));
			this.pacienteSelecionado = null;
			return;
		}

		this.listarAtendimentos();
	}
	
	public void listarAtendimentos() {
		this.atendimentoList = atendimentoService.retornarAtendimentosPaciente(this.pacienteSelecionado.getId());
		
		if (!this.atendimentoList.isEmpty()) {
			this.atendimentoSelecionado = this.atendimentoList.get(0);
			this.onAtendimentoSelecionado();
		}
	}
	
	public void onAtendimentoSelecionado() {
		this.atendimentoSelecionado = atendimentoService.retornarAtendimento(this.atendimentoSelecionado.getId());
	}
	
	public String novoAtendimento() {
		return "/secure/atendimento/editarAtendimento.xhtml?faces-redirect=true&idPaciente=" + this.pacienteSelecionado.getId();
	}
	
	public String editarAtendimento() {
		return "/secure/atendimento/editarAtendimento.xhtml?faces-redirect=true&idAtendimento=" + this.atendimentoSelecionado.getId();
	}
	
	public List<Atendimento> getAtendimentoList() {
		return atendimentoList;
	}

	public void setAtendimentoList(List<Atendimento> atendimentoList) {
		this.atendimentoList = atendimentoList;
	}

	public Atendimento getAtendimentoSelecionado() {
		return atendimentoSelecionado;
	}

	public void setAtendimentoSelecionado(Atendimento atendimentoSelecionado) {
		this.atendimentoSelecionado = atendimentoSelecionado;
	}
}
