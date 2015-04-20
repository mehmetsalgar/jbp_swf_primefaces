package org.salgar.techdemo.flowhandler;

import org.springframework.webflow.mvc.portlet.AbstractFlowHandler;

public class TechdemoFlowHandler extends AbstractFlowHandler {
    @Override
    public String getFlowId() {      
        return "demo";
    }
}
