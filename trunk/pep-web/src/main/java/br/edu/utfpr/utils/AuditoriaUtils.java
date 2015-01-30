package br.edu.utfpr.utils;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.model.AlteracaoAtributo;
import br.edu.utfpr.model.Auditoria;

public class AuditoriaUtils {

	private AuditoriaUtils() {}
	
	public static List<AlteracaoAtributo> montarListaAlteracoesAtributos(List<AtributoAuditoria> atributos, List<Auditoria> auditoriaList) {
		List<AlteracaoAtributo> alteracoes = new ArrayList<AlteracaoAtributo>();
		
		if (auditoriaList != null && auditoriaList.size() > 1) {
			
			for (int i = 0; i < auditoriaList.size() - 1; i++) {
				Auditoria auditoriaAntigo = auditoriaList.get(i+1);
				Auditoria auditoriaNovo = auditoriaList.get(i);
				
				for (AtributoAuditoria atributoAuditoria : atributos) {
					try {
						Object valorAntigo = PropertyUtils.getProperty(auditoriaAntigo.getEntidade(), atributoAuditoria.getNomeCampo());
						Object valorNovo = PropertyUtils.getProperty(auditoriaNovo.getEntidade(), atributoAuditoria.getNomeCampo());
						
						if (!ObjectUtils.equals(valorAntigo, valorNovo)) {
							AlteracaoAtributo alteracaoAtributo = new AlteracaoAtributo();
							alteracaoAtributo.setCampo(atributoAuditoria.getTituloCampo());
							alteracaoAtributo.setDataAlteracao(auditoriaNovo.getRevisao().getRevisionDate());
							alteracaoAtributo.setLoginUsuario(auditoriaNovo.getRevisao().getUsuario().getEmail());
							
							alteracaoAtributo.setValorAntigo( formataValorStringAtributo(valorAntigo, atributoAuditoria) );
							alteracaoAtributo.setValorNovo( formataValorStringAtributo(valorNovo, atributoAuditoria) );
							alteracoes.add(alteracaoAtributo);
						}
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {}
				}
			}
		}
		
		return alteracoes;
	}
	
	private static String formataValorStringAtributo(Object valor, AtributoAuditoria atributoAuditoria) {
		if (valor == null) {
			return "";
		} else {
			String retorno;
			if (valor instanceof Boolean) {
				retorno = (Boolean)valor ? "Sim" : "Não";
			} else if (valor instanceof Date) {
				String formato = atributoAuditoria.getFormatoData() != null ? atributoAuditoria.getFormatoData() : Constantes.FORMATO_DATA_HORA;
				retorno = new SimpleDateFormat(formato).format((Date)valor);
			} else if (valor instanceof String) {
				retorno = (String)valor;
			} else {
				retorno = valor.toString();
			}
			
			if (atributoAuditoria.getDominios() != null) {
				retorno = atributoAuditoria.getDominios().get(retorno);
			}
			
			return retorno;
		}
	}
}
