package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.constants.TipoProcedimento;
import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.AntecedenteCirurgicoAtendimento;
import br.edu.utfpr.model.Procedimento;
import br.edu.utfpr.service.AntecedenteCirurgicoAtendimentoService;
import br.edu.utfpr.service.ProcedimentoService;

public class AntecedentesCirurgicosViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProcedimentoService procedimentoService;
	@Inject
	private AntecedenteCirurgicoAtendimentoService antecedenteCirurgicoAtendimentoService;
	
	private Procedimento antecedenteCirurgicoSelecionado;
	private List<Procedimento> antecedentesCirurgicosDisponiveis;
	private List<Procedimento> antecedentesCirurgicosMaisUsados;
	private List<AntecedenteCirurgicoAtendimento> antecedentesCirurgicosAtendimento;
	private List<AntecedenteCirurgicoAtendimento> antecedentesCirurgicosAtendimentosAnteriores;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (getAtendimentoSelecionado().isNew()) {
			this.antecedentesCirurgicosAtendimento = new ArrayList<AntecedenteCirurgicoAtendimento>();
		} else {
			this.antecedentesCirurgicosAtendimento = new ArrayList<AntecedenteCirurgicoAtendimento>(getAtendimentoSelecionado().getAntecedentesCirurgicos());
		}
		
		this.listarAntecedentesCirurgicosAtendimentosAnteriores();
		this.listarAntecedentesCirurgicosDisponiveis();
		this.listarAntecedentesCirurgicosMaisUsados();
	}
	
	private void listarAntecedentesCirurgicosDisponiveis() {
		this.antecedentesCirurgicosDisponiveis = procedimentoService.retornarProcedimentos(Boolean.TRUE, TipoProcedimento.CIRURGICO);
		
		List<AntecedenteCirurgicoAtendimento> antecedentesCirurgicosAgrupado = this.getAntecedentesCirurgicosAtendimentoAgrupado();
		for (AntecedenteCirurgicoAtendimento antecedenteCirurgico : antecedentesCirurgicosAgrupado) {
			if (antecedenteCirurgico.getProcedimento() != null) {
				this.antecedentesCirurgicosDisponiveis.remove(antecedenteCirurgico.getProcedimento());
			}
		}
	}
	
	private void listarAntecedentesCirurgicosMaisUsados() {
		List<Long> idsProcedimentosIgnorar = new ArrayList<Long>();
		for (AntecedenteCirurgicoAtendimento antecedenteCirurgicoAtendimento : getAntecedentesCirurgicosAtendimentoAgrupado()) {
			if (antecedenteCirurgicoAtendimento.getProcedimento() != null) {
				idsProcedimentosIgnorar.add(antecedenteCirurgicoAtendimento.getProcedimento().getId());
			}
		}
		
		this.antecedentesCirurgicosMaisUsados = antecedenteCirurgicoAtendimentoService
				.retornarAntecedentesCirurgicosMaisUsados(Constantes.QTDE_SUGESTOES_ANTECEDENTES_CIRURGICOS, idsProcedimentosIgnorar, Boolean.TRUE, TipoProcedimento.CIRURGICO);
	}
	
	private void listarAntecedentesCirurgicosAtendimentosAnteriores() {
		this.antecedentesCirurgicosAtendimentosAnteriores = antecedenteCirurgicoAtendimentoService
				.retornarAntecedentesCirurgicosAtendimentosAnteriores(getPacienteSelecionado().getId(), getAtendimentoSelecionado().getId());
		for (AntecedenteCirurgicoAtendimento antecedenteCirurgicoAtendimento : this.antecedentesCirurgicosAtendimentosAnteriores) {
			antecedenteCirurgicoAtendimento.setAtendimentoAnterior(true);
		}
	}
	
	public List<AntecedenteCirurgicoAtendimento> getAntecedentesCirurgicosAtendimentoAgrupado() {
		List<AntecedenteCirurgicoAtendimento> l = new ArrayList<>(this.antecedentesCirurgicosAtendimento);
		l.addAll(this.antecedentesCirurgicosAtendimentosAnteriores);
		return l;
	}
	
	public void adicionarAntecedenteCirurgico(Procedimento procedimento) {
		this.antecedenteCirurgicoSelecionado = procedimento;
		this.adicionarAntecedenteCirurgico();
	}
	
	public void adicionarAntecedenteCirurgico() {
		if (this.antecedenteCirurgicoSelecionado != null) {
			this.antecedentesCirurgicosDisponiveis.remove(this.antecedenteCirurgicoSelecionado);
			this.antecedentesCirurgicosMaisUsados.remove(this.antecedenteCirurgicoSelecionado);
			
			AntecedenteCirurgicoAtendimento antecedenteCirurgicoAtendimento = new AntecedenteCirurgicoAtendimento();
			antecedenteCirurgicoAtendimento.setProcedimento(antecedenteCirurgicoSelecionado);
			this.antecedentesCirurgicosAtendimento.add(antecedenteCirurgicoAtendimento);

			this.antecedenteCirurgicoSelecionado = null;
			
			this.listarAntecedentesCirurgicosMaisUsados();
		}
	}
	
	public void adicionarOutroAntecedenteCirurgico() {
		this.antecedentesCirurgicosAtendimento.add(new AntecedenteCirurgicoAtendimento());
	}
	
	public void removerAntecedenteCirurgico(int indice) {
		AntecedenteCirurgicoAtendimento antecedenteCirurgicoAtendimento = this.antecedentesCirurgicosAtendimento.get(indice);
		this.antecedentesCirurgicosAtendimento.remove(indice);
		if (antecedenteCirurgicoAtendimento.getProcedimento() != null) {
			this.antecedentesCirurgicosDisponiveis.add(antecedenteCirurgicoAtendimento.getProcedimento());
			
			this.listarAntecedentesCirurgicosMaisUsados();
			this.ordenaListasAntecedentesCirurgicos();
		}
	}
	
	private void ordenaListasAntecedentesCirurgicos() {
		Comparator<Procedimento> comparator = new Comparator<Procedimento>() {
			@Override
			public int compare(Procedimento o1, Procedimento o2) {
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		};
		
		Collections.sort(this.antecedentesCirurgicosDisponiveis, comparator);
		Collections.sort(this.antecedentesCirurgicosMaisUsados, comparator);
	}
	
	public Procedimento getAntecedenteCirurgicoSelecionado() {
		return antecedenteCirurgicoSelecionado;
	}

	public void setAntecedenteCirurgicoSelecionado(Procedimento antecedenteCirurgicoSelecionado) {
		this.antecedenteCirurgicoSelecionado = antecedenteCirurgicoSelecionado;
	}

	public List<Procedimento> getAntecedentesCirurgicosDisponiveis() {
		return antecedentesCirurgicosDisponiveis;
	}

	public void setAntecedentesCirurgicosDisponiveis(List<Procedimento> antecedentesCirurgicosDisponiveis) {
		this.antecedentesCirurgicosDisponiveis = antecedentesCirurgicosDisponiveis;
	}

	public List<Procedimento> getAntecedentesCirurgicosMaisUsados() {
		return antecedentesCirurgicosMaisUsados;
	}

	public void setAntecedentesCirurgicosMaisUsados(List<Procedimento> antecedentesCirurgicosMaisUsados) {
		this.antecedentesCirurgicosMaisUsados = antecedentesCirurgicosMaisUsados;
	}

	public List<AntecedenteCirurgicoAtendimento> getAntecedentesCirurgicosAtendimento() {
		return antecedentesCirurgicosAtendimento;
	}

	public void setAntecedentesCirurgicosAtendimento(List<AntecedenteCirurgicoAtendimento> antecedentesCirurgicosAtendimento) {
		this.antecedentesCirurgicosAtendimento = antecedentesCirurgicosAtendimento;
	}

	public List<AntecedenteCirurgicoAtendimento> getAntecedentesCirurgicosAtendimentosAnteriores() {
		return antecedentesCirurgicosAtendimentosAnteriores;
	}

	public void setAntecedentesCirurgicosAtendimentosAnteriores(List<AntecedenteCirurgicoAtendimento> antecedentesCirurgicosAtendimentosAnteriores) {
		this.antecedentesCirurgicosAtendimentosAnteriores = antecedentesCirurgicosAtendimentosAnteriores;
	}
}
