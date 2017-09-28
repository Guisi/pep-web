package br.edu.utfpr.mbean.atendimento.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Exame;
import br.edu.utfpr.model.ExameAtendimento;
import br.edu.utfpr.model.GrupoExame;
import br.edu.utfpr.service.ExameService;
import br.edu.utfpr.service.GrupoExameService;

public class SolicitacaoExamesViewBean extends BaseAtendimentoViewBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ExameService exameService;
	@Inject
	private GrupoExameService grupoExameService;
	
	private Exame exameSelecionado;
	private List<Exame> examesDisponiveis;
	private List<ExameAtendimento> examesAtendimento;
	private List<ExameAtendimento> examesAtendimentosAnteriores;
	private List<GrupoExame> gruposExame;
	private List<GrupoExame> gruposExameDisponiveis;
	
	@Override
	public void init(EditarAtendimentoMBean mbean) {
		super.init(mbean);
		
		if (getAtendimentoSelecionado().isNew()) {
			this.examesAtendimento = new ArrayList<ExameAtendimento>();
		} else {
			this.examesAtendimento = new ArrayList<ExameAtendimento>(getAtendimentoSelecionado().getExamesSolicitados());
		}
		
		this.listarExamesDisponiveis();
		this.listarGruposExame();
	}
	
	private void listarExamesDisponiveis() {
		this.examesDisponiveis = exameService.retornarExames(Boolean.TRUE);
		
		for (ExameAtendimento exameAtendimento : this.examesAtendimento) {
			if (exameAtendimento.getExame() != null) {
				this.examesDisponiveis.remove(exameAtendimento.getExame());
			}
		}
	}
	
	private void listarGruposExame() {
		this.gruposExame = grupoExameService.retornarGruposExames(Boolean.TRUE, Boolean.TRUE);
		this.atualizarGruposExameDisponiveis();
	}
	
	private void atualizarGruposExameDisponiveis() {
		this.gruposExameDisponiveis = new ArrayList<GrupoExame>();
		
		List<Exame> examesAtendimento = new ArrayList<>();
		for (ExameAtendimento exameAtendimento : this.examesAtendimento) {
			examesAtendimento.add(exameAtendimento.getExame());
		}
		// só mostra grupos que tenham algum exame ainda não existente no atendimento
		for (GrupoExame grupoExame : this.gruposExame) {
			if (!examesAtendimento.containsAll(grupoExame.getExames())) {
				this.gruposExameDisponiveis.add(grupoExame);
			}
		}
	}
	
	public void adicionarExame() {
		if (this.exameSelecionado != null) {
			this.examesDisponiveis.remove(this.exameSelecionado);
			
			ExameAtendimento exameAtendimento = new ExameAtendimento();
			exameAtendimento.setExame(exameSelecionado);
			this.examesAtendimento.add(exameAtendimento);

			this.exameSelecionado = null;
			
			this.atualizarGruposExameDisponiveis();
		}
	}
	
	public void removerExame(int indice) {
		ExameAtendimento exameAtendimento = this.examesAtendimento.get(indice);
		this.examesAtendimento.remove(indice);
		this.examesDisponiveis.add(exameAtendimento.getExame());
		this.ordenaListasExames();
		this.atualizarGruposExameDisponiveis();
	}
	
	private void ordenaListasExames() {
		Comparator<Exame> comparator = new Comparator<Exame>() {
			@Override
			public int compare(Exame o1, Exame o2) {
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		};
		
		Collections.sort(this.examesDisponiveis, comparator);
	}
	
	public void adicionarExamesGrupo(GrupoExame grupoExame) {
		for (Exame exame : grupoExame.getExames()) {
			boolean temExame = false;
			for (ExameAtendimento exameAtendimento : this.examesAtendimento) {
				if (exame.equals(exameAtendimento.getExame())) {
					temExame = true;
					break;
				}
			}
			
			if (!temExame) {
				ExameAtendimento exameAtendimento = new ExameAtendimento();
				exameAtendimento.setExame(exame);
				this.examesAtendimento.add(exameAtendimento);
			}
		}

		this.atualizarGruposExameDisponiveis();
	}
	
	public List<Atendimento> getAtendimentosAnterioresExamesSolicitados() {
		List<Atendimento> atendimentos = new ArrayList<>();
		for (Atendimento atendimento : getAtendimentosAnteriores()) {
			if (!atendimento.getExamesSolicitados().isEmpty()) {
				atendimentos.add(atendimento);
			}
		}
		return atendimentos;
	}

	public Exame getExameSelecionado() {
		return exameSelecionado;
	}

	public void setExameSelecionado(Exame exameSelecionado) {
		this.exameSelecionado = exameSelecionado;
	}

	public List<Exame> getExamesDisponiveis() {
		return examesDisponiveis;
	}

	public void setExamesDisponiveis(List<Exame> examesDisponiveis) {
		this.examesDisponiveis = examesDisponiveis;
	}

	public List<ExameAtendimento> getExamesAtendimento() {
		return examesAtendimento;
	}

	public void setExamesAtendimento(List<ExameAtendimento> examesAtendimento) {
		this.examesAtendimento = examesAtendimento;
	}

	public List<ExameAtendimento> getExamesAtendimentosAnteriores() {
		return examesAtendimentosAnteriores;
	}

	public void setExamesAtendimentosAnteriores(List<ExameAtendimento> examesAtendimentosAnteriores) {
		this.examesAtendimentosAnteriores = examesAtendimentosAnteriores;
	}

	public List<GrupoExame> getGruposExameDisponiveis() {
		return gruposExameDisponiveis;
	}

	public void setGruposExameDisponiveis(List<GrupoExame> gruposExameDisponiveis) {
		this.gruposExameDisponiveis = gruposExameDisponiveis;
	}
	
}
