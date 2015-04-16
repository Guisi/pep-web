package br.edu.utfpr.mbean.atendimento.viewbean;

import java.io.Serializable;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import br.edu.utfpr.mbean.atendimento.EditarAtendimentoMBean;
import br.edu.utfpr.model.Atendimento;

public abstract class BaseAtendimentoViewBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    Application app = facesContext.getApplication();
	    ExpressionFactory elFactory = app.getExpressionFactory();
	    ELContext elContext = facesContext.getELContext();
	    ValueExpression valueExp = elFactory.createValueExpression(elContext, "#{" + beanName + "}",Object.class);
	    return (T) valueExp.getValue(elContext);
	}
	
	public Atendimento getAtendimentoSelecionado() {
		return ((EditarAtendimentoMBean)findBean("editarAtendimentoMBean")).getAtendimentoSelecionado();
	}
	
	public List<Atendimento> getAtendimentosAnteriores() {
		return ((EditarAtendimentoMBean)findBean("editarAtendimentoMBean")).getAtendimentosAnteriores();
	}
}
