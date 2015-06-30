package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.ExameFisicoAtendimento;

public class ExameFisicoViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		if (this.getAtendimentoSelecionado().isNew()) {
			this.getAtendimentoSelecionado().setExameFisicoAtendimento(new ExameFisicoAtendimento());
		}
	}
	
	public List<Atendimento> getAtendimentosAnterioresExamesFisicos() {
		List<Atendimento> atendimentos = new ArrayList<>();
		for (Atendimento atendimento : getAtendimentosAnteriores()) {
			ExameFisicoAtendimento exame = atendimento.getExameFisicoAtendimento();
			if (exame != null && exame.hasSomeValue()) {
				atendimentos.add(atendimento);
			}
		}
		return atendimentos;
	}
}
