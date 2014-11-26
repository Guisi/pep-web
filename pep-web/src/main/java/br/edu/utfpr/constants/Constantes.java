package br.edu.utfpr.constants;

import java.util.Locale;

public interface Constantes {

	final String PEP_OWNER = "pep_owner.";
	
	final String PASSWORD_ENCODER = "MD5";
	
	final Short QTDE_MAXIMA_ERROS_ACESSO = 5;
	
	final Locale LOCALE_PT_BR = new Locale("pt_BR");
	
	final Integer PASSWD_MIN_LENGHT = 6;

	final Integer PASSWD_MAX_LENGHT = 8;
	
	final String EDITAR_INFO_PESSOAL_URL = "editarInformacoesPessoais";
	
	final String EDITAR_PACIENTE_URL = "editarPaciente";
	
	final String NOVO_PACIENTE_URL = "novoPaciente";
}