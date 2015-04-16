package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.List;

import br.edu.utfpr.model.Doenca;

public class AntecedentesPessoaisViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;

	private Doenca antecedenteClinicoSelecionado;
	private List<Doenca> antecedentesClinicosDisponiveis;
	
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
}
