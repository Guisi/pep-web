package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.AntecedenteFamiliarAtendimento;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.service.AntecedenteFamiliarAtendimentoService;
import br.edu.utfpr.service.DoencaService;

public class AntecedentesFamiliaresViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DoencaService doencaService;
	@Inject
	private AntecedenteFamiliarAtendimentoService antecedenteFamiliarAtendimentoService;
	
	private Doenca antecedenteFamiliarSelecionado;
	private List<Doenca> antecedentesFamiliaresDisponiveis;
	private List<Doenca> antecedentesFamiliaresMaisUsados;
	private List<AntecedenteFamiliarAtendimento> antecedentesFamiliaresAtendimento;
	private List<AntecedenteFamiliarAtendimento> antecedentesFamiliaresAtendimentosAnteriores;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (getAtendimentoSelecionado().isNew()) {
			this.antecedentesFamiliaresAtendimento = new ArrayList<AntecedenteFamiliarAtendimento>();
		} else {
			this.antecedentesFamiliaresAtendimento = new ArrayList<AntecedenteFamiliarAtendimento>(getAtendimentoSelecionado().getAntecedentesFamiliares());
		}
		
		this.listarAntecedentesFamiliaresAtendimentosAnteriores();
		this.listarAntecedentesFamiliaresDisponiveis();
		this.listarAntecedentesFamiliaresMaisUsados();
	}
	
	private void listarAntecedentesFamiliaresDisponiveis() {
		this.antecedentesFamiliaresDisponiveis = doencaService.retornarDoencas(Boolean.TRUE);
		
		List<AntecedenteFamiliarAtendimento> antecedentesFamiliaresAgrupado = this.getAntecedentesFamiliaresAtendimentoAgrupado();
		for (AntecedenteFamiliarAtendimento antecedenteFamiliar : antecedentesFamiliaresAgrupado) {
			if (antecedenteFamiliar.getDoenca() != null) {
				this.antecedentesFamiliaresDisponiveis.remove(antecedenteFamiliar.getDoenca());
			}
		}
	}
	
	private void listarAntecedentesFamiliaresMaisUsados() {
		List<Long> idsDoencasIgnorar = new ArrayList<Long>();
		for (AntecedenteFamiliarAtendimento antecedenteFamiliarAtendimento : getAntecedentesFamiliaresAtendimentoAgrupado()) {
			if (antecedenteFamiliarAtendimento.getDoenca() != null) {
				idsDoencasIgnorar.add(antecedenteFamiliarAtendimento.getDoenca().getId());
			}
		}
		
		this.antecedentesFamiliaresMaisUsados = antecedenteFamiliarAtendimentoService
				.retornarAntecedentesFamiliaresMaisUsados(Constantes.QTDE_SUGESTOES_ANTECEDENTES_FAMILIARES, idsDoencasIgnorar, Boolean.TRUE);
	}
	
	private void listarAntecedentesFamiliaresAtendimentosAnteriores() {
		this.antecedentesFamiliaresAtendimentosAnteriores = antecedenteFamiliarAtendimentoService
				.retornarAntecedentesFamiliaresAtendimentosAnteriores(getPacienteSelecionado().getId(), getAtendimentoSelecionado().getId());
		for (AntecedenteFamiliarAtendimento antecedenteFamiliarAtendimento : this.antecedentesFamiliaresAtendimentosAnteriores) {
			antecedenteFamiliarAtendimento.setAtendimentoAnterior(true);
		}
	}
	
	public List<AntecedenteFamiliarAtendimento> getAntecedentesFamiliaresAtendimentoAgrupado() {
		List<AntecedenteFamiliarAtendimento> l = new ArrayList<>(this.antecedentesFamiliaresAtendimento);
		l.addAll(this.antecedentesFamiliaresAtendimentosAnteriores);
		return l;
	}
	
	public void adicionarAntecedenteFamiliar(Doenca doenca) {
		this.antecedenteFamiliarSelecionado = doenca;
		this.adicionarAntecedenteFamiliar();
	}
	
	public void adicionarAntecedenteFamiliar() {
		if (this.antecedenteFamiliarSelecionado != null) {
			this.antecedentesFamiliaresDisponiveis.remove(this.antecedenteFamiliarSelecionado);
			this.antecedentesFamiliaresMaisUsados.remove(this.antecedenteFamiliarSelecionado);
			
			AntecedenteFamiliarAtendimento antecedenteFamiliarAtendimento = new AntecedenteFamiliarAtendimento();
			antecedenteFamiliarAtendimento.setDoenca(antecedenteFamiliarSelecionado);
			this.antecedentesFamiliaresAtendimento.add(antecedenteFamiliarAtendimento);

			this.antecedenteFamiliarSelecionado = null;
			
			this.listarAntecedentesFamiliaresMaisUsados();
		}
	}
	
	public void adicionarOutroAntecedenteFamiliar() {
		this.antecedentesFamiliaresAtendimento.add(new AntecedenteFamiliarAtendimento());
	}
	
	public void removerAntecedenteFamiliar(int indice) {
		AntecedenteFamiliarAtendimento antecedenteFamiliarAtendimento = this.antecedentesFamiliaresAtendimento.get(indice);
		this.antecedentesFamiliaresAtendimento.remove(indice);
		if (antecedenteFamiliarAtendimento.getDoenca() != null) {
			this.antecedentesFamiliaresDisponiveis.add(antecedenteFamiliarAtendimento.getDoenca());
			
			this.listarAntecedentesFamiliaresMaisUsados();
			this.ordenaListasAntecedentesFamiliares();
		}
	}
	
	private void ordenaListasAntecedentesFamiliares() {
		Comparator<Doenca> comparator = new Comparator<Doenca>() {
			@Override
			public int compare(Doenca o1, Doenca o2) {
				return o1.getCodigoCid().compareTo(o2.getCodigoCid());
			}
		};
		
		Collections.sort(this.antecedentesFamiliaresDisponiveis, comparator);
		Collections.sort(this.antecedentesFamiliaresMaisUsados, comparator);
	}
	
	public Doenca getAntecedenteFamiliarSelecionado() {
		return antecedenteFamiliarSelecionado;
	}
	
	public void setAntecedenteFamiliarSelecionado(Doenca antecedenteFamiliarSelecionado) {
		this.antecedenteFamiliarSelecionado = antecedenteFamiliarSelecionado;
	}
	
	public List<Doenca> getAntecedentesFamiliaresDisponiveis() {
		return antecedentesFamiliaresDisponiveis;
	}
	
	public void setAntecedentesFamiliaresDisponiveis(List<Doenca> antecedentesFamiliaresDisponiveis) {
		this.antecedentesFamiliaresDisponiveis = antecedentesFamiliaresDisponiveis;
	}

	public List<AntecedenteFamiliarAtendimento> getAntecedentesFamiliaresAtendimento() {
		return antecedentesFamiliaresAtendimento;
	}

	public void setAntecedentesFamiliaresAtendimento(List<AntecedenteFamiliarAtendimento> antecedentesFamiliaresAtendimento) {
		this.antecedentesFamiliaresAtendimento = antecedentesFamiliaresAtendimento;
	}

	public List<AntecedenteFamiliarAtendimento> getAntecedentesFamiliaresAtendimentosAnteriores() {
		return antecedentesFamiliaresAtendimentosAnteriores;
	}

	public void setAntecedentesFamiliaresAtendimentosAnteriores(List<AntecedenteFamiliarAtendimento> antecedentesFamiliaresAtendimentosAnteriores) {
		this.antecedentesFamiliaresAtendimentosAnteriores = antecedentesFamiliaresAtendimentosAnteriores;
	}

	public List<Doenca> getAntecedentesFamiliaresMaisUsados() {
		return antecedentesFamiliaresMaisUsados;
	}

	public void setAntecedentesFamiliaresMaisUsados(List<Doenca> antecedentesFamiliaresMaisUsados) {
		this.antecedentesFamiliaresMaisUsados = antecedentesFamiliaresMaisUsados;
	}
}
