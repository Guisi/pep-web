package br.edu.utfpr.mbean.grupoexame;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Exame;
import br.edu.utfpr.model.GrupoExame;
import br.edu.utfpr.service.ExameService;
import br.edu.utfpr.service.GrupoExameService;

@ManagedBean
@ViewScoped
public class EditarGrupoExameMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private GrupoExameService grupoExameService;
	@Inject
	private ExameService exameService;
	
	private GrupoExame grupoExameSelecionado;
	private List<Exame> examesGrupoSelecionado;
	private List<Exame> examesDisponiveis;
	private Exame exameSelecionado;
	
	@PostConstruct
	public void init() {
		String idGrupoExame = getRequest().getParameter("idGrupoExame");
		
		if (StringUtils.isNotEmpty(idGrupoExame)) {
			if (new Scanner(idGrupoExame).hasNextLong()) {
				this.grupoExameSelecionado = grupoExameService.retornarGrupoExame(Long.parseLong(idGrupoExame));
			}
			
			if (this.grupoExameSelecionado == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("grupoexame.erro.naoencontrado"), idGrupoExame));
				return;
			}
			this.examesGrupoSelecionado = new ArrayList<Exame>(this.grupoExameSelecionado.getExames());
		} else {
			this.grupoExameSelecionado = new GrupoExame();
			this.grupoExameSelecionado.setChkAtivo(Boolean.TRUE);
			this.examesGrupoSelecionado = new ArrayList<Exame>();
		}
		
		this.listarExamesDisponiveis();
	}
	
	public String salvarGrupoExame() {
		boolean isNew = grupoExameSelecionado.isNew();
		try {
			grupoExameSelecionado.setExames(new LinkedHashSet<Exame>(this.examesGrupoSelecionado));
			grupoExameService.salvarGrupoExame(grupoExameSelecionado);
			grupoExameSelecionado = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("grupoexame.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("grupoexame.editar.sucesso"), true);
			}
			
			return "/secure/grupoexame/gruposExames.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}
	
	public void listarExamesDisponiveis() {
		this.examesDisponiveis = exameService.retornarExames(Boolean.TRUE);
		for (Exame e : this.examesGrupoSelecionado) {
			this.examesDisponiveis.remove(e);
		}
		this.ordenaListasExames();
	}
	
	public void adicionarExame() {
		if (this.exameSelecionado != null) {
			this.examesDisponiveis.remove(this.exameSelecionado);
			this.examesGrupoSelecionado.add(this.exameSelecionado);
			this.ordenaListasExames();
			this.exameSelecionado = null;
		}
	}
	
	public void removerExame(Exame exame) {
		this.examesGrupoSelecionado.remove(exame);
		this.examesDisponiveis.add(exame);
		this.ordenaListasExames();
	}
	
	private void ordenaListasExames() {
		Comparator<Exame> comparator = new Comparator<Exame>() {
			@Override
			public int compare(Exame o1, Exame o2) {
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		};
		Collections.sort(this.examesDisponiveis, comparator);
		Collections.sort(this.examesGrupoSelecionado, comparator);
	}

	public GrupoExame getGrupoExameSelecionado() {
		return grupoExameSelecionado;
	}

	public void setGrupoExameSelecionado(GrupoExame grupoExameSelecionado) {
		this.grupoExameSelecionado = grupoExameSelecionado;
	}

	public List<Exame> getExamesGrupoSelecionado() {
		return examesGrupoSelecionado;
	}

	public void setExamesGrupoSelecionado(List<Exame> examesGrupoSelecionado) {
		this.examesGrupoSelecionado = examesGrupoSelecionado;
	}

	public List<Exame> getExamesDisponiveis() {
		return examesDisponiveis;
	}

	public void setExamesDisponiveis(List<Exame> examesDisponiveis) {
		this.examesDisponiveis = examesDisponiveis;
	}

	public Exame getExameSelecionado() {
		return exameSelecionado;
	}

	public void setExameSelecionado(Exame exameSelecionado) {
		this.exameSelecionado = exameSelecionado;
	}
}
