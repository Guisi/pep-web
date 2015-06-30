package br.edu.utfpr.mbean.atendimento;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.mbean.atendimento.viewbean.AlergiasViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.AntecedentesCirurgicosViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.AntecedentesClinicosViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.AntecedentesFamiliaresViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.DoencasDiagnosticadasViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.ExameFisicoViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.HabitosViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.HistoriaAtendimentoViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.TratamentosAndamentoViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.VacinasViewBean;
import br.edu.utfpr.model.AntecedenteCirurgicoAtendimento;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.service.AtendimentoService;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class EditarAtendimentoMBean extends BaseAtendimentoMBean {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private AtendimentoService atendimentoService;
	
	@Inject
	private HistoriaAtendimentoViewBean historiaViewBean;
	@Inject
	private TratamentosAndamentoViewBean tratamentoViewBean;
	@Inject
	private AntecedentesClinicosViewBean antecedentesClinicosViewBean;
	@Inject
	private AntecedentesCirurgicosViewBean antecedentesCirurgicosViewBean;
	@Inject
	private HabitosViewBean habitosViewBean;
	@Inject
	private AlergiasViewBean alergiasViewBean;
	@Inject
	private VacinasViewBean vacinasViewBean;
	@Inject
	private AntecedentesFamiliaresViewBean antecedentesFamiliaresViewBean;
	@Inject
	private ExameFisicoViewBean exameFisicoViewBean;
	@Inject
	private DoencasDiagnosticadasViewBean doencasDiagnosticadasViewBean;
	
	private String menuInclude;
	private String menuHeader;
	
	private Atendimento atendimentoSelecionado;
	private List<Atendimento> atendimentosAnteriores;

	@PostConstruct
	public void init() {
		String idAtendimento = getRequest().getParameter("idAtendimento");
		
		if (StringUtils.isNotEmpty(idAtendimento)) {
			if (new Scanner(idAtendimento).hasNextLong()) {
				this.atendimentoSelecionado = atendimentoService.retornarAtendimento(Long.parseLong(idAtendimento));
			}
			
			if (this.atendimentoSelecionado == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("atendimento.erro.naoencontrado"), idAtendimento));
				return;
			}
			
			this.pacienteSelecionado = this.atendimentoSelecionado.getPaciente();
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
		}
		
		//atendimentos anteriores
		this.atendimentosAnteriores = atendimentoService.retornarAtendimentosAnterioresPaciente(this.pacienteSelecionado.getId(), this.atendimentoSelecionado.getId());
		
		//inicializa viewbeans
		this.historiaViewBean.init(this);
		this.tratamentoViewBean.init(this);
		this.antecedentesClinicosViewBean.init(this);
		this.antecedentesCirurgicosViewBean.init(this);
		this.habitosViewBean.init(this);
		this.alergiasViewBean.init(this);
		this.vacinasViewBean.init(this);
		this.antecedentesFamiliaresViewBean.init(this);
		this.exameFisicoViewBean.init(this);
		this.doencasDiagnosticadasViewBean.init(this);
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
		this.atendimentoSelecionado = atendimentoService.salvarAtendimento(this.atendimentoSelecionado, 
				this.tratamentoViewBean.getMedicamentosAtendimento(), 
				this.tratamentoViewBean.getMedicamentosAtendimentosAnteriores(),
				this.historiaViewBean.getQueixasPrincipaisAtendimento(),
				this.antecedentesClinicosViewBean.getAntecedentesClinicosAtendimento(),
				this.antecedentesCirurgicosViewBean.getAntecedentesCirurgicosAtendimento(),
				this.habitosViewBean.getHabitosAtendimento(),
				this.alergiasViewBean.getAlergiasAtendimento(),
				this.vacinasViewBean.getVacinasAtendimento(),
				this.antecedentesFamiliaresViewBean.getAntecedentesFamiliaresAtendimento());
		
		this.tratamentoViewBean.setMedicamentosAtendimento(new ArrayList<>(this.atendimentoSelecionado.getMedicamentos()));
		this.historiaViewBean.setQueixasPrincipaisAtendimento(new ArrayList<>(this.atendimentoSelecionado.getQueixasPrincipais()));
		this.antecedentesClinicosViewBean.setAntecedentesClinicosAtendimento(new ArrayList<AntecedenteClinicoAtendimento>(this.atendimentoSelecionado.getAntecedentesClinicos()));
		this.antecedentesCirurgicosViewBean.setAntecedentesCirurgicosAtendimento(new ArrayList<AntecedenteCirurgicoAtendimento>(this.atendimentoSelecionado.getAntecedentesCirurgicos()));
		this.habitosViewBean.setHabitosAtendimento(new ArrayList<>(this.atendimentoSelecionado.getHabitos()));
		this.alergiasViewBean.setAlergiasAtendimento(new ArrayList<>(this.atendimentoSelecionado.getAlergias()));
		this.vacinasViewBean.setVacinasAtendimento(new ArrayList<>(this.atendimentoSelecionado.getVacinas()));
		this.antecedentesFamiliaresViewBean.setAntecedentesFamiliaresAtendimento(new ArrayList<>(this.atendimentoSelecionado.getAntecedentesFamiliares()));
	}
	
	public List<Atendimento> getAtendimentosAnterioresAntecedentesPessoais() {
		List<Atendimento> atendimentos = new ArrayList<>();
		for (Atendimento atendimento : getAtendimentosAnteriores()) {
			if (!atendimento.getAntecedentesClinicos().isEmpty()
					|| !atendimento.getAntecedentesCirurgicos().isEmpty()
					|| !atendimento.getHabitos().isEmpty()
					|| !atendimento.getAlergias().isEmpty()
					|| !atendimento.getVacinas().isEmpty()
					|| !atendimento.getAntecedentesFamiliares().isEmpty()) {
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

	public HistoriaAtendimentoViewBean getHistoriaViewBean() {
		return historiaViewBean;
	}

	public void setHistoriaViewBean(HistoriaAtendimentoViewBean historiaViewBean) {
		this.historiaViewBean = historiaViewBean;
	}

	public TratamentosAndamentoViewBean getTratamentoViewBean() {
		return tratamentoViewBean;
	}

	public void setTratamentoViewBean(TratamentosAndamentoViewBean tratamentoViewBean) {
		this.tratamentoViewBean = tratamentoViewBean;
	}

	public AntecedentesClinicosViewBean getAntecedentesClinicosViewBean() {
		return antecedentesClinicosViewBean;
	}

	public void setAntecedentesClinicosViewBean(AntecedentesClinicosViewBean antecedentesClinicosViewBean) {
		this.antecedentesClinicosViewBean = antecedentesClinicosViewBean;
	}

	public AntecedentesCirurgicosViewBean getAntecedentesCirurgicosViewBean() {
		return antecedentesCirurgicosViewBean;
	}

	public void setAntecedentesCirurgicosViewBean(AntecedentesCirurgicosViewBean antecedentesCirurgicosViewBean) {
		this.antecedentesCirurgicosViewBean = antecedentesCirurgicosViewBean;
	}

	public HabitosViewBean getHabitosViewBean() {
		return habitosViewBean;
	}

	public void setHabitosViewBean(HabitosViewBean habitosViewBean) {
		this.habitosViewBean = habitosViewBean;
	}

	public AlergiasViewBean getAlergiasViewBean() {
		return alergiasViewBean;
	}

	public void setAlergiasViewBean(AlergiasViewBean alergiasViewBean) {
		this.alergiasViewBean = alergiasViewBean;
	}

	public VacinasViewBean getVacinasViewBean() {
		return vacinasViewBean;
	}

	public void setVacinasViewBean(VacinasViewBean vacinasViewBean) {
		this.vacinasViewBean = vacinasViewBean;
	}

	public AntecedentesFamiliaresViewBean getAntecedentesFamiliaresViewBean() {
		return antecedentesFamiliaresViewBean;
	}

	public void setAntecedentesFamiliaresViewBean(AntecedentesFamiliaresViewBean antecedentesFamiliaresViewBean) {
		this.antecedentesFamiliaresViewBean = antecedentesFamiliaresViewBean;
	}

	public ExameFisicoViewBean getExameFisicoViewBean() {
		return exameFisicoViewBean;
	}

	public void setExameFisicoViewBean(ExameFisicoViewBean exameFisicoViewBean) {
		this.exameFisicoViewBean = exameFisicoViewBean;
	}
}
