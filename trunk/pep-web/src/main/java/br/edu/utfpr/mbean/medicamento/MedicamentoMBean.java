package br.edu.utfpr.mbean.medicamento;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Medicamento;
import br.edu.utfpr.service.MedicamentoService;

@ManagedBean
@ViewScoped
public class MedicamentoMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private MedicamentoService medicamentoService;
	
	private List<Medicamento> medicamentoList;
	private Medicamento medicamentoSelecionado;
	
	@PostConstruct
	public void init() {
		this.listarMedicamentos();
	}
	
	private void listarMedicamentos() {
		this.medicamentoList = medicamentoService.retornarMedicamentos();
	}
	
	public String novoMedicamento() {
		return "/secure/medicamento/editarMedicamento.xhtml?faces-redirect=true";
	}
	
	public String editarMedicamento(Long idMedicamento) {
		return "/secure/medicamento/editarMedicamento.xhtml?faces-redirect=true&idMedicamento=" + idMedicamento;
	}
	
	public void removerMedicamento() {
		try {
			medicamentoService.removerMedicamento(medicamentoSelecionado);
			medicamentoSelecionado = null;
			
			this.listarMedicamentos();
			addInfoMessage(getMsgs().getString("medicamento.remover.sucesso"));
		} catch (AppException e) {
			addressException(e);
		}
	}

	public List<Medicamento> getMedicamentoList() {
		return medicamentoList;
	}

	public void setMedicamentoList(List<Medicamento> medicamentoList) {
		this.medicamentoList = medicamentoList;
	}

	public Medicamento getMedicamentoSelecionado() {
		return medicamentoSelecionado;
	}

	public void setMedicamentoSelecionado(Medicamento medicamentoSelecionado) {
		this.medicamentoSelecionado = medicamentoSelecionado;
	}

}
