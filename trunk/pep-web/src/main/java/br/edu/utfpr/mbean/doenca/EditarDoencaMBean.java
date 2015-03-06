package br.edu.utfpr.mbean.doenca;

import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.service.DoencaService;

@ManagedBean
@ViewScoped
public class EditarDoencaMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private DoencaService doencaService;
	
	private Doenca doencaSelecionada;
	
	@PostConstruct
	public void init() {
		String idDoenca = getRequest().getParameter("idDoenca");
		
		if (StringUtils.isNotEmpty(idDoenca)) {
			if (new Scanner(idDoenca).hasNextLong()) {
				this.doencaSelecionada = doencaService.retornarDoenca(Long.parseLong(idDoenca));
			}
			
			if (this.doencaSelecionada == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("doenca.erro.naoencontrada"), idDoenca));
				return;
			}
		} else {
			this.doencaSelecionada = new Doenca();
			this.doencaSelecionada.setChkAtivo(Boolean.TRUE);
		}
	}
	
	public String salvarDoenca() {
		boolean isNew = doencaSelecionada.isNew();
		try {
			doencaService.salvarDoenca(doencaSelecionada);
			doencaSelecionada = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("doenca.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("doenca.editar.sucesso"), true);
			}
			
			return "/secure/doenca/doencas.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}

	public Doenca getDoencaSelecionada() {
		return doencaSelecionada;
	}

	public void setDoencaSelecionada(Doenca doencaSelecionada) {
		this.doencaSelecionada = doencaSelecionada;
	}
}
