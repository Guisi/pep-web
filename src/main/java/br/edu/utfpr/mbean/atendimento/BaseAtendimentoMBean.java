package br.edu.utfpr.mbean.atendimento;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.constants.EstadoCivilEnum;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Usuario;

public abstract class BaseAtendimentoMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	protected Usuario pacienteSelecionado;
	
	public String editarPaciente() {
		return "/secure/paciente/editarPaciente.xhtml?faces-redirect=true&idUsuario=" + this.pacienteSelecionado.getId();
	}
	
	public String getIdadePaciente() {
		int idade = this.pacienteSelecionado.getIdade();
		return MessageFormat.format(getMsgs().getString("atendimento.paciente.idade"), idade);
	}
	
	public String getEstadoCivilPaciente() {
		String estadoCivil = this.pacienteSelecionado.getEstadoCivil();
		if (StringUtils.isNotBlank(estadoCivil) && EstadoCivilEnum.retornarPorDescricao(estadoCivil) != null) {
			EstadoCivilEnum estadoEnum = EstadoCivilEnum.retornarPorDescricao(estadoCivil);
			return estadoEnum.getConjugacao() + " " + estadoEnum.getEstadoCivil().toLowerCase() + ".";
		}
		return "";
	}
	
	public String getCidadePaciente() {
		return MessageFormat.format(getMsgs().getString("atendimento.paciente.cidade"), this.pacienteSelecionado.getCidade(), this.pacienteSelecionado.getUf());
	}

	public Usuario getPacienteSelecionado() {
		return pacienteSelecionado;
	}

	public void setPacienteSelecionado(Usuario pacienteSelecionado) {
		this.pacienteSelecionado = pacienteSelecionado;
	}
}
