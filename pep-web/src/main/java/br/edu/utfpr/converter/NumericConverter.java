package br.edu.utfpr.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.utils.FormatUtils;

/**
 * Conversor para campos somente digitos
 * 
 * @author Douglas Guisi
 */
@FacesConverter(value = "numericConverter")
public class NumericConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		String valor = value;
		if (StringUtils.isNotBlank(valor)) {
			valor = FormatUtils.somenteDigitos(valor);
		}
		return valor;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		return (String) value;
	}
}