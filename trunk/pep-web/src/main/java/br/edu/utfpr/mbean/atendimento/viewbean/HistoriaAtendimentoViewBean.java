package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.QueixaPrincipal;
import br.edu.utfpr.model.QueixaPrincipalAtendimento;
import br.edu.utfpr.service.QueixaPrincipalService;

@Named
public class HistoriaAtendimentoViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private QueixaPrincipalService queixaPrincipalService;
	
	private static final Integer QTDE_SUGESTOES_QUEIXAS = 10;
	
	private QueixaPrincipal queixaPrincipalSelecionada;
	private List<QueixaPrincipal> queixasPrincipaisDisponiveis;
	private List<QueixaPrincipal> queixasPrincipaisMaisUsadas;
	private List<QueixaPrincipalAtendimento> queixasPrincipaisAtendimento;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (mbean.getAtendimentoSelecionado().isNew()) {
			this.queixasPrincipaisAtendimento = new ArrayList<QueixaPrincipalAtendimento>();
		} else {
			this.queixasPrincipaisAtendimento = new ArrayList<>(getAtendimentoSelecionado().getQueixasPrincipais());
		}

		this.setQueixasPrincipaisAtendimento(queixasPrincipaisAtendimento);
		this.listarQueixasPrincipaisDisponiveis();
		this.listarQueixasPrincipaisMaisUsadas();
	}
	
	/** Historia e motivo do atendimento */
	private void listarQueixasPrincipaisDisponiveis() {
		this.queixasPrincipaisDisponiveis = queixaPrincipalService.retornarQueixasPrincipais(Boolean.TRUE);
		
		for (QueixaPrincipalAtendimento queixaPrincipalAtendimento : queixasPrincipaisAtendimento) {
			if (queixaPrincipalAtendimento.getQueixaPrincipal() != null) {
				this.queixasPrincipaisDisponiveis.remove(queixaPrincipalAtendimento.getQueixaPrincipal());
			}
		}
	}
	
	private void listarQueixasPrincipaisMaisUsadas() {
		List<Long> idsQueixasIgnorar = new ArrayList<Long>();
		for (QueixaPrincipalAtendimento queixaPrincipalAtendimento : queixasPrincipaisAtendimento) {
			if (queixaPrincipalAtendimento.getQueixaPrincipal() != null) {
				idsQueixasIgnorar.add(queixaPrincipalAtendimento.getQueixaPrincipal().getId());
			}
		}
		
		this.queixasPrincipaisMaisUsadas = queixaPrincipalService.retornarQueixasPrincipaisMaisUsadas(QTDE_SUGESTOES_QUEIXAS, idsQueixasIgnorar, Boolean.TRUE);
		
		/*for (QueixaPrincipalAtendimento queixaPrincipalAtendimento : queixasPrincipaisAtendimento) {
			if (queixaPrincipalAtendimento.getQueixaPrincipal() != null) {
				this.queixasPrincipaisMaisUsadas.remove(queixaPrincipalAtendimento.getQueixaPrincipal());
			}
		}*/
	}
	
	public void adicionarQueixaPrincipal(QueixaPrincipal queixaPrincipal) {
		this.queixaPrincipalSelecionada = queixaPrincipal;
		this.adicionarQueixaPrincipal();
	}
	
	public void adicionarQueixaPrincipal() {
		if (this.queixaPrincipalSelecionada != null) {
			this.queixasPrincipaisDisponiveis.remove(this.queixaPrincipalSelecionada);
			this.queixasPrincipaisMaisUsadas.remove(this.queixaPrincipalSelecionada);
			
			QueixaPrincipalAtendimento queixaPrincipalAtendimento = new QueixaPrincipalAtendimento();
			queixaPrincipalAtendimento.setQueixaPrincipal(this.queixaPrincipalSelecionada);
			this.queixasPrincipaisAtendimento.add(queixaPrincipalAtendimento);

			this.queixaPrincipalSelecionada = null;
			
			this.listarQueixasPrincipaisMaisUsadas();
		}
	}
	
	public void removerQueixaPrincipal(int indice) {
		QueixaPrincipalAtendimento queixaPrincipalAtendimento = this.queixasPrincipaisAtendimento.get(indice);
		this.queixasPrincipaisAtendimento.remove(indice);
		if (queixaPrincipalAtendimento.getQueixaPrincipal() != null) {
			this.queixasPrincipaisDisponiveis.add(queixaPrincipalAtendimento.getQueixaPrincipal());
			
			this.listarQueixasPrincipaisMaisUsadas();
			this.ordenaListasQueixasPrincipais();
		}
	}
	
	private void ordenaListasQueixasPrincipais() {
		Comparator<QueixaPrincipal> comparator = new Comparator<QueixaPrincipal>() {
			@Override
			public int compare(QueixaPrincipal o1, QueixaPrincipal o2) {
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		};
		
		Collections.sort(this.queixasPrincipaisDisponiveis, comparator);
		Collections.sort(this.queixasPrincipaisMaisUsadas, comparator);
	}
	
	public List<Atendimento> getAtendimentosAnterioresHistoriaMotivo() {
		List<Atendimento> atendimentos = new ArrayList<>();
		for (Atendimento atendimento : getAtendimentosAnteriores()) {
			if (!atendimento.getQueixasPrincipais().isEmpty()
					|| StringUtils.isNotBlank(atendimento.getHistoriaDoencaAtual())
					|| StringUtils.isNotBlank(atendimento.getIsda())) {
				atendimentos.add(atendimento);
			}
		}
		return atendimentos;
	}
	
	public void adicionarOutraQueixaPrincipal() {
		this.queixasPrincipaisAtendimento.add(new QueixaPrincipalAtendimento());
	}

	public QueixaPrincipal getQueixaPrincipalSelecionada() {
		return queixaPrincipalSelecionada;
	}

	public void setQueixaPrincipalSelecionada(QueixaPrincipal queixaPrincipalSelecionada) {
		this.queixaPrincipalSelecionada = queixaPrincipalSelecionada;
	}

	public List<QueixaPrincipal> getQueixasPrincipaisDisponiveis() {
		return queixasPrincipaisDisponiveis;
	}

	public void setQueixasPrincipaisDisponiveis(List<QueixaPrincipal> queixasPrincipaisDisponiveis) {
		this.queixasPrincipaisDisponiveis = queixasPrincipaisDisponiveis;
	}

	public List<QueixaPrincipal> getQueixasPrincipaisMaisUsadas() {
		return queixasPrincipaisMaisUsadas;
	}

	public void setQueixasPrincipaisMaisUsadas(List<QueixaPrincipal> queixasPrincipaisMaisUsadas) {
		this.queixasPrincipaisMaisUsadas = queixasPrincipaisMaisUsadas;
	}

	public List<QueixaPrincipalAtendimento> getQueixasPrincipaisAtendimento() {
		return queixasPrincipaisAtendimento;
	}

	public void setQueixasPrincipaisAtendimento(List<QueixaPrincipalAtendimento> queixasPrincipaisAtendimento) {
		this.queixasPrincipaisAtendimento = queixasPrincipaisAtendimento;
	}
}
