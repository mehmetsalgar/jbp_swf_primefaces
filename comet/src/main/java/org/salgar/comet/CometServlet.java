package org.salgar.comet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.atmosphere.cpr.AtmosphereServlet;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.primefaces.comet.CometContext;
import org.primefaces.comet.PrimeFacesCometHandler;

public class CometServlet extends AtmosphereServlet {
    private static final long serialVersionUID = 7457627547476L;
    public static final String BROADCASTER = "BROADCASTER";

    @Override
    public void init(ServletConfig sc) throws ServletException {
        super.init(sc);
        DefaultBroadcaster defaultBroadcaster = new DefaultBroadcaster();
        atmosphereHandlers.put(CometContext.CHANNEL_PATH + "*", new AtmosphereHandlerWrapper(
                new PrimeFacesCometHandler(), defaultBroadcaster));
        sc.getServletContext().setAttribute(BROADCASTER, defaultBroadcaster);
        CometServiceLocator.getInstance().setBroadcaster(defaultBroadcaster);
    }
}
