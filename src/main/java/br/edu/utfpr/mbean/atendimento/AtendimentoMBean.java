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
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.service.AlergiaService;
import br.edu.utfpr.service.AtendimentoService;
import br.edu.utfpr.service.DoencaService;
import br.edu.utfpr.service.HabitoService;
import br.edu.utfpr.service.MedicamentoAtendimentoService;
import br.edu.utfpr.service.ProcedimentoService;
import br.edu.utfpr.service.UsuarioService;
import br.edu.utfpr.service.VacinaService;
import br.edu.utfpr.service.vo.AlergiaPaciente;
import br.edu.utfpr.service.vo.AntecedenteFamiliarPaciente;
import br.edu.utfpr.service.vo.DoencaDiagnosticada;
import br.edu.utfpr.service.vo.HabitoPaciente;
import br.edu.utfpr.service.vo.ProcedimentoRealizado;
import br.edu.utfpr.service.vo.VacinaPaciente;

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
	@Inject
	private HabitoService habitoService;
	@Inject
	private AlergiaService alergiaService;
	@Inject
	private VacinaService vacinaService;
	
	private List<Atendimento> atendimentoList;
	private Atendimento atendimentoSelecionado;
	
	private boolean mostrarReabrirAtendimento;
	
	/* Resumo prontuario */
	private List<MedicamentoAtendimento> medicamentosEmUso;
	private List<DoencaDiagnosticada> doencasDiagnosticadas;
	private List<ProcedimentoRealizado> cirurgias;
	private List<HabitoPaciente> habitos;
	private List<AlergiaPaciente> alergias;
	private List<VacinaPaciente> vacinas;
	private List<AntecedenteFamiliarPaciente> antecedentesFamiliares;
	
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
		this.cirurgias = procedimentoService.retornarProcedimentosRealizados(this.pacienteSelecionado.getId());
		this.habitos = habitoService.retornarHabitosPaciente(this.pacienteSelecionado.getId());
		this.alergias = alergiaService.retornarAlergiasPaciente(this.pacienteSelecionado.getId());
		this.vacinas = vacinaService.retornarVacinasPaciente(this.pacienteSelecionado.getId());
		this.antecedentesFamiliares = doencaService.retornarAntecedentesFamiliaresPaciente(this.pacienteSelecionado.getId());
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

	public List<DoencaDiagnosticada> getDoencasDiagnosticadas() {
		return doencasDiagnosticadas;
	}

	public void setDoencasDiagnosticadas(List<DoencaDiagnosticada> doencasDiagnosticadas) {
		this.doencasDiagnosticadas = doencasDiagnosticadas;
	}

	public List<ProcedimentoRealizado> getCirurgias() {
		return cirurgias;
	}

	public void setCirurgias(List<ProcedimentoRealizado> cirurgias) {
		this.cirurgias = cirurgias;
	}

	public List<HabitoPaciente> getHabitos() {
		return habitos;
	}

	public void setHabitos(List<HabitoPaciente> habitos) {
		this.habitos = habitos;
	}

	public List<AlergiaPaciente> getAlergias() {
		return alergias;
	}

	public void setAlergias(List<AlergiaPaciente> alergias) {
		this.alergias = alergias;
	}

	public List<VacinaPaciente> getVacinas() {
		return vacinas;
	}

	public void setVacinas(List<VacinaPaciente> vacinas) {
		this.vacinas = vacinas;
	}

	public List<AntecedenteFamiliarPaciente> getAntecedentesFamiliares() {
		return antecedentesFamiliares;
	}

	public void setAntecedentesFamiliares(List<AntecedenteFamiliarPaciente> antecedentesFamiliares) {
		this.antecedentesFamiliares = antecedentesFamiliares;
	}
}
