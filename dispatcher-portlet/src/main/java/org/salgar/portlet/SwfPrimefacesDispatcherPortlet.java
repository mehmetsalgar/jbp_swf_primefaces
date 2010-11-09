package org.salgar.portlet;

import java.util.Enumeration;
import java.util.Random;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.springframework.web.portlet.DispatcherPortlet;

public class SwfPrimefacesDispatcherPortlet extends DispatcherPortlet {
    @Override
    protected void doActionService(ActionRequest request, ActionResponse response) throws Exception {
        super.doActionService(request, response);
        //A variation of PortletUtils.passAllParametersToRenderPhase
        //PortletUtils.passAllParametersToRenderPhase(request, response);
        passPrimefacesParametersToRenderPhase(request, response);
        //Caching preventer
        response.setRenderParameter("rd", Double.valueOf(new Random().nextDouble()).toString());
    }
    
    /**
     * Pass all the action request parameters to the render phase by putting them into
     * the action response object. This may not be called when the action will call
     * {@link javax.portlet.ActionResponse#sendRedirect sendRedirect}.
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
            }
          }
       }
       catch (IllegalStateException ex) {
          // Ignore in case sendRedirect was already set.
       }
    }
}
