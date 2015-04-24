package br.edu.utfpr.converter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.edu.utfpr.model.BaseEntity;

@FacesConverter(value = "pickListConverter")
public class PickListConverter implements Converter {
	
	private static Logger logger = Logger.getLogger(PickListConverter.class.getName());

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		return getObjectFromUIPickListComponent(uiComponent, value);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		String string = "";  
        if (object != null) {
            try {
                string = String.valueOf(((BaseEntity) object).getId());
            } catch (ClassCastException cce) {
            	logger.log(Level.SEVERE, "Erro em PickListConverter.", cce);
                throw new ConverterException(cce);
            }
        }
        return string;
	}
	
	@SuppressWarnings("unchecked")  
    private BaseEntity getObjectFromUIPickListComponent(UIComponent component, String value) {  
        final DualListModel<BaseEntity> dualList;
        try {  
            dualList = (DualListModel<BaseEntity>) ((PickList) component).getValue();  
            BaseEntity entity = getObjectFromList(dualList.getSource(), Long.valueOf(value));  
            if (entity == null) {  
            	entity = getObjectFromList(dualList.getTarget(), Long.valueOf(value));  
            }  
  
            return entity;
        } catch (ClassCastException cce) {  
        	logger.log(Level.SEVERE, "Erro em PickListConverter.", cce);
            throw new ConverterException(cce);
        } catch (NumberFormatException nfe) {  
        	logger.log(Level.SEVERE, "Erro em PickListConverter.", nfe);  
            throw new ConverterException(nfe);
        }  
    }
	
	private BaseEntity getObjectFromList(final List<?> list, final Long id) {
        for (final Object object : list) {
            final BaseEntity entity = (BaseEntity) object;
            if (entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;  
    }
}