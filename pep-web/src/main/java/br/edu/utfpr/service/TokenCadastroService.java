package br.edu.utfpr.service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import br.edu.utfpr.dao.TokenCadastroDao;
import br.edu.utfpr.model.TokenCadastro;

@Named
@Stateless
public class TokenCadastroService {

	@Inject
	private TokenCadastroDao tokenCadastroDao;
	
	public TokenCadastro criarTokenUsuario(String username) {
		//validade do token eh de 01 dia
		Calendar dataValidade = Calendar.getInstance();
		dataValidade.add(Calendar.DAY_OF_MONTH, -1);
		
		//inativa token anterior se existe
		try {
			TokenCadastro tokenCadastro = tokenCadastroDao.retornarTokenAtivoPorUsuario(username, dataValidade.getTime());
			tokenCadastro.setChkAtivo(Boolean.FALSE);
			tokenCadastroDao.save(tokenCadastro);
		} catch (NoResultException e) {}
		
		/* Criacao do Hash - Unique Identifier */
		UUID hashUUID = UUID.randomUUID();
		String token = hashUUID.toString().replaceAll("-", "").trim();
		
		TokenCadastro tokenCadastro = new TokenCadastro();
		tokenCadastro.setTxEmailUsuario(username);
		tokenCadastro.setChkAtivo(Boolean.TRUE);
		tokenCadastro.setTxTokenValue(token);
		tokenCadastro.setDtGeracao(new Date());
		tokenCadastroDao.save(tokenCadastro);
		
		return tokenCadastro;
	}
	
	public TokenCadastro retornarTokenAtivo(String tokenValue) {
		//validade do token eh de 01 dia
		Calendar dataValidade = Calendar.getInstance();
		dataValidade.add(Calendar.DAY_OF_MONTH, -1);
		
		try {
			return tokenCadastroDao.retornarTokenAtivoPorValor(tokenValue, dataValidade.getTime());
		} catch (NoResultException e) {
			return null;
		}
	}
}
