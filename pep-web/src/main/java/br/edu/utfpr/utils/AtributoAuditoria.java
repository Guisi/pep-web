package br.edu.utfpr.utils;

import java.io.Serializable;
import java.util.Map;

public class AtributoAuditoria implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public AtributoAuditoria() {}
		
		private String nomeCampo;
		private String tituloCampo;
		private String formatoData;
		private Map<String, String> dominios;
		
		public AtributoAuditoria(String nomeCampo, String tituloCampo) {
			this.nomeCampo = nomeCampo;
			this.tituloCampo = tituloCampo;
		}
		
		public AtributoAuditoria(String nomeCampo, String tituloCampo, String formatoData) {
			this.nomeCampo = nomeCampo;
			this.tituloCampo = tituloCampo;
			this.formatoData = formatoData;
		}
		
		public AtributoAuditoria(String nomeCampo, String tituloCampo, Map<String, String> dominios) {
			this.nomeCampo = nomeCampo;
			this.tituloCampo = tituloCampo;
			this.dominios = dominios;
		}
		
		public String getNomeCampo() {
			return nomeCampo;
		}
		
		public void setNomeCampo(String nomeCampo) {
			this.nomeCampo = nomeCampo;
		}
		
		public String getTituloCampo() {
			return tituloCampo;
		}

		public void setTituloCampo(String tituloCampo) {
			this.tituloCampo = tituloCampo;
		}

		public String getFormatoData() {
			return formatoData;
		}

		public void setFormatoData(String formatoData) {
			this.formatoData = formatoData;
		}

		public Map<String, String> getDominios() {
			return dominios;
		}

		public void setDominios(Map<String, String> dominios) {
			this.dominios = dominios;
		}
	}