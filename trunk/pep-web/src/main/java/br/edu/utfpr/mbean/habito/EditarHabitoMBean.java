package br.edu.utfpr.mbean.habito;

import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Habito;
import br.edu.utfpr.service.HabitoService;

@ManagedBean
@ViewScoped
public class EditarHabitoMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private HabitoService habitoService;
	
	private Habito habitoSelecionado;
	
	@PostConstruct
	public void init() {
		String idHabito = getRequest().getParameter("idHabito");
		
		if (StringUtils.isNotEmpty(idHabito)) {
			if (new Scanner(idHabito).hasNextLong()) {
				this.habitoSelecionado = habitoService.retornarHabito(Long.parseLong(idHabito));
			}
			
			if (this.habitoSelecionado == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("habito.erro.naoencontrado"), idHabito));
				return;
			}
		} else {
			this.habitoSelecionado = new Habito();
			this.habitoSelecionado.setChkAtivo(Boolean.TRUE);
		}
	}
	
	public String salvarHabito() {
		boolean isNew = habitoSelecionado.isNew();
		try {
			habitoService.salvarHabito(habitoSelecionado);
			habitoSelecionado = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("habito.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("habito.editar.sucesso"), true);
			}
			
			return "/secure/habito/habitos.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}

	public Habito getHabitoSelecionado() {
		return habitoSelecionado;
	}

	public void setHabitoSelecionado(Habito habitoSelecionado) {
		this.habitoSelecionado = habitoSelecionado;
	}

}
