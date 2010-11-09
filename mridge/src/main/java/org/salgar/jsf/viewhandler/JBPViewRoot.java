package org.salgar.jsf.viewhandler;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class JBPViewRoot extends UIViewRoot implements NamingContainer {
    private static final String SEPARATOR = (new Character(NamingContainer.SEPARATOR_CHAR)).toString();
    public static final String ROOT_ID = "_viewRoot";
    private String clientId;

    public JBPViewRoot() {
        super();
        super.setId(ROOT_ID);
    }

//    @Override
//    public String getClientId(FacesContext context) {
//        if (null == this.clientId) {
//            this.clientId = convertClientId(context, this, super.getId());
//        }
//        return this.clientId;
//    }

    public static String convertClientId(FacesContext context, UIViewRoot root, String additionalId) {
        ExternalContext ec = context.getExternalContext();
        String namespace = ec.encodeNamespace(SEPARATOR);

        /*
         * In servlet world encodeNamespace does nothing -- so if we get back what we sent in then do not perturn the
         * NamingContainer Id
         */
        if (namespace.length() > 1) {
            if (additionalId != null) {
                return "_" + namespace + additionalId;
            } else {
                return "_" + namespace;
            }
        } else {
            return additionalId;
        }
    }

    /**
     * Implements NamingContainer semantics. Ensures that the returned identifier contains the consumer (portal)
     * provided unique portlet id. This ensures that those components in this NamingContainer generate ids which will
     * not collide in the consumer page. Implementation merely calls the static form of this method.
     */
    @Override
    public String getContainerClientId(FacesContext context) {
        return convertClientId(context, this, super.getContainerClientId(context));
    }
}
