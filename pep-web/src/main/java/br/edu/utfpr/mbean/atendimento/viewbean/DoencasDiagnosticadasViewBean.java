package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.constants.StatusDoenca;
import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.model.DoencaDiagnosticadaAtendimento;
import br.edu.utfpr.service.DoencaDiagnosticadaAtendimentoService;
import br.edu.utfpr.service.DoencaService;

public class DoencasDiagnosticadasViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DoencaService doencaService;
	@Inject
	private DoencaDiagnosticadaAtendimentoService doencaDiagnosticadaAtendimentoService;
	
	private Doenca doencaDiagnosticadaSelecionada;
	private List<Doenca> doencasDiagnosticadasDisponiveis;
	private List<Doenca> doencasDiagnosticadasMaisUsadas;
	private List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAtendimento;
	private List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAtendimentosAnteriores;
	
	private List<StatusDoenca> statusDoencaList;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (getAtendimentoSelecionado().isNew()) {
			this.doencasDiagnosticadasAtendimento = new ArrayList<DoencaDiagnosticadaAtendimento>();
		} else {
			this.doencasDiagnosticadasAtendimento = new ArrayList<DoencaDiagnosticadaAtendimento>(getAtendimentoSelecionado().getDoencasDiagnosticadas());
		}
		
		this.listarDoencasDiagnosticadasAtendimentosAnteriores();
		this.listarDoencasDiagnosticadasDisponiveis();
		this.listarDoencasDiagnosticadasMaisUsadas();
		
		this.statusDoencaList = Arrays.asList(StatusDoenca.values());
	}
	
	private void listarDoencasDiagnosticadasDisponiveis() {
		this.doencasDiagnosticadasDisponiveis = doencaService.retornarDoencas(Boolean.TRUE);
		
		List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAgrupado = this.getDoencasDiagnosticadasAtendimentoAgrupado();
		for (DoencaDiagnosticadaAtendimento doencaDiagnosticada : doencasDiagnosticadasAgrupado) {
			if (doencaDiagnosticada.getDoenca() != null) {
				this.doencasDiagnosticadasDisponiveis.remove(doencaDiagnosticada.getDoenca());
			}
		}
	}
	
	private void listarDoencasDiagnosticadasMaisUsadas() {
		List<Long> idsDoencasIgnorar = new ArrayList<Long>();
		for (DoencaDiagnosticadaAtendimento doencaDiagnosticadaAtendimento : getDoencasDiagnosticadasAtendimentoAgrupado()) {
			if (doencaDiagnosticadaAtendimento.getDoenca() != null) {
				idsDoencasIgnorar.add(doencaDiagnosticadaAtendimento.getDoenca().getId());
			}
		}
		
		this.doencasDiagnosticadasMaisUsadas = doencaDiagnosticadaAtendimentoService
				.retornarDoencasDiagnosticadasMaisUsadas(Constantes.QTDE_SUGESTOES_DOENCAS_DIAGNOSTICADAS, idsDoencasIgnorar, Boolean.TRUE);
	}
	
	private void listarDoencasDiagnosticadasAtendimentosAnteriores() {
		this.doencasDiagnosticadasAtendimentosAnteriores = doencaDiagnosticadaAtendimentoService
				.retornarDoencasDiagnosticadasAtendimentosAnteriores(getPacienteSelecionado().getId(), getAtendimentoSelecionado().getId());
		for (DoencaDiagnosticadaAtendimento doencaDiagnosticadaAtendimento : this.doencasDiagnosticadasAtendimentosAnteriores) {
			doencaDiagnosticadaAtendimento.setAtendimentoAnterior(true);
		}
	}
	
	public List<DoencaDiagnosticadaAtendimento> getDoencasDiagnosticadasAtendimentoAgrupado() {
		List<DoencaDiagnosticadaAtendimento> l = new ArrayList<>(this.doencasDiagnosticadasAtendimento);
		l.addAll(this.doencasDiagnosticadasAtendimentosAnteriores);
		return l;
	}
	
	public void adicionarDoencaDiagnosticada(Doenca doenca) {
		this.doencaDiagnosticadaSelecionada = doenca;
		this.adicionarDoencaDiagnosticada();
	}
	
	public void adicionarDoencaDiagnosticada() {
		if (this.doencaDiagnosticadaSelecionada != null) {
			this.doencasDiagnosticadasDisponiveis.remove(this.doencaDiagnosticadaSelecionada);
			this.doencasDiagnosticadasMaisUsadas.remove(this.doencaDiagnosticadaSelecionada);
			
			DoencaDiagnosticadaAtendimento doencaDiagnosticadaAtendimento = new DoencaDiagnosticadaAtendimento();
			doencaDiagnosticadaAtendimento.setDoenca(doencaDiagnosticadaSelecionada);
			this.doencasDiagnosticadasAtendimento.add(doencaDiagnosticadaAtendimento);

			this.doencaDiagnosticadaSelecionada = null;
			
			this.listarDoencasDiagnosticadasMaisUsadas();
		}
	}
	
	public void adicionarOutroDoencaDiagnosticada() {
		this.doencasDiagnosticadasAtendimento.add(new DoencaDiagnosticadaAtendimento());
	}
	
	public void removerDoencaDiagnosticada(int indice) {
		DoencaDiagnosticadaAtendimento doencaDiagnosticadaAtendimento = this.doencasDiagnosticadasAtendimento.get(indice);
		this.doencasDiagnosticadasAtendimento.remove(indice);
		if (doencaDiagnosticadaAtendimento.getDoenca() != null) {
			this.doencasDiagnosticadasDisponiveis.add(doencaDiagnosticadaAtendimento.getDoenca());
			
			this.listarDoencasDiagnosticadasMaisUsadas();
			this.ordenaListasDoencasDiagnosticadas();
		}
	}
	
	private void ordenaListasDoencasDiagnosticadas() {
		Comparator<Doenca> comparator = new Comparator<Doenca>() {
			@Override
			public int compare(Doenca o1, Doenca o2) {
				return o1.getCodigoCid().compareTo(o2.getCodigoCid());
			}
		};
		
		Collections.sort(this.doencasDiagnosticadasDisponiveis, comparator);
		Collections.sort(this.doencasDiagnosticadasMaisUsadas, comparator);
	}
	
	public List<Atendimento> getAtendimentosAnterioresDoencasDiagnosticadas() {
		List<Atendimento> atendimentos = new ArrayList<>();
		for (Atendimento atendimento : getAtendimentosAnteriores()) {
			if (!atendimento.getDoencasDiagnosticadas().isEmpty()
					|| StringUtils.isNotBlank(atendimento.getImpressaoDiagnostica())) {
				atendimentos.add(atendimento);
			}
		}
		return atendimentos;
	}

	public Doenca getDoencaDiagnosticadaSelecionada() {
		return doencaDiagnosticadaSelecionada;
	}

	public void setDoencaDiagnosticadaSelecionada(Doenca doencaDiagnosticadaSelecionada) {
		this.doencaDiagnosticadaSelecionada = doencaDiagnosticadaSelecionada;
	}

	public List<Doenca> getDoencasDiagnosticadasDisponiveis() {
		return doencasDiagnosticadasDisponiveis;
	}

	public void setDoencasDiagnosticadasDisponiveis(List<Doenca> doencasDiagnosticadasDisponiveis) {
		this.doencasDiagnosticadasDisponiveis = doencasDiagnosticadasDisponiveis;
	}

	public List<Doenca> getDoencasDiagnosticadasMaisUsadas() {
		return doencasDiagnosticadasMaisUsadas;
	}

	public void setDoencasDiagnosticadasMaisUsadas(List<Doenca> doencasDiagnosticadasMaisUsadas) {
		this.doencasDiagnosticadasMaisUsadas = doencasDiagnosticadasMaisUsadas;
	}

	public List<DoencaDiagnosticadaAtendimento> getDoencasDiagnosticadasAtendimento() {
		return doencasDiagnosticadasAtendimento;
	}

	public void setDoencasDiagnosticadasAtendimento(List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAtendimento) {
		this.doencasDiagnosticadasAtendimento = doencasDiagnosticadasAtendimento;
	}

	public List<DoencaDiagnosticadaAtendimento> getDoencasDiagnosticadasAtendimentosAnteriores() {
		return doencasDiagnosticadasAtendimentosAnteriores;
	}

	public void setDoencasDiagnosticadasAtendimentosAnteriores(List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAtendimentosAnteriores) {
		this.doencasDiagnosticadasAtendimentosAnteriores = doencasDiagnosticadasAtendimentosAnteriores;
	}

	public List<StatusDoenca> getStatusDoencaList() {
		return statusDoencaList;
	}
}
