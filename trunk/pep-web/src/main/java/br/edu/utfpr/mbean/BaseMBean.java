package br.edu.utfpr.mbean;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import br.edu.utfpr.exception.AppException;


public abstract class BaseMBean implements Serializable {
	
	private static final long serialVersionUID = -4584871656033166513L;

	@ManagedProperty("#{msg}")
	private ResourceBundle msgs;
	
	@ManagedProperty("#{err}")
	private ResourceBundle errs;

	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    Application app = facesContext.getApplication();
	    ExpressionFactory elFactory = app.getExpressionFactory();
	    ELContext elContext = facesContext.getELContext();
	    ValueExpression valueExp = elFactory.createValueExpression(elContext, "#{" + beanName + "}",Object.class);
	    return (T) valueExp.getValue(elContext);
	}
	
	public void addMessage(Severity severity, String message, boolean keepMessages) {
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, message, ""));
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(keepMessages);
	}

	public void addInfoMessage(String message) {
		addMessage(FacesMessage.SEVERITY_INFO, message, false);
	}
	
	public void addErrorMessage(String message) {
		addMessage(FacesMessage.SEVERITY_ERROR, message, false);
	}
	
	public void addWarnMessage(String message) {
		addMessage(FacesMessage.SEVERITY_WARN, message, false);
	}
	
	public void addInfoMessage(String message, boolean keepMessages) {
		addMessage(FacesMessage.SEVERITY_INFO, message, keepMessages);
	}
	
	public void addErrorMessage(String message, boolean keepMessages) {
		addMessage(FacesMessage.SEVERITY_ERROR, message, keepMessages);
	}
	
	public void addWarnMessage(String message, boolean keepMessages) {
		addMessage(FacesMessage.SEVERITY_WARN, message, keepMessages);
	}

	public ResourceBundle getMsgs() {
		return msgs;
	}

	public void setMsgs(ResourceBundle msgs) {
		this.msgs = msgs;
	}

	public ResourceBundle getErrs() {
		return errs;
	}

	public void setErrs(ResourceBundle errs) {
		this.errs = errs;
	}
	
	protected void addressException(AppException exception) {
		String errorCode = exception.getErrorCode();
		
		if (errorCode != null) {
			addErrorMessage(getErrs().getString(errorCode));
		} else {
			throw new RuntimeException(exception);
		}
	}
	
	public String getEmptyStr() {
		return "";
	}
}
