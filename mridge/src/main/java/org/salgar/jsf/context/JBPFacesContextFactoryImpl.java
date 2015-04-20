package org.salgar.jsf.context;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import com.sun.faces.portlet.FacesContextFactoryImpl;
import com.sun.faces.portlet.FacesContextImpl;

public class JBPFacesContextFactoryImpl extends FacesContextFactory {
    private static final String JSF_RI_FACES_CONTEXT = "com.sun.faces.context.FacesContextImpl";
    private com.sun.faces.portlet.FacesContextFactoryImpl delegate;

    public JBPFacesContextFactoryImpl(FacesContextFactory defaultFactory) {
        super();
        this.delegate = (FacesContextFactoryImpl) defaultFactory;
    }

    @Override
    public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle)
            throws FacesException {
        if ((context == null) || (request == null) || (response == null) || (lifecycle == null)) {
            throw new NullPointerException();
        }
        if (hasJSFRI() && context instanceof ServletContext) {
            ExternalContext econtext = new com.sun.faces.context.ExternalContextImpl((ServletContext) context,
                    (ServletRequest) request, (ServletResponse) response);

            return new com.sun.faces.context.FacesContextImpl(econtext, lifecycle);

        } else if ((context instanceof PortletContext) && (request instanceof RenderRequest)
                && (response instanceof RenderResponse)) {
            ExternalContext econtext = new JBPRenderPortletExternalContextImpl((PortletContext) context,
                    (PortletRequest) request, (PortletResponse) response);

            return (new FacesContextImpl(econtext, lifecycle));
        } else if ((context instanceof PortletContext) && (request instanceof ActionRequest)
                && (response instanceof ActionResponse)) {

            ExternalContext econtext = new JBPPortletExternalContextImpl((PortletContext) context,
                    (PortletRequest) request, (PortletResponse) response);

            return (new FacesContextImpl(econtext, lifecycle));
        }
        return delegate.getFacesContext(context, request, response, lifecycle);
    }

    // Check if the bridge is running with JSF RI
    private boolean hasJSFRI() {
        try {
            Class.forName(JSF_RI_FACES_CONTEXT);
        } catch (Throwable t) {
            return false;
        }
        return true;
    }

}
