package br.edu.utfpr.mbean.atendimento;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Medicamento;
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.service.AtendimentoService;
import br.edu.utfpr.service.MedicamentoService;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class EditarAtendimentoMBean extends BaseAtendimentoMBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private MedicamentoService medicamentoService;
	@Inject
	private AtendimentoService atendimentoService;
	
	private Atendimento atendimentoSelecionado;

	private Medicamento medicamentoSelecionado;
	private List<Medicamento> medicamentosDisponiveis;
	private List<MedicamentoAtendimento> medicamentosAtendimento;

	@PostConstruct
	public void init() {
		String idAtendimento = getRequest().getParameter("idAtendimento");
		
		if (StringUtils.isNotEmpty(idAtendimento)) {
			if (new Scanner(idAtendimento).hasNextLong()) {
				this.atendimentoSelecionado = atendimentoService.retornarAtendimento(Long.parseLong(idAtendimento));
				this.pacienteSelecionado = this.atendimentoSelecionado.getPaciente();
				this.medicamentosAtendimento = new ArrayList<MedicamentoAtendimento>(this.atendimentoSelecionado.getMedicamentos());
			}
			
			if (this.atendimentoSelecionado == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("atendimento.erro.naoencontrado"), idAtendimento));
				return;
			}
		} else {
			this.atendimentoSelecionado = new Atendimento();
			this.medicamentosAtendimento = new ArrayList<MedicamentoAtendimento>();
			
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
			
			this.atendimentoSelecionado.setPaciente(this.pacienteSelecionado);
		}
		
		this.listarMedicamentosDisponiveis();
	}
	
	public String cancelar() {
		return "/secure/atendimento/atendimentos.xhtml?faces-redirect=true&idPaciente=" + this.pacienteSelecionado.getId();
	}
	
	public void salvar() {
		this.salvarAtendimento();
		addInfoMessage(getMsgs().getString("atendimento.salvar.sucesso"));
	}
	
	public String finalizar() {
		this.salvarAtendimento();
		addInfoMessage(getMsgs().getString("atendimento.finalizar.sucesso"), true);
		
		return "/secure/atendimento/atendimentos.xhtml?faces-redirect=true&idPaciente=" + this.pacienteSelecionado.getId();
	}
	
	private void salvarAtendimento() {
		this.atendimentoSelecionado.setMedicamentos(new LinkedHashSet<MedicamentoAtendimento>(this.medicamentosAtendimento));
		this.atendimentoSelecionado = atendimentoService.salvarAtendimento(this.atendimentoSelecionado);
	}
	
	private void listarMedicamentosDisponiveis() {
		this.medicamentosDisponiveis = medicamentoService.retornarMedicamentos(Boolean.TRUE);
		
		for (MedicamentoAtendimento medicamentoAtendimento : this.medicamentosAtendimento) {
			if (medicamentoAtendimento.getMedicamento() != null) {
				this.medicamentosDisponiveis.remove(medicamentoAtendimento.getMedicamento());
			}
		}
	}
	
	public void adicionarMedicamento() {
		if (this.medicamentoSelecionado != null) {
			this.medicamentosDisponiveis.remove(this.medicamentoSelecionado);
			
			MedicamentoAtendimento medicamentoAtendimento = new MedicamentoAtendimento();
			medicamentoAtendimento.setMedicamento(this.medicamentoSelecionado);
			this.medicamentosAtendimento.add(medicamentoAtendimento);

			this.medicamentoSelecionado = null;
		}
	}
	
	private void ordenaListaMedicamentos() {
		Comparator<Medicamento> comparator = new Comparator<Medicamento>() {
			@Override
			public int compare(Medicamento o1, Medicamento o2) {
				return o1.getPrincipioAtivo().compareTo(o2.getPrincipioAtivo());
			}
		};
		Collections.sort(this.medicamentosDisponiveis, comparator);
	}
	
	public Atendimento getAtendimentoSelecionado() {
		return atendimentoSelecionado;
	}

	public void setAtendimentoSelecionado(Atendimento atendimentoSelecionado) {
		this.atendimentoSelecionado = atendimentoSelecionado;
	}

	public List<Medicamento> getMedicamentosDisponiveis() {
		return medicamentosDisponiveis;
	}

	public void setMedicamentosDisponiveis(List<Medicamento> medicamentosDisponiveis) {
		this.medicamentosDisponiveis = medicamentosDisponiveis;
	}

	public Medicamento getMedicamentoSelecionado() {
		return medicamentoSelecionado;
	}

	public void setMedicamentoSelecionado(Medicamento medicamentoSelecionado) {
		this.medicamentoSelecionado = medicamentoSelecionado;
	}

	public List<MedicamentoAtendimento> getMedicamentosAtendimento() {
		return medicamentosAtendimento;
	}

	public void setMedicamentosAtendimento(List<MedicamentoAtendimento> medicamentosAtendimento) {
		this.medicamentosAtendimento = medicamentosAtendimento;
	}
	
}
