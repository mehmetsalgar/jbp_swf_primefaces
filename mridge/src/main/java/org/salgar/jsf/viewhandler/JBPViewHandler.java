package org.salgar.jsf.viewhandler;

import java.io.IOException;
import java.io.Writer;

import javax.faces.FacesException;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.sun.facelets.FaceletViewHandler;

/**
 * <p>
 * Concrete implementation of <code>ViewHandler</code> for use in a portlet environment. This implementation delegates
 * to the standard <codeViewHandler</code> instance provided to our constructor, and only implements portlet-specific
 * behavior where necessary.
 * </p>
 */

public final class JBPViewHandler extends FaceletViewHandler {
    private static final String SAVESTATE_FIELD_MARKER = "~com.sun.faces.saveStateFieldMarker~";

    private ViewHandler parent;

    public JBPViewHandler(ViewHandler parent) {
        super(parent);
    }

    /**
     * Null Writer so the under laying JSF framework can do something to the stream response which would be an invalid
     * action against the portal.
     */
    private static final Writer nullWriter = new Writer() {
        public void close() throws IOException {
            // Do nothing
        }

        public void flush() throws IOException {
            // Do nothing
        }

        public void write(char[] cbuf, int off, int len) throws IOException {
            // Do nothing
        }
    };

    /**
     * Delivers nullWriter instead of a real writer so it would not effect the portal response writer.
     * 
     * 
     */
    protected ResponseWriter createResponseWriter(FacesContext context) throws IOException, FacesException {
        ResponseWriter writer;
        RenderKit renderKit = context.getRenderKit();
        ExternalContext extContext = context.getExternalContext();
        if (extContext.getRequest() instanceof ActionRequest) {
            writer = renderKit.createResponseWriter(nullWriter, "text/html", "UTF-8");

            return writer;
        }

        // Object lifecyclePhaseAttr = extContext.getRequestMap().get(Bridge.PORTLET_LIFECYCLE_PHASE);
        // if (Bridge.PortletPhase.RenderPhase.equals(lifecyclePhaseAttr)) {
        RenderRequest request = (RenderRequest) extContext.getRequest();
        RenderResponse response = (RenderResponse) extContext.getResponse();
        String contenttype = request.getResponseContentType();
        if (contenttype == null) {
            contenttype = "text/html";
        }
        String encoding = response.getCharacterEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        writer = renderKit.createResponseWriter(nullWriter, contenttype, encoding);
        contenttype = writer.getContentType();
        // apply them to the response
        response.setContentType(contenttype);
        // Now, clone with the real writer
        writer = writer.cloneWithWriter(response.getWriter());
        // } else {
        // writer = super.createResponseWriter(context);
        // }

        return writer;
    }

    /**
     * Provide a state writer in Portal environment.
     */
    @Override
    public void writeState(FacesContext context) throws IOException {
        StringBuilderWriter stringBuilderWriter = StringBuilderWriter.getInstance();
        if (null != stringBuilderWriter) {
            stringBuilderWriter.stateWrited();
            context.getResponseWriter().write(SAVESTATE_FIELD_MARKER);
        } else {
            super.writeState(context);
        }
    }

    @Override
    protected ViewHandler getWrapped() {
        return parent;
    }

    /**
     * Writer implementation for the JSF State which travels inside of a ThreadLocal. So when stateWrited called instead
     * of writting to the response it is written to the StringBuilder.
     * 
     * @author salgar
     * 
     */
    private static final class StringBuilderWriter extends Writer {

        private static final ThreadLocal<StringBuilderWriter> instance = new ThreadLocal<StringBuilderWriter>();
        private final StringBuilder mBuilder;
        private final FacesContext context;
        private final Writer responseWriter;
        private boolean stateWrited = false;
        private static final int SAVESTATE_MARK_LEN = SAVESTATE_FIELD_MARKER.length();

        public StringBuilderWriter(FacesContext context, Writer responseWriter, int initialCapacity) {
            if (initialCapacity < 0) {
                throw new IllegalArgumentException();
            }
            mBuilder = new StringBuilder(initialCapacity);
            this.context = context;
            this.responseWriter = responseWriter;
            instance.set(this);
        }

        @SuppressWarnings("unused")
        public void release() {
            instance.remove();
        }

        public void stateWrited() {
            this.stateWrited = true;

        }

        public static StringBuilderWriter getInstance() {
            return instance.get();
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            if (off < 0 || off > cbuf.length || len < 0 || off + len > cbuf.length || off + len < 0) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            }
            if (stateWrited) {
                mBuilder.append(cbuf, off, len);
            } else {
                responseWriter.write(cbuf, off, len);
            }
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }

        /**
         * Write a string.
         * 
         * @param str String to be written
         */
        @Override
        public void write(String str) throws IOException {
            if (stateWrited) {
                mBuilder.append(str);
            } else {
                responseWriter.write(str);
            }
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            if (stateWrited) {
                mBuilder.append(str, off, off + len);
            } else {
                responseWriter.write(str, off, len);
            }
        }

        @SuppressWarnings("unused")
        public StringBuilder getBuffer() {
            return mBuilder;
        }

        @Override
        public String toString() {
            return mBuilder.toString();
        }
        
        /**
         * If JSF State is written it would replace the original writer with a clone of
         * the original writer and commit changes to this writer instead of the real response
         * writer. After this operation complete, it replace old writer so it stay valid for the
         * point of Portal.
         * 
         * @throws IOException
         */
        @SuppressWarnings("unused")
        public void flushToWriter() throws IOException {
            // TODO: Buffer?
            if (stateWrited) {
                StateManager stateManager = context.getApplication().getStateManager();
                ResponseWriter oldResponseWriter = context.getResponseWriter();
                context.setResponseWriter(oldResponseWriter.cloneWithWriter(responseWriter));
                Object stateToWrite = stateManager.saveView(context);
                int pos = 0;
                int tildeIdx = mBuilder.indexOf(SAVESTATE_FIELD_MARKER);
                while (tildeIdx >= 0) {
                    responseWriter.write(mBuilder.substring(pos, tildeIdx));
                    stateManager.writeState(context, stateToWrite);
                    pos = tildeIdx + SAVESTATE_MARK_LEN;
                    tildeIdx = mBuilder.indexOf(SAVESTATE_FIELD_MARKER, pos);
                }
                responseWriter.write(mBuilder.substring(pos));
                context.setResponseWriter(oldResponseWriter);
            }
        }
    }

    @Override
    public UIViewRoot createView(FacesContext context, String viewId) {
        UIViewRoot root = super.createView(context, viewId);

        try {
            UIViewRoot portletRoot = new JBPViewRoot();
            portletRoot.setViewId(root.getViewId());
            portletRoot.setLocale(root.getLocale());
            portletRoot.setRenderKitId(root.getRenderKitId());
            root = portletRoot;
        } catch (Exception e) {
            throw new FacesException(e);
        }

        return root;
    }
}
