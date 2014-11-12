package br.edu.utfpr.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.constants.MessageName;
import br.edu.utfpr.utils.FormatUtils;

/**
 * Classe para validacao de CPF
 * 
 * @author Douglas Guisi
 */
@FacesValidator("cpfValidator")
public class CpfValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object cpf) throws ValidatorException {
		/* Deixa somente os numeros */
		String cpfStr = FormatUtils.somenteDigitos(String.valueOf(cpf));
		ResourceBundle bundle = ResourceBundle.getBundle(MessageName.MESSAGES.value(), Constantes.LOCALE_PT_BR);

		try {
			new CPFValidator(false).assertValid(cpfStr);
		} catch (InvalidStateException e) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(bundle.getString("validator.cpfcnpjvalidator.msg.erro.cnpjinvalido"));
			throw new ValidatorException(message);
		}
	}
}