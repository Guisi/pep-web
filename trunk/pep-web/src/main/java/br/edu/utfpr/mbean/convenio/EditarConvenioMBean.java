package br.edu.utfpr.mbean.convenio;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Convenio;
import br.edu.utfpr.service.ConvenioService;

@ManagedBean
@ViewScoped
public class EditarConvenioMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private ConvenioService convenioService;
	
	private Convenio convenioSelecionado;
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		String idConvenio = request.getParameter("idConvenio");
		
		if (StringUtils.isNotEmpty(idConvenio)) {
			if (new Scanner(idConvenio).hasNextLong()) {
				this.convenioSelecionado = convenioService.retornarConvenio(Long.parseLong(idConvenio));
			}
			
			if (this.convenioSelecionado == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("convenio.erro.naoencontrado"), idConvenio));
				return;
			}
		} else {
			this.convenioSelecionado = new Convenio();
		}
	}
	
	public String salvarConvenio() {
		boolean isNew = convenioSelecionado.isNew();
		try {
			convenioService.salvarConvenio(convenioSelecionado);
			convenioSelecionado = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("convenio.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("convenio.editar.sucesso"), true);
			}
			
			return "/secure/convenio/convenios.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}

	public Convenio getConvenioSelecionado() {
		return convenioSelecionado;
	}

	public void setConvenioSelecionado(Convenio convenioSelecionado) {
		this.convenioSelecionado = convenioSelecionado;
	}

}
