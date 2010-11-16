package org.salgar.portlet;

import java.util.Enumeration;
import java.util.Random;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.springframework.web.portlet.DispatcherPortlet;

/**
 * Subclasses DispatcherPortlet to implement the necessary functionality for Spring Workflow
 * and Primefaces to work with JBoss Portal
 * 
 * @author salgar
 *
 */
public class SwfPrimefacesDispatcherPortlet extends DispatcherPortlet {
    private static final String NAMESPACE_PARAMETER = "org.salgar.portlet.NAMESPACE";
    /**
     * This method is responsible of receiving the JSF Submit request an process HTTP POST and then
     * redirect request to simulate the render phase of a portlet. Unfortunately during this operation
     * POST parameters would be lost but some of them are important for the Primefaces.
     * 
     * For this reason we are coping the parameters that are relevant for the Primefaces and pass
     * them to the render phase.
     * 
     * Also the browsers has a tendency to cache the HTTP GET request, to prevent this we are adding a
     * random valued request parameter.
     */
    @Override
    protected void doActionService(ActionRequest request, ActionResponse response) throws Exception {
        super.doActionService(request, response);
        // A variation of PortletUtils.passAllParametersToRenderPhase
        // PortletUtils.passAllParametersToRenderPhase(request, response);
        passPrimefacesParametersToRenderPhase(request, response);
        // Caching preventer
        response.setRenderParameter("rd", Double.valueOf(new Random().nextDouble() * 100000000000.0).toString());
    }

    /**
     * Pass all the action request parameters to the render phase by putting them into the action response object. This
     * may not be called when the action will call {@link javax.portlet.ActionResponse#sendRedirect sendRedirect}.
     * 
     * @param request the current action request
     * @param response the current action response
     * @see javax.portlet.ActionResponse#setRenderParameter
     */
    private static void passPrimefacesParametersToRenderPhase(ActionRequest request, ActionResponse response) {
        try {
            @SuppressWarnings("rawtypes")
            Enumeration en = request.getParameterNames();
            while (en.hasMoreElements()) {
                String param = (String) en.nextElement();
                if (param.indexOf("primefaces") >= 0) {
                    String values[] = request.getParameterValues(param);
                    response.setRenderParameter(param, values);
                } else if(param.indexOf(NAMESPACE_PARAMETER) >= 0) {
                    String values[] = request.getParameterValues(param);
                    response.setRenderParameter(param, values);                    
                }
            }
        } catch (IllegalStateException ex) {
            // Ignore in case sendRedirect was already set.
        }
    }
}
