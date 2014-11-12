package br.edu.utfpr.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.utils.FormatUtils;

/**
 * Conversor de CPF.
 * 
 * @author Douglas Guisi
 */
@FacesConverter(value = "cpfConverter")
public class CpfConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String cpf = value;
		if (StringUtils.isNotBlank(cpf)) {
			cpf = FormatUtils.somenteDigitos(cpf);
		}
		return cpf;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		return (String) value;
	}
}