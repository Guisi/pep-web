package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Alergia;
import br.edu.utfpr.model.AlergiaAtendimento;
import br.edu.utfpr.service.AlergiaAtendimentoService;
import br.edu.utfpr.service.AlergiaService;

public class AlergiasViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlergiaService alergiaService;
	@Inject
	private AlergiaAtendimentoService alergiaAtendimentoService;
	
	private Alergia alergiaSelecionada;
	private List<Alergia> alergiasDisponiveis;
	private List<Alergia> alergiasMaisUsadas;
	private List<AlergiaAtendimento> alergiasAtendimento;
	private List<AlergiaAtendimento> alergiasAtendimentosAnteriores;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (getAtendimentoSelecionado().isNew()) {
			this.alergiasAtendimento = new ArrayList<AlergiaAtendimento>();
		} else {
			this.alergiasAtendimento = new ArrayList<AlergiaAtendimento>(getAtendimentoSelecionado().getAlergias());
		}
		
		this.listarAlergiasAtendimentosAnteriores();
		this.listarAlergiasDisponiveis();
		this.listarAlergiasMaisUsadas();
	}
	
	private void listarAlergiasDisponiveis() {
		this.alergiasDisponiveis = alergiaService.retornarAlergias(Boolean.TRUE);
		
		List<AlergiaAtendimento> alergiasAgrupado = this.getAlergiasAtendimentoAgrupado();
		for (AlergiaAtendimento alergiaAtendimento : alergiasAgrupado) {
			if (alergiaAtendimento.getAlergia() != null) {
				this.alergiasDisponiveis.remove(alergiaAtendimento.getAlergia());
			}
		}
	}
	
	private void listarAlergiasMaisUsadas() {
		List<Long> idsAlergiasIgnorar = new ArrayList<Long>();
		for (AlergiaAtendimento alergiaAtendimento : getAlergiasAtendimentoAgrupado()) {
			if (alergiaAtendimento.getAlergia() != null) {
				idsAlergiasIgnorar.add(alergiaAtendimento.getAlergia().getId());
			}
		}
		
		this.alergiasMaisUsadas = alergiaAtendimentoService
				.retornarAlergiasMaisUsadas(Constantes.QTDE_SUGESTOES_ALERGIAS, idsAlergiasIgnorar, Boolean.TRUE);
	}
	
	private void listarAlergiasAtendimentosAnteriores() {
		this.alergiasAtendimentosAnteriores = alergiaAtendimentoService
				.retornarAlergiasAtendimentosAnteriores(getPacienteSelecionado().getId(), getAtendimentoSelecionado().getId());
		for (AlergiaAtendimento alergiaAtendimento : this.alergiasAtendimentosAnteriores) {
			alergiaAtendimento.setAtendimentoAnterior(true);
		}
	}
	
	public List<AlergiaAtendimento> getAlergiasAtendimentoAgrupado() {
		List<AlergiaAtendimento> l = new ArrayList<>(this.alergiasAtendimento);
		l.addAll(this.alergiasAtendimentosAnteriores);
		return l;
	}
	
	public void adicionarAlergia(Alergia alergia) {
		this.alergiaSelecionada = alergia;
		this.adicionarAlergia();
	}
	
	public void adicionarAlergia() {
		if (this.alergiaSelecionada != null) {
			this.alergiasDisponiveis.remove(this.alergiaSelecionada);
			this.alergiasMaisUsadas.remove(this.alergiaSelecionada);
			
			AlergiaAtendimento alergiaAtendimento = new AlergiaAtendimento();
			alergiaAtendimento.setAlergia(alergiaSelecionada);
			this.alergiasAtendimento.add(alergiaAtendimento);

			this.alergiaSelecionada = null;
			
			this.listarAlergiasMaisUsadas();
		}
	}
	
	public void adicionarOutraAlergia() {
		this.alergiasAtendimento.add(new AlergiaAtendimento());
	}
	
	public void removerAlergia(int indice) {
		AlergiaAtendimento alergiaAtendimento = this.alergiasAtendimento.get(indice);
		this.alergiasAtendimento.remove(indice);
		if (alergiaAtendimento.getAlergia() != null) {
			this.alergiasDisponiveis.add(alergiaAtendimento.getAlergia());
			
			this.listarAlergiasMaisUsadas();
			this.ordenaListasAlergias();
		}
	}
	
	private void ordenaListasAlergias() {
		Comparator<Alergia> comparator = new Comparator<Alergia>() {
			@Override
			public int compare(Alergia o1, Alergia o2) {
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		};
		
		Collections.sort(this.alergiasDisponiveis, comparator);
		Collections.sort(this.alergiasMaisUsadas, comparator);
	}

	public List<Alergia> getAlergiasDisponiveis() {
		return alergiasDisponiveis;
	}

	public void setAlergiasDisponiveis(List<Alergia> alergiasDisponiveis) {
		this.alergiasDisponiveis = alergiasDisponiveis;
	}

	public List<AlergiaAtendimento> getAlergiasAtendimento() {
		return alergiasAtendimento;
	}

	public void setAlergiasAtendimento(List<AlergiaAtendimento> alergiasAtendimento) {
		this.alergiasAtendimento = alergiasAtendimento;
	}

	public List<AlergiaAtendimento> getAlergiasAtendimentosAnteriores() {
		return alergiasAtendimentosAnteriores;
	}

	public void setAlergiasAtendimentosAnteriores(List<AlergiaAtendimento> alergiasAtendimentosAnteriores) {
		this.alergiasAtendimentosAnteriores = alergiasAtendimentosAnteriores;
	}

	public Alergia getAlergiaSelecionada() {
		return alergiaSelecionada;
	}

	public void setAlergiaSelecionada(Alergia alergiaSelecionada) {
		this.alergiaSelecionada = alergiaSelecionada;
	}

	public List<Alergia> getAlergiasMaisUsadas() {
		return alergiasMaisUsadas;
	}

	public void setAlergiasMaisUsadas(List<Alergia> alergiasMaisUsadas) {
		this.alergiasMaisUsadas = alergiasMaisUsadas;
	}
	
}
