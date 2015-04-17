package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Medicamento;
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.service.MedicamentoAtendimentoService;
import br.edu.utfpr.service.MedicamentoService;

public class TratamentosAndamentoViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MedicamentoAtendimentoService medicamentoAtendimentoService;
	@Inject
	private MedicamentoService medicamentoService;

	private Medicamento medicamentoSelecionado;
	private List<Medicamento> medicamentosDisponiveis;
	private List<MedicamentoAtendimento> medicamentosAtendimento;
	private List<MedicamentoAtendimento> medicamentosAtendimentosAnteriores;

	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (getAtendimentoSelecionado().isNew()) {
			this.medicamentosAtendimento = new ArrayList<MedicamentoAtendimento>();
		} else {
			this.medicamentosAtendimento = new ArrayList<>(getAtendimentoSelecionado().getMedicamentos());
		}

		this.listarMedicamentosAtendimentosAnteriores();
		this.listarMedicamentosDisponiveis();
	}
	
	private void listarMedicamentosDisponiveis() {
		this.medicamentosDisponiveis = medicamentoService.retornarMedicamentos(Boolean.TRUE);
		
		List<MedicamentoAtendimento> medicamentosAgrupado = this.getMedicamentosAtendimentoAgrupado();
		for (MedicamentoAtendimento medicamentoAtendimento : medicamentosAgrupado) {
			if (medicamentoAtendimento.getMedicamento() != null) {
				this.medicamentosDisponiveis.remove(medicamentoAtendimento.getMedicamento());
			}
		}
	}
	
	private void listarMedicamentosAtendimentosAnteriores() {
		this.medicamentosAtendimentosAnteriores = medicamentoAtendimentoService
				.retornarMedicamentosEmUsoPaciente(getPacienteSelecionado().getId(), getAtendimentoSelecionado().getId());
		for (MedicamentoAtendimento medicamentoAtendimento : this.medicamentosAtendimentosAnteriores) {
			medicamentoAtendimento.setAtendimentoAnterior(true);
		}
	}
	
	public void adicionarMedicamento() {
		if (this.medicamentoSelecionado != null) {
			this.medicamentosDisponiveis.remove(this.medicamentoSelecionado);
			
			MedicamentoAtendimento medicamentoAtendimento = new MedicamentoAtendimento();
			medicamentoAtendimento.setMedicamento(this.medicamentoSelecionado);
			medicamentoAtendimento.setEmUso(Boolean.TRUE);
			this.medicamentosAtendimento.add(medicamentoAtendimento);

			this.medicamentoSelecionado = null;
		}
	}
	
	public void adicionarOutroMedicamento() {
		MedicamentoAtendimento medicamentoAtendimento = new MedicamentoAtendimento();
		medicamentoAtendimento.setEmUso(Boolean.TRUE);
		this.medicamentosAtendimento.add(medicamentoAtendimento);
	}
	
	public void removerMedicamento(int indice) {
		MedicamentoAtendimento medicamento = this.medicamentosAtendimento.get(indice);
		this.medicamentosAtendimento.remove(indice);
		if (medicamento.getMedicamento() != null) {
			this.medicamentosDisponiveis.add(medicamento.getMedicamento());
			this.ordenaListasMedicamentos();
		}
	}
	
	private void ordenaListasMedicamentos() {
		Comparator<Medicamento> comparator = new Comparator<Medicamento>() {
			@Override
			public int compare(Medicamento o1, Medicamento o2) {
				int c = o1.getPrincipioAtivo().compareTo(o2.getPrincipioAtivo());
				if (c == 0) {
					c = o1.getApresentacao().compareTo(o2.getApresentacao());
				}
				return c;
			}
		};
		
		Collections.sort(this.medicamentosDisponiveis, comparator);
	}
	
	public List<MedicamentoAtendimento> getMedicamentosAtendimentoAgrupado() {
		List<MedicamentoAtendimento> l = new ArrayList<>(this.medicamentosAtendimento);
		l.addAll(this.medicamentosAtendimentosAnteriores);
		return l;
	}
	
	public List<Atendimento> getAtendimentosAnterioresTratamentos() {
		List<Atendimento> atendimentos = new ArrayList<>();
		for (Atendimento atendimento : getAtendimentosAnteriores()) {
			if (!atendimento.getMedicamentos().isEmpty()) {
				atendimentos.add(atendimento);
			}
		}
		return atendimentos;
	}

	public Medicamento getMedicamentoSelecionado() {
		return medicamentoSelecionado;
	}

	public void setMedicamentoSelecionado(Medicamento medicamentoSelecionado) {
		this.medicamentoSelecionado = medicamentoSelecionado;
	}

	public List<Medicamento> getMedicamentosDisponiveis() {
		return medicamentosDisponiveis;
	}

	public void setMedicamentosDisponiveis(List<Medicamento> medicamentosDisponiveis) {
		this.medicamentosDisponiveis = medicamentosDisponiveis;
	}

	public List<MedicamentoAtendimento> getMedicamentosAtendimento() {
		return medicamentosAtendimento;
	}

	public void setMedicamentosAtendimento(List<MedicamentoAtendimento> medicamentosAtendimento) {
		this.medicamentosAtendimento = medicamentosAtendimento;
	}

	public List<MedicamentoAtendimento> getMedicamentosAtendimentosAnteriores() {
		return medicamentosAtendimentosAnteriores;
	}

	public void setMedicamentosAtendimentosAnteriores(List<MedicamentoAtendimento> medicamentosAtendimentosAnteriores) {
		this.medicamentosAtendimentosAnteriores = medicamentosAtendimentosAnteriores;
	}
}
