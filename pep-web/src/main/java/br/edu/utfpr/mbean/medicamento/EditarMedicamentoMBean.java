package br.edu.utfpr.mbean.medicamento;

import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Medicamento;
import br.edu.utfpr.service.MedicamentoService;

@ManagedBean
@ViewScoped
public class EditarMedicamentoMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private MedicamentoService medicamentoService;
	
	private Medicamento medicamentoSelecionado;
	
	@PostConstruct
	public void init() {
		String idMedicamento = getRequest().getParameter("idMedicamento");
		
		if (StringUtils.isNotEmpty(idMedicamento)) {
			if (new Scanner(idMedicamento).hasNextLong()) {
				this.medicamentoSelecionado = medicamentoService.retornarMedicamento(Long.parseLong(idMedicamento));
			}
			
			if (this.medicamentoSelecionado == null) {
				addErrorMessage(MessageFormat.format(getMsgs().getString("medicamento.erro.naoencontrado"), idMedicamento));
				return;
			}
		} else {
			this.medicamentoSelecionado = new Medicamento();
			this.medicamentoSelecionado.setChkAtivo(Boolean.TRUE);
		}
	}
	
	public String salvarMedicamento() {
		boolean isNew = medicamentoSelecionado.isNew();
		try {
			medicamentoService.salvarMedicamento(medicamentoSelecionado);
			medicamentoSelecionado = null;
			
			if (isNew) {
				addInfoMessage(getMsgs().getString("medicamento.criar.sucesso"), true);
			} else {
				addInfoMessage(getMsgs().getString("medicamento.editar.sucesso"), true);
			}
			
			return "/secure/medicamento/medicamentos.xhtml?faces-redirect=true";
		} catch (AppException e) {
			addressException(e);
		}
		return null;
	}

	public Medicamento getMedicamentoSelecionado() {
		return medicamentoSelecionado;
	}

	public void setMedicamentoSelecionado(Medicamento medicamentoSelecionado) {
		this.medicamentoSelecionado = medicamentoSelecionado;
	}

}
