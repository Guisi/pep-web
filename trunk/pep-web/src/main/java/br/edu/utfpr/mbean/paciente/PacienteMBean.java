package br.edu.utfpr.mbean.paciente;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.constants.PerfilEnum;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.AlteracaoAtributo;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class PacienteMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;
	
	private List<Usuario> pacienteList;
	private Usuario pacienteSelecionado;
	private String pesquisa;
	
	private List<AlteracaoAtributo> historicoAlteracoes;
	
	@PostConstruct
	public void init() {
		this.pesquisa = null;
		this.listarPacientes();
	}
	
	public void listarPacientes() {
		this.pacienteList = usuarioService.retornarUsuarios(pesquisa, Boolean.TRUE, PerfilEnum.PACIENTE);
	}
	
	public String onEditPacienteClick(Long idUsuario) {
		return "/secure/paciente/editarPaciente.xhtml?faces-redirect=true&idUsuario=" + idUsuario;
	}
	
	public String verProntuario(Long idPaciente) {
		return "/secure/atendimento/atendimentos.xhtml?faces-redirect=true&idPaciente=" + idPaciente;
	}
	
	public String novoPaciente() {
		return "/secure/paciente/editarPaciente.xhtml?faces-redirect=true";
	}
	
	public void removerPaciente() {
		usuarioService.inativarUsuario(getUsuarioLogado(), pacienteSelecionado);
		
		this.listarPacientes();
		
		addInfoMessage(getMsgs().getString("paciente.remover.sucesso"));
	}
	
	public void listarHistoricoPaciente(Long idPaciente) {
		this.historicoAlteracoes = usuarioService.retornarHistoricoAlteracaoUsuario(idPaciente);
	}

	public List<Usuario> getPacienteList() {
		return pacienteList;
	}

	public void setPacienteList(List<Usuario> pacienteList) {
		this.pacienteList = pacienteList;
	}

	public Usuario getPacienteSelecionado() {
		return pacienteSelecionado;
	}

	public void setPacienteSelecionado(Usuario pacienteSelecionado) {
		this.pacienteSelecionado = pacienteSelecionado;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public List<AlteracaoAtributo> getHistoricoAlteracoes() {
		return historicoAlteracoes;
	}

	public void setHistoricoAlteracoes(List<AlteracaoAtributo> historicoAlteracoes) {
		this.historicoAlteracoes = historicoAlteracoes;
	}
}
