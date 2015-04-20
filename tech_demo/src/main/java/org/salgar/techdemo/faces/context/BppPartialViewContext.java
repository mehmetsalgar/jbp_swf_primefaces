package org.salgar.techdemo.faces.context;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;

import org.primefaces.context.PrimePartialViewContext;

public class BppPartialViewContext extends PrimePartialViewContext {

	public BppPartialViewContext(PartialViewContext wrapped) {
		super(wrapped);
	}
	
	@Override
	public boolean isAjaxRequest() {
		return super.isAjaxRequest() || "partial/ajax".equals(FacesContext.getCurrentInstance().
                getExternalContext().getRequestHeaderMap().get("Faces-Request"));
	}

}
