package org.salgar.spring.faces.webflow.context.portlet;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.springframework.faces.webflow.JsfRuntimeInformation;

public class JbpPortlerFacesContextFactory extends FacesContextFactory {

	private final FacesContextFactory factory;

	public JbpPortlerFacesContextFactory(FacesContextFactory factory) {
		this.factory = factory;
	}

	@Override
	public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle)
			throws FacesException {
		if (JsfRuntimeInformation.isPortletContext(context)) {
			return new JbpPortletFacesContextImpl((PortletContext) context, (PortletRequest) request,
					(PortletResponse) response);
		}
		return this.factory.getFacesContext(context, request, response, lifecycle);
	}
}
