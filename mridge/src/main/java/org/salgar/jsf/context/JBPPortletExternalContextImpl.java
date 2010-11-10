package org.salgar.jsf.context;

import java.net.MalformedURLException;
import java.util.Random;

import javax.faces.FacesException;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

public class JBPPortletExternalContextImpl extends com.sun.faces.portlet.ExternalContextImpl {
    private static final String NAMESPACE_PARAMETER = "org.salgar.portlet.NAMESPACE";
    private static final String ACTION_URL_DO_NOTHITG = "/JBossPortletBridge/actionUrl/do/nothing";

    public JBPPortletExternalContextImpl(PortletContext context, PortletRequest request, PortletResponse response) {
        super(context, request, response);
    }

    @Override
    public String encodeNamespace(String name) {
        String namespace = (String) ((PortletRequest) this.getRequest()).getPortletSession().getAttribute(
                NAMESPACE_PARAMETER);
        return (namespace + name);
    }

    @Override
    public String encodeActionURL(String url) {
        if (null == url) {
            throw new NullPointerException();
        }

        String actionUrl = url;

        if (!actionUrl.startsWith("#")) {
            try {
                PortalActionURL portalActionURL = new PortalActionURL(url);
                portalActionURL.setPath(getRequestContextPath() + ACTION_URL_DO_NOTHITG);
                // Preventing caching
                portalActionURL.setParameter("rd", Double.valueOf(new Random().nextDouble() * 10000000000000.0)
                        .toString());

                return portalActionURL.toString();
            } catch (MalformedURLException e) {
                throw new FacesException(e);
            }
        }
        return url;
    }
}
