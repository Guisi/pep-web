package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Habito;
import br.edu.utfpr.model.HabitoAtendimento;
import br.edu.utfpr.service.HabitoAtendimentoService;
import br.edu.utfpr.service.HabitoService;

public class HabitosViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private HabitoService habitoService;
	@Inject
	private HabitoAtendimentoService habitoAtendimentoService;
	
	private Habito habitoSelecionado;
	private List<Habito> habitosDisponiveis;
	private List<Habito> habitosMaisUsados;
	private List<HabitoAtendimento> habitosAtendimento;
	private List<HabitoAtendimento> habitosAtendimentosAnteriores;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (getAtendimentoSelecionado().isNew()) {
			this.habitosAtendimento = new ArrayList<HabitoAtendimento>();
		} else {
			this.habitosAtendimento = new ArrayList<HabitoAtendimento>(getAtendimentoSelecionado().getHabitos());
		}
		
		this.listarHabitosAtendimentosAnteriores();
		this.listarHabitosDisponiveis();
		this.listarHabitosMaisUsados();
	}
	
	private void listarHabitosDisponiveis() {
		this.habitosDisponiveis = habitoService.retornarHabitos(Boolean.TRUE);
		
		List<HabitoAtendimento> habitosAgrupado = this.getHabitosAtendimentoAgrupado();
		for (HabitoAtendimento habitoAtendimento : habitosAgrupado) {
			if (habitoAtendimento.getHabito() != null) {
				this.habitosDisponiveis.remove(habitoAtendimento.getHabito());
			}
		}
	}
	
	private void listarHabitosMaisUsados() {
		List<Long> idsHabitosIgnorar = new ArrayList<Long>();
		for (HabitoAtendimento habitoAtendimento : getHabitosAtendimentoAgrupado()) {
			if (habitoAtendimento.getHabito() != null) {
				idsHabitosIgnorar.add(habitoAtendimento.getHabito().getId());
			}
		}
		
		this.habitosMaisUsados = habitoAtendimentoService
				.retornarHabitosMaisUsados(Constantes.QTDE_SUGESTOES_HABITOS, idsHabitosIgnorar, Boolean.TRUE);
	}
	
	private void listarHabitosAtendimentosAnteriores() {
		this.habitosAtendimentosAnteriores = habitoAtendimentoService
				.retornarHabitosAtendimentosAnteriores(getPacienteSelecionado().getId(), getAtendimentoSelecionado().getId());
		for (HabitoAtendimento habitoAtendimento : this.habitosAtendimentosAnteriores) {
			habitoAtendimento.setAtendimentoAnterior(true);
		}
	}
	
	public List<HabitoAtendimento> getHabitosAtendimentoAgrupado() {
		List<HabitoAtendimento> l = new ArrayList<>(this.habitosAtendimento);
		l.addAll(this.habitosAtendimentosAnteriores);
		return l;
	}
	
	public void adicionarHabito(Habito habito) {
		this.habitoSelecionado = habito;
		this.adicionarHabito();
	}
	
	public void adicionarHabito() {
		if (this.habitoSelecionado != null) {
			this.habitosDisponiveis.remove(this.habitoSelecionado);
			this.habitosMaisUsados.remove(this.habitoSelecionado);
			
			HabitoAtendimento habitoAtendimento = new HabitoAtendimento();
			habitoAtendimento.setHabito(habitoSelecionado);
			this.habitosAtendimento.add(habitoAtendimento);

			this.habitoSelecionado = null;
			
			this.listarHabitosMaisUsados();
		}
	}
	
	public void adicionarOutroHabito() {
		this.habitosAtendimento.add(new HabitoAtendimento());
	}
	
	public void removerHabito(int indice) {
		HabitoAtendimento habitoAtendimento = this.habitosAtendimento.get(indice);
		this.habitosAtendimento.remove(indice);
		if (habitoAtendimento.getHabito() != null) {
			this.habitosDisponiveis.add(habitoAtendimento.getHabito());
			
			this.listarHabitosMaisUsados();
			this.ordenaListasHabitos();
		}
	}
	
	private void ordenaListasHabitos() {
		Comparator<Habito> comparator = new Comparator<Habito>() {
			@Override
			public int compare(Habito o1, Habito o2) {
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		};
		
		Collections.sort(this.habitosDisponiveis, comparator);
		Collections.sort(this.habitosMaisUsados, comparator);
	}

	public Habito getHabitoSelecionado() {
		return habitoSelecionado;
	}

	public void setHabitoSelecionado(Habito habitoSelecionado) {
		this.habitoSelecionado = habitoSelecionado;
	}

	public List<Habito> getHabitosDisponiveis() {
		return habitosDisponiveis;
	}

	public void setHabitosDisponiveis(List<Habito> habitosDisponiveis) {
		this.habitosDisponiveis = habitosDisponiveis;
	}

	public List<Habito> getHabitosMaisUsados() {
		return habitosMaisUsados;
	}

	public void setHabitosMaisUsados(List<Habito> habitosMaisUsados) {
		this.habitosMaisUsados = habitosMaisUsados;
	}

	public List<HabitoAtendimento> getHabitosAtendimento() {
		return habitosAtendimento;
	}

	public void setHabitosAtendimento(List<HabitoAtendimento> habitosAtendimento) {
		this.habitosAtendimento = habitosAtendimento;
	}

	public List<HabitoAtendimento> getHabitosAtendimentosAnteriores() {
		return habitosAtendimentosAnteriores;
	}

	public void setHabitosAtendimentosAnteriores(List<HabitoAtendimento> habitosAtendimentosAnteriores) {
		this.habitosAtendimentosAnteriores = habitosAtendimentosAnteriores;
	}
	
}
