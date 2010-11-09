package org.salgar.jsf.context;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;

public class JBPRenderPortletExternalContextImpl extends com.sun.faces.portlet.ExternalContextImpl {
    private static final String NAMESPACE_PARAMETER = "org.glassbox.portlet.NAMESPACE";

    public JBPRenderPortletExternalContextImpl(PortletContext context, PortletRequest request, PortletResponse response) {
        super(context, request, response);
    }

    @Override
    public String encodeNamespace(String name) {
        String namespace = super.encodeNamespace(name);
        ((PortletRequest) this.getRequest()).setAttribute(NAMESPACE_PARAMETER, namespace);
        return namespace;
    }

    @Override
    public String encodeActionURL(String url) {
        if (null == url) {
            throw new NullPointerException();
        }

        String actionUrl = url;

        if (!actionUrl.startsWith("#")) {

            // PortalActionURL portalActionURL = new PortalActionURL(url);

//            RenderResponse renderResponse = (RenderResponse) this.getResponse();
//            PortletURL portletURL = renderResponse.createActionURL();
//            portletURL.setParameter(NAMESPACE_PARAMETER, super.encodeNamespace(""));

            ((RenderRequest) this.getRequest()).getPortletSession().setAttribute(NAMESPACE_PARAMETER,
                    super.encodeNamespace(""));

//            return portletURL.toString();
        }
        return url;
    }
}
