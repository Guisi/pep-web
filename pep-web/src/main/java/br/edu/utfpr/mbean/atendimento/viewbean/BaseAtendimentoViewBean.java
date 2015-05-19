package br.edu.utfpr.mbean.atendimento.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Usuario;

public abstract class BaseAtendimentoViewBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EditarAtendimentoMBean mbean;
	
	public void init(EditarAtendimentoMBean mbean) {
		this.mbean = mbean;
	}
	
	public Atendimento getAtendimentoSelecionado() {
		return mbean.getAtendimentoSelecionado();
	}
	
	public Usuario getPacienteSelecionado() {
		return mbean.getPacienteSelecionado();
	}
	
	public List<Atendimento> getAtendimentosAnteriores() {
		return mbean.getAtendimentosAnteriores();
	}

	protected EditarAtendimentoMBean getMbean() {
		return mbean;
	}
	
	public List<Atendimento> getAtendimentosAnterioresAntecedentesPessoais() {
		List<Atendimento> atendimentos = new ArrayList<>();
		for (Atendimento atendimento : getAtendimentosAnteriores()) {
			if (!atendimento.getAntecedentesClinicos().isEmpty()
					|| !atendimento.getAntecedentesCirurgicos().isEmpty()
					|| !atendimento.getHabitos().isEmpty()) {
				atendimentos.add(atendimento);
			}
		}
		return atendimentos;
	}

}