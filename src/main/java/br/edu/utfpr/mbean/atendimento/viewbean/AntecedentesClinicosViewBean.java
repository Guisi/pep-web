package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.service.AntecedenteClinicoAtendimentoService;
import br.edu.utfpr.service.DoencaService;

public class AntecedentesClinicosViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DoencaService doencaService;
	@Inject
	private AntecedenteClinicoAtendimentoService antecedenteClinicoAtendimentoService;
	
	private Doenca antecedenteClinicoSelecionado;
	private List<Doenca> antecedentesClinicosDisponiveis;
	private List<Doenca> antecedentesClinicosMaisUsados;
	private List<AntecedenteClinicoAtendimento> antecedentesClinicosAtendimento;
	private List<AntecedenteClinicoAtendimento> antecedentesClinicosAtendimentosAnteriores;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (getAtendimentoSelecionado().isNew()) {
			this.antecedentesClinicosAtendimento = new ArrayList<AntecedenteClinicoAtendimento>();
		} else {
			this.antecedentesClinicosAtendimento = new ArrayList<AntecedenteClinicoAtendimento>(getAtendimentoSelecionado().getAntecedentesClinicos());
		}
		
		this.listarAntecedentesClinicosAtendimentosAnteriores();
		this.listarAntecedentesClinicosDisponiveis();
		this.listarAntecedentesClinicosMaisUsados();
	}
	
	private void listarAntecedentesClinicosDisponiveis() {
		this.antecedentesClinicosDisponiveis = doencaService.retornarDoencas(Boolean.TRUE);
		
		List<AntecedenteClinicoAtendimento> antecedentesClinicosAgrupado = this.getAntecedentesClinicosAtendimentoAgrupado();
		for (AntecedenteClinicoAtendimento antecedenteClinico : antecedentesClinicosAgrupado) {
			if (antecedenteClinico.getDoenca() != null) {
				this.antecedentesClinicosDisponiveis.remove(antecedenteClinico.getDoenca());
			}
		}
	}
	
	private void listarAntecedentesClinicosMaisUsados() {
		List<Long> idsDoencasIgnorar = new ArrayList<Long>();
		for (AntecedenteClinicoAtendimento antecedenteClinicoAtendimento : getAntecedentesClinicosAtendimentoAgrupado()) {
			if (antecedenteClinicoAtendimento.getDoenca() != null) {
				idsDoencasIgnorar.add(antecedenteClinicoAtendimento.getDoenca().getId());
			}
		}
		
		this.antecedentesClinicosMaisUsados = antecedenteClinicoAtendimentoService
				.retornarAntecedentesClinicosMaisUsados(Constantes.QTDE_SUGESTOES_ANTECEDENTES_CLINICOS, idsDoencasIgnorar, Boolean.TRUE);
	}
	
	private void listarAntecedentesClinicosAtendimentosAnteriores() {
		this.antecedentesClinicosAtendimentosAnteriores = antecedenteClinicoAtendimentoService
				.retornarAntecedentesClinicosAtendimentosAnteriores(getPacienteSelecionado().getId(), getAtendimentoSelecionado().getId());
		for (AntecedenteClinicoAtendimento antecedenteClinicoAtendimento : this.antecedentesClinicosAtendimentosAnteriores) {
			antecedenteClinicoAtendimento.setAtendimentoAnterior(true);
		}
	}
	
	public List<AntecedenteClinicoAtendimento> getAntecedentesClinicosAtendimentoAgrupado() {
		List<AntecedenteClinicoAtendimento> l = new ArrayList<>(this.antecedentesClinicosAtendimento);
		l.addAll(this.antecedentesClinicosAtendimentosAnteriores);
		return l;
	}
	
	public void adicionarAntecedenteClinico(Doenca doenca) {
		this.antecedenteClinicoSelecionado = doenca;
		this.adicionarAntecedenteClinico();
	}
	
	public void adicionarAntecedenteClinico() {
		if (this.antecedenteClinicoSelecionado != null) {
			this.antecedentesClinicosDisponiveis.remove(this.antecedenteClinicoSelecionado);
			this.antecedentesClinicosMaisUsados.remove(this.antecedenteClinicoSelecionado);
			
			AntecedenteClinicoAtendimento antecedenteClinicoAtendimento = new AntecedenteClinicoAtendimento();
			antecedenteClinicoAtendimento.setDoenca(antecedenteClinicoSelecionado);
			this.antecedentesClinicosAtendimento.add(antecedenteClinicoAtendimento);

			this.antecedenteClinicoSelecionado = null;
			
			this.listarAntecedentesClinicosMaisUsados();
		}
	}
	
	public void adicionarOutroAntecedenteClinico() {
		this.antecedentesClinicosAtendimento.add(new AntecedenteClinicoAtendimento());
	}
	
	public void removerAntecedenteClinico(int indice) {
		AntecedenteClinicoAtendimento antecedenteClinicoAtendimento = this.antecedentesClinicosAtendimento.get(indice);
		this.antecedentesClinicosAtendimento.remove(indice);
		if (antecedenteClinicoAtendimento.getDoenca() != null) {
			this.antecedentesClinicosDisponiveis.add(antecedenteClinicoAtendimento.getDoenca());
			
			this.listarAntecedentesClinicosMaisUsados();
			this.ordenaListasAntecedentesClinicos();
		}
	}
	
	private void ordenaListasAntecedentesClinicos() {
		Comparator<Doenca> comparator = new Comparator<Doenca>() {
			@Override
			public int compare(Doenca o1, Doenca o2) {
				return o1.getCodigoCid().compareTo(o2.getCodigoCid());
			}
		};
		
		Collections.sort(this.antecedentesClinicosDisponiveis, comparator);
		Collections.sort(this.antecedentesClinicosMaisUsados, comparator);
	}
	
	public Doenca getAntecedenteClinicoSelecionado() {
		return antecedenteClinicoSelecionado;
	}
	
	public void setAntecedenteClinicoSelecionado(Doenca antecedenteClinicoSelecionado) {
		this.antecedenteClinicoSelecionado = antecedenteClinicoSelecionado;
	}
	
	public List<Doenca> getAntecedentesClinicosDisponiveis() {
		return antecedentesClinicosDisponiveis;
	}
	
	public void setAntecedentesClinicosDisponiveis(List<Doenca> antecedentesClinicosDisponiveis) {
		this.antecedentesClinicosDisponiveis = antecedentesClinicosDisponiveis;
	}

	public List<AntecedenteClinicoAtendimento> getAntecedentesClinicosAtendimento() {
		return antecedentesClinicosAtendimento;
	}

	public void setAntecedentesClinicosAtendimento(List<AntecedenteClinicoAtendimento> antecedentesClinicosAtendimento) {
		this.antecedentesClinicosAtendimento = antecedentesClinicosAtendimento;
	}

	public List<AntecedenteClinicoAtendimento> getAntecedentesClinicosAtendimentosAnteriores() {
		return antecedentesClinicosAtendimentosAnteriores;
	}

	public void setAntecedentesClinicosAtendimentosAnteriores(List<AntecedenteClinicoAtendimento> antecedentesClinicosAtendimentosAnteriores) {
		this.antecedentesClinicosAtendimentosAnteriores = antecedentesClinicosAtendimentosAnteriores;
	}

	public List<Doenca> getAntecedentesClinicosMaisUsados() {
		return antecedentesClinicosMaisUsados;
	}

	public void setAntecedentesClinicosMaisUsados(List<Doenca> antecedentesClinicosMaisUsados) {
		this.antecedentesClinicosMaisUsados = antecedentesClinicosMaisUsados;
	}
}
