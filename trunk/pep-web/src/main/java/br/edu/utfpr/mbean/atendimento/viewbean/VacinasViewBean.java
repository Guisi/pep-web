package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Vacina;
import br.edu.utfpr.model.VacinaAtendimento;
import br.edu.utfpr.service.VacinaAtendimentoService;
import br.edu.utfpr.service.VacinaService;

public class VacinasViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private VacinaService vacinaService;
	@Inject
	private VacinaAtendimentoService vacinaAtendimentoService;
	
	private Vacina vacinaSelecionada;
	private List<Vacina> vacinasDisponiveis;
	private List<Vacina> vacinasMaisUsadas;
	private List<VacinaAtendimento> vacinasAtendimento;
	private List<VacinaAtendimento> vacinasAtendimentosAnteriores;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (getAtendimentoSelecionado().isNew()) {
			this.vacinasAtendimento = new ArrayList<VacinaAtendimento>();
		} else {
			this.vacinasAtendimento = new ArrayList<VacinaAtendimento>(getAtendimentoSelecionado().getVacinas());
		}
		
		this.listarVacinasAtendimentosAnteriores();
		this.listarVacinasDisponiveis();
		this.listarVacinasMaisUsadas();
	}
	
	private void listarVacinasDisponiveis() {
		this.vacinasDisponiveis = vacinaService.retornarVacinas(Boolean.TRUE);
		
		List<VacinaAtendimento> vacinasAgrupado = this.getVacinasAtendimentoAgrupado();
		for (VacinaAtendimento vacinaAtendimento : vacinasAgrupado) {
			if (vacinaAtendimento.getVacina() != null) {
				this.vacinasDisponiveis.remove(vacinaAtendimento.getVacina());
			}
		}
	}
	
	private void listarVacinasMaisUsadas() {
		List<Long> idsVacinasIgnorar = new ArrayList<Long>();
		for (VacinaAtendimento vacinaAtendimento : getVacinasAtendimentoAgrupado()) {
			if (vacinaAtendimento.getVacina() != null) {
				idsVacinasIgnorar.add(vacinaAtendimento.getVacina().getId());
			}
		}
		
		this.vacinasMaisUsadas = vacinaAtendimentoService
				.retornarVacinasMaisUsadas(Constantes.QTDE_SUGESTOES_VACINAS, idsVacinasIgnorar, Boolean.TRUE);
	}
	
	private void listarVacinasAtendimentosAnteriores() {
		this.vacinasAtendimentosAnteriores = vacinaAtendimentoService
				.retornarVacinasAtendimentosAnteriores(getPacienteSelecionado().getId(), getAtendimentoSelecionado().getId());
		for (VacinaAtendimento vacinaAtendimento : this.vacinasAtendimentosAnteriores) {
			vacinaAtendimento.setAtendimentoAnterior(true);
		}
	}
	
	public List<VacinaAtendimento> getVacinasAtendimentoAgrupado() {
		List<VacinaAtendimento> l = new ArrayList<>(this.vacinasAtendimento);
		l.addAll(this.vacinasAtendimentosAnteriores);
		return l;
	}
	
	public void adicionarVacina(Vacina vacina) {
		this.vacinaSelecionada = vacina;
		this.adicionarVacina();
	}
	
	public void adicionarVacina() {
		if (this.vacinaSelecionada != null) {
			this.vacinasDisponiveis.remove(this.vacinaSelecionada);
			this.vacinasMaisUsadas.remove(this.vacinaSelecionada);
			
			VacinaAtendimento vacinaAtendimento = new VacinaAtendimento();
			vacinaAtendimento.setVacina(vacinaSelecionada);
			this.vacinasAtendimento.add(vacinaAtendimento);

			this.vacinaSelecionada = null;
			
			this.listarVacinasMaisUsadas();
		}
	}
	
	public void adicionarOutraVacina() {
		this.vacinasAtendimento.add(new VacinaAtendimento());
	}
	
	public void removerVacina(int indice) {
		VacinaAtendimento vacinaAtendimento = this.vacinasAtendimento.get(indice);
		this.vacinasAtendimento.remove(indice);
		if (vacinaAtendimento.getVacina() != null) {
			this.vacinasDisponiveis.add(vacinaAtendimento.getVacina());
			
			this.listarVacinasMaisUsadas();
			this.ordenaListasVacinas();
		}
	}
	
	private void ordenaListasVacinas() {
		Comparator<Vacina> comparator = new Comparator<Vacina>() {
			@Override
			public int compare(Vacina o1, Vacina o2) {
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		};
		
		Collections.sort(this.vacinasDisponiveis, comparator);
		Collections.sort(this.vacinasMaisUsadas, comparator);
	}

	public List<Vacina> getVacinasDisponiveis() {
		return vacinasDisponiveis;
	}

	public void setVacinasDisponiveis(List<Vacina> vacinasDisponiveis) {
		this.vacinasDisponiveis = vacinasDisponiveis;
	}

	public List<VacinaAtendimento> getVacinasAtendimento() {
		return vacinasAtendimento;
	}

	public void setVacinasAtendimento(List<VacinaAtendimento> vacinasAtendimento) {
		this.vacinasAtendimento = vacinasAtendimento;
	}

	public List<VacinaAtendimento> getVacinasAtendimentosAnteriores() {
		return vacinasAtendimentosAnteriores;
	}

	public void setVacinasAtendimentosAnteriores(List<VacinaAtendimento> vacinasAtendimentosAnteriores) {
		this.vacinasAtendimentosAnteriores = vacinasAtendimentosAnteriores;
	}

	public Vacina getVacinaSelecionada() {
		return vacinaSelecionada;
	}

	public void setVacinaSelecionada(Vacina vacinaSelecionada) {
		this.vacinaSelecionada = vacinaSelecionada;
	}

	public List<Vacina> getVacinasMaisUsadas() {
		return vacinasMaisUsadas;
	}

	public void setVacinasMaisUsadas(List<Vacina> vacinasMaisUsadas) {
		this.vacinasMaisUsadas = vacinasMaisUsadas;
	}
	
}
