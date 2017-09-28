package br.edu.utfpr.constants;

import java.util.Locale;

public interface Constantes {

	final String PEP_OWNER = "pep_owner.";
	
	final String PASSWORD_ENCODER = "MD5";
	
	final Short QTDE_MAXIMA_ERROS_ACESSO = 5;
	
	final Locale LOCALE_PT_BR = new Locale("pt_BR");
	
	final String FORMATO_DATA_HORA_DIA_SEMANA = "dd/MM/yyyy HH:mm - EEEE";
	
	final String FORMATO_DATA_HORA = "dd/MM/yyyy HH:mm";
	
	final String FORMATO_DATA = "dd/MM/yyyy";
	
	final Integer PASSWD_MIN_LENGHT = 6;

	final Integer PASSWD_MAX_LENGHT = 8;
	
	final String EDITAR_INFO_PESSOAL_URL = "editarInformacoesPessoais";
	
	final String EDITAR_PACIENTE_URL = "editarPaciente";
	
	final String NOVO_PACIENTE_URL = "novoPaciente";
	
	final Integer QTDE_SUGESTOES_QUEIXAS = 10;
	
	final Integer QTDE_SUGESTOES_ANTECEDENTES_CLINICOS = 10;
	
	final Integer QTDE_SUGESTOES_ANTECEDENTES_CIRURGICOS = 10;
	
	final Integer QTDE_SUGESTOES_HABITOS = 10;
	
	final Integer QTDE_SUGESTOES_ALERGIAS = 10;
	
	final Integer QTDE_SUGESTOES_VACINAS = 10;
	
	final Integer QTDE_SUGESTOES_ANTECEDENTES_FAMILIARES = 10;
	
	final Integer QTDE_SUGESTOES_DOENCAS_DIAGNOSTICADAS = 10;
}