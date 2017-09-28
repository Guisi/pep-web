package br.edu.utfpr.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
		
		//inativa tokens ativos se existirem
		this.inativarTokensUsuario(username);
		
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
	
	public void inativarTokensUsuario(String username) {
		//inativa token anterior se existe
		List<TokenCadastro> tokens = tokenCadastroDao.retornarTokensAtivosPorUsuario(username);
		for (TokenCadastro token : tokens) {
			token.setChkAtivo(Boolean.FALSE);
			tokenCadastroDao.save(token);
		}
	}
}
