package br.edu.utfpr.mbean.atendimento;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.constants.TipoProcedimento;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.model.Procedimento;
import br.edu.utfpr.service.AtendimentoService;
import br.edu.utfpr.service.DoencaService;
import br.edu.utfpr.service.MedicamentoAtendimentoService;
import br.edu.utfpr.service.ProcedimentoService;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class AtendimentoMBean extends BaseAtendimentoMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private AtendimentoService atendimentoService;
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private MedicamentoAtendimentoService medicamentoAtendimentoService;
	@Inject
	private DoencaService doencaService;
	@Inject
	private ProcedimentoService procedimentoService;
	
	private List<Atendimento> atendimentoList;
	private Atendimento atendimentoSelecionado;
	
	private boolean mostrarReabrirAtendimento;
	
	/* Resumo prontuario */
	private List<MedicamentoAtendimento> medicamentosEmUso;
	private List<Doenca> doencasDiagnosticadas;
	private List<Procedimento> cirurgias;
	
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
		this.listarResumoProntuario();
	}
	
	public void listarAtendimentos() {
		this.atendimentoList = atendimentoService.retornarAtendimentosPaciente(this.pacienteSelecionado.getId());
		
		if (!this.atendimentoList.isEmpty()) {
			this.atendimentoSelecionado = this.atendimentoList.get(0);
			this.onAtendimentoSelecionado();
		}
	}
	
	public void listarResumoProntuario() {
		this.medicamentosEmUso = medicamentoAtendimentoService.retornarMedicamentosEmUsoPaciente(this.pacienteSelecionado.getId(), null);
		this.doencasDiagnosticadas = doencaService.retornarDoencasDiagnosticadas(this.pacienteSelecionado.getId());
		this.cirurgias = procedimentoService.retornarProcedimentosRealizados(TipoProcedimento.CIRURGICO, this.pacienteSelecionado.getId());
	}
	
	public void onAtendimentoSelecionado() {
		this.atendimentoSelecionado = atendimentoService.retornarAtendimento(this.atendimentoSelecionado.getId());
		this.mostrarReabrirAtendimento = this.atendimentoList.get(0).equals(this.atendimentoSelecionado);
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

	public boolean isMostrarReabrirAtendimento() {
		return mostrarReabrirAtendimento;
	}

	public void setMostrarReabrirAtendimento(boolean mostrarReabrirAtendimento) {
		this.mostrarReabrirAtendimento = mostrarReabrirAtendimento;
	}

	public List<MedicamentoAtendimento> getMedicamentosEmUso() {
		return medicamentosEmUso;
	}

	public void setMedicamentosEmUso(List<MedicamentoAtendimento> medicamentosEmUso) {
		this.medicamentosEmUso = medicamentosEmUso;
	}

	public List<Doenca> getDoencasDiagnosticadas() {
		return doencasDiagnosticadas;
	}

	public void setDoencasDiagnosticadas(List<Doenca> doencasDiagnosticadas) {
		this.doencasDiagnosticadas = doencasDiagnosticadas;
	}

	public List<Procedimento> getCirurgias() {
		return cirurgias;
	}

	public void setCirurgias(List<Procedimento> cirurgias) {
		this.cirurgias = cirurgias;
	}
}
