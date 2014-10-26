package br.edu.utfpr.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.edu.utfpr.model.Autorizacao;

@FacesConverter(value = "autorizacaoPickListConverter")
public class AutorizacaoPickListConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		return getObjectFromUIPickListComponent(uiComponent, value);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		String string = "";  
        if (object != null) {
            try {
                string = String.valueOf(((Autorizacao) object).getId());
            } catch (ClassCastException cce) {
                throw new ConverterException(cce);
            }
        }
        return string;
	}
	
	@SuppressWarnings("unchecked")  
    private Autorizacao getObjectFromUIPickListComponent(UIComponent component, String value) {  
        final DualListModel<Autorizacao> dualList;
        try {  
            dualList = (DualListModel<Autorizacao>) ((PickList) component).getValue();  
            Autorizacao personType = getObjectFromList(dualList.getSource(), Long.valueOf(value));  
            if (personType == null) {  
            	personType = getObjectFromList(dualList.getTarget(), Long.valueOf(value));  
            }  
  
            return personType;
        } catch (ClassCastException cce) {  
            cce.printStackTrace();  
            throw new ConverterException();  
        } catch (NumberFormatException nfe) {  
            nfe.printStackTrace();  
            throw new ConverterException();  
        }  
    }
	
	private Autorizacao getObjectFromList(final List<?> list, final Long id) {
        for (final Object object : list) {
            final Autorizacao personType = (Autorizacao) object;
            if (personType.getId().equals(id)) {
                return personType;
            }
        }
        return null;  
    }
}