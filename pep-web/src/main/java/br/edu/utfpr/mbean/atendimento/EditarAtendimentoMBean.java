package br.edu.utfpr.mbean.atendimento;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.mbean.atendimento.viewbean.HistoriaAtendimentoViewBean;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.model.Medicamento;
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.model.QueixaPrincipalAtendimento;
import br.edu.utfpr.service.AtendimentoService;
import br.edu.utfpr.service.MedicamentoAtendimentoService;
import br.edu.utfpr.service.MedicamentoService;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class EditarAtendimentoMBean extends BaseAtendimentoMBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private MedicamentoService medicamentoService;
	@Inject
	private AtendimentoService atendimentoService;
	@Inject
	private MedicamentoAtendimentoService medicamentoAtendimentoService;
	
	@Inject
	private HistoriaAtendimentoViewBean historiaViewBean;
	
	private String menuInclude;
	private String menuHeader;
	
	private Atendimento atendimentoSelecionado;
	private List<Atendimento> atendimentosAnteriores;

	private Medicamento medicamentoSelecionado;
	private List<Medicamento> medicamentosDisponiveis;
	private List<MedicamentoAtendimento> medicamentosAtendimento;
	private List<MedicamentoAtendimento> medicamentosAtendimentosAnteriores;
	
	private Doenca antecedenteClinicoSelecionado;
	private List<Doenca> antecedentesClinicosDisponiveis;

	@PostConstruct
	public void init() {
		String idAtendimento = getRequest().getParameter("idAtendimento");
		
		List<QueixaPrincipalAtendimento> queixasPrincipaisAtendimento;
		
		if (StringUtils.isNotEmpty(idAtendimento)) {
			if (new Scanner(idAtendimento).hasNextLong()) {
				this.atendimentoSelecionado = atendimentoService.retornarAtendimento(Long.parseLong(idAtendimento));
			}
			
			if (this.atendimentoSelecionado == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("atendimento.erro.naoencontrado"), idAtendimento));
				return;
			}
			
			this.pacienteSelecionado = this.atendimentoSelecionado.getPaciente();
			this.medicamentosAtendimento = new ArrayList<>(this.atendimentoSelecionado.getMedicamentos());
			queixasPrincipaisAtendimento = new ArrayList<>(this.atendimentoSelecionado.getQueixasPrincipais());
		} else {
			String idPaciente = getRequest().getParameter("idPaciente");
			
			if (StringUtils.isNotEmpty(idPaciente) && new Scanner(idPaciente).hasNextLong()) {
				this.pacienteSelecionado = usuarioService.retornarUsuario(Long.parseLong(idPaciente));
			}
			
			//se nao achou usuario ou usuario nao eh paciente, retorna msg de erro
			if (this.pacienteSelecionado == null || !this.pacienteSelecionado.isPaciente()) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("paciente.erro.naoencontrado"), idPaciente));
				this.pacienteSelecionado = null;
				return;
			}
			
			this.atendimentoSelecionado = new Atendimento();
			this.atendimentoSelecionado.setPaciente(this.pacienteSelecionado);
			
			this.medicamentosAtendimento = new ArrayList<MedicamentoAtendimento>();
			queixasPrincipaisAtendimento = new ArrayList<QueixaPrincipalAtendimento>();
		}
		
		//atendimentos anteriores
		this.atendimentosAnteriores = atendimentoService.retornarAtendimentosAnterioresPaciente(this.pacienteSelecionado.getId(), this.atendimentoSelecionado.getId());
		
		this.historiaViewBean.init(queixasPrincipaisAtendimento);
		
		//tratamentos em andamento
		this.listarMedicamentosAtendimentosAnteriores();
		this.listarMedicamentosDisponiveis();
	}
	
	public String cancelar() {
		return "/secure/atendimento/atendimentos.xhtml?faces-redirect=true&idPaciente=" + this.pacienteSelecionado.getId();
	}
	
	public void salvar() {
		this.salvarAtendimento();
		addInfoMessage(getMsgs().getString("atendimento.salvar.sucesso"));
	}
	
	public String finalizar() {
		this.salvarAtendimento();
		addInfoMessage(getMsgs().getString("atendimento.finalizar.sucesso"), true);
		
		return "/secure/atendimento/atendimentos.xhtml?faces-redirect=true&idPaciente=" + this.pacienteSelecionado.getId();
	}
	
	private void salvarAtendimento() {
		this.atendimentoSelecionado = atendimentoService.salvarAtendimento(this.atendimentoSelecionado, this.medicamentosAtendimento, 
				this.medicamentosAtendimentosAnteriores, this.historiaViewBean.getQueixasPrincipaisAtendimento());
		this.medicamentosAtendimento = new ArrayList<>(this.atendimentoSelecionado.getMedicamentos());
		this.historiaViewBean.setQueixasPrincipaisAtendimento(new ArrayList<>(this.atendimentoSelecionado.getQueixasPrincipais()));
	}
	
	/** Tratamentos em andamento */
	private void listarMedicamentosDisponiveis() {
		this.medicamentosDisponiveis = medicamentoService.retornarMedicamentos(Boolean.TRUE);
		
		List<MedicamentoAtendimento> medicamentosAgrupado = this.getMedicamentosAtendimentoAgrupado();
		for (MedicamentoAtendimento medicamentoAtendimento : medicamentosAgrupado) {
			if (medicamentoAtendimento.getMedicamento() != null) {
				this.medicamentosDisponiveis.remove(medicamentoAtendimento.getMedicamento());
			}
		}
	}
	
	private void listarMedicamentosAtendimentosAnteriores() {
		this.medicamentosAtendimentosAnteriores = medicamentoAtendimentoService
				.retornarMedicamentosEmUsoPaciente(this.pacienteSelecionado.getId(), this.atendimentoSelecionado.getId());
		for (MedicamentoAtendimento medicamentoAtendimento : this.medicamentosAtendimentosAnteriores) {
			medicamentoAtendimento.setAtendimentoAnterior(true);
		}
	}
	
	public void adicionarMedicamento() {
		if (this.medicamentoSelecionado != null) {
			this.medicamentosDisponiveis.remove(this.medicamentoSelecionado);
			
			MedicamentoAtendimento medicamentoAtendimento = new MedicamentoAtendimento();
			medicamentoAtendimento.setMedicamento(this.medicamentoSelecionado);
			medicamentoAtendimento.setEmUso(Boolean.TRUE);
			this.medicamentosAtendimento.add(medicamentoAtendimento);

			this.medicamentoSelecionado = null;
		}
	}
	
	public void adicionarOutroMedicamento() {
		MedicamentoAtendimento medicamentoAtendimento = new MedicamentoAtendimento();
		medicamentoAtendimento.setEmUso(Boolean.TRUE);
		this.medicamentosAtendimento.add(medicamentoAtendimento);
	}
	
	public void removerMedicamento(int indice) {
		MedicamentoAtendimento medicamento = this.medicamentosAtendimento.get(indice);
		this.medicamentosAtendimento.remove(indice);
		if (medicamento.getMedicamento() != null) {
			this.medicamentosDisponiveis.add(medicamento.getMedicamento());
			this.ordenaListasMedicamentos();
		}
	}
	
	private void ordenaListasMedicamentos() {
		Comparator<Medicamento> comparator = new Comparator<Medicamento>() {
			@Override
			public int compare(Medicamento o1, Medicamento o2) {
				int c = o1.getPrincipioAtivo().compareTo(o2.getPrincipioAtivo());
				if (c == 0) {
					c = o1.getApresentacao().compareTo(o2.getApresentacao());
				}
				return c;
			}
		};
		
		Collections.sort(this.medicamentosDisponiveis, comparator);
	}
	
	public List<MedicamentoAtendimento> getMedicamentosAtendimentoAgrupado() {
		List<MedicamentoAtendimento> l = new ArrayList<>(this.medicamentosAtendimento);
		l.addAll(this.medicamentosAtendimentosAnteriores);
		return l;
	}
	
	public List<Atendimento> getAtendimentosAnterioresTratamentos() {
		List<Atendimento> atendimentos = new ArrayList<>();
		for (Atendimento atendimento : this.atendimentosAnteriores) {
			if (!atendimento.getMedicamentos().isEmpty()) {
				atendimentos.add(atendimento);
			}
		}
		return atendimentos;
	}
	
	public Atendimento getAtendimentoSelecionado() {
		return atendimentoSelecionado;
	}

	public void setAtendimentoSelecionado(Atendimento atendimentoSelecionado) {
		this.atendimentoSelecionado = atendimentoSelecionado;
	}

	public List<Medicamento> getMedicamentosDisponiveis() {
		return medicamentosDisponiveis;
	}

	public void setMedicamentosDisponiveis(List<Medicamento> medicamentosDisponiveis) {
		this.medicamentosDisponiveis = medicamentosDisponiveis;
	}

	public Medicamento getMedicamentoSelecionado() {
		return medicamentoSelecionado;
	}

	public void setMedicamentoSelecionado(Medicamento medicamentoSelecionado) {
		this.medicamentoSelecionado = medicamentoSelecionado;
	}

	public List<MedicamentoAtendimento> getMedicamentosAtendimento() {
		return medicamentosAtendimento;
	}

	public void setMedicamentosAtendimento(List<MedicamentoAtendimento> medicamentosAtendimento) {
		this.medicamentosAtendimento = medicamentosAtendimento;
	}

	public List<MedicamentoAtendimento> getMedicamentosAtendimentosAnteriores() {
		return medicamentosAtendimentosAnteriores;
	}

	public void setMedicamentosAtendimentosAnteriores(List<MedicamentoAtendimento> medicamentosAtendimentosAnteriores) {
		this.medicamentosAtendimentosAnteriores = medicamentosAtendimentosAnteriores;
	}

	public List<Atendimento> getAtendimentosAnteriores() {
		return atendimentosAnteriores;
	}

	public void setAtendimentosAnteriores(List<Atendimento> atendimentosAnteriores) {
		this.atendimentosAnteriores = atendimentosAnteriores;
	}

	public String getMenuInclude() {
		return menuInclude;
	}

	public void setMenuInclude(String menuInclude) {
		this.menuInclude = menuInclude;
	}

	public String getMenuHeader() {
		return menuHeader;
	}

	public void setMenuHeader(String menuHeader) {
		this.menuHeader = menuHeader;
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

	public HistoriaAtendimentoViewBean getHistoriaViewBean() {
		return historiaViewBean;
	}

	public void setHistoriaViewBean(HistoriaAtendimentoViewBean historiaViewBean) {
		this.historiaViewBean = historiaViewBean;
	}
}
