package br.edu.utfpr.mbean.procedimento;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.constants.TipoProcedimento;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Procedimento;
import br.edu.utfpr.service.ProcedimentoService;

@ManagedBean
@ViewScoped
public class EditarProcedimentoMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProcedimentoService procedimentoService;
	
	private Procedimento procedimentoSelecionado;
	
	@PostConstruct
	public void init() {
		String idProcedimento = getRequest().getParameter("idProcedimento");
		
		if (StringUtils.isNotEmpty(idProcedimento)) {
			if (new Scanner(idProcedimento).hasNextLong()) {
				this.procedimentoSelecionado = procedimentoService.retornarProcedimento(Long.parseLong(idProcedimento));
			}
			
			if (this.procedimentoSelecionado == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("procedimento.erro.naoencontrado"), idProcedimento));
				return;
			}
		} else {
			this.procedimentoSelecionado = new Procedimento();
			this.procedimentoSelecionado.setChkAtivo(Boolean.TRUE);
		}
	}
	
	public String salvarProcedimento() {
		boolean isNew = procedimentoSelecionado.isNew();
		try {
			procedimentoService.salvarProcedimento(procedimentoSelecionado);
			procedimentoSelecionado = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("procedimento.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("procedimento.editar.sucesso"), true);
			}
			
			return "/secure/procedimento/procedimentos.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}
	
	public List<TipoProcedimento> getTiposProcedimento() {
		return Arrays.asList(TipoProcedimento.values());
	}

	public Procedimento getProcedimentoSelecionado() {
		return procedimentoSelecionado;
	}

	public void setProcedimentoSelecionado(Procedimento procedimentoSelecionado) {
		this.procedimentoSelecionado = procedimentoSelecionado;
	}
}
