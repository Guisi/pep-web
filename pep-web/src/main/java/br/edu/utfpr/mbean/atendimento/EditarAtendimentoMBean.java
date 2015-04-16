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

import br.edu.utfpr.mbean.atendimento.viewbean.AntecedentesPessoaisViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.HistoriaAtendimentoViewBean;
import br.edu.utfpr.mbean.atendimento.viewbean.TratamentosAndamentoViewBean;
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
	private AntecedentesPessoaisViewBean antecedentesPessoaisViewBean;
	
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
				this.historiaViewBean.getQueixasPrincipaisAtendimento());

		this.tratamentoViewBean.setMedicamentosAtendimento(new ArrayList<>(this.atendimentoSelecionado.getMedicamentos()));
		this.historiaViewBean.setQueixasPrincipaisAtendimento(new ArrayList<>(this.atendimentoSelecionado.getQueixasPrincipais()));
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

	public AntecedentesPessoaisViewBean getAntecedentesPessoaisViewBean() {
		return antecedentesPessoaisViewBean;
	}

	public void setAntecedentesPessoaisViewBean(AntecedentesPessoaisViewBean antecedentesPessoaisViewBean) {
		this.antecedentesPessoaisViewBean = antecedentesPessoaisViewBean;
	}
}
