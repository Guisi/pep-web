package br.edu.utfpr.utils;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class EnderecoCorreios implements Serializable {
		private static final long serialVersionUID = 1L;

		private String cep;
		private String logradouro;
		private String bairro;
		private String cidade;
		private String uf;
		
		public boolean isRetornoOk() {
			return StringUtils.isNotBlank(logradouro);
		}
		
		public String getCep() {
			return cep;
		}
		public void setCep(String cep) {
			this.cep = cep;
		}
		public String getLogradouro() {
			return logradouro;
		}
		public void setLogradouro(String logradouro) {
			this.logradouro = logradouro;
		}
		public String getBairro() {
			return bairro;
		}
		public void setBairro(String bairro) {
			this.bairro = bairro;
		}
		public String getCidade() {
			return cidade;
		}
		public void setCidade(String cidade) {
			this.cidade = cidade;
		}
		public String getUf() {
			return uf;
		}
		public void setUf(String uf) {
			this.uf = uf;
		}

		@Override
		public String toString() {
			return "EnderecoCorreios [cep=" + cep + ", logradouro=" + logradouro + ", bairro=" + bairro + ", cidade=" + cidade + ", uf=" + uf + "]";
		}
	}