package org.salgar.techdemo.web.listener;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import java.util.List;

import javax.faces.application.FacesMessage;
//import org.atmosphere.cpr.Broadcaster;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.listener.AsyncListener;
import org.salgar.techdemo.web.component.TextListResultBackingBeanConsumer;
import org.salgar.techdemo.web.model.WordCountManagedBean;

/**
 *
 */
public class AsyncWordCountListener implements AsyncListener {
    private static final Logger LOG = Logger.getLogger(WordCountManagedBean.class);
    private TextListResultBackingBeanConsumer consumer;
    //private volatile Broadcaster broadcaster;
    private boolean valid = true;
    private final String CHANNEL = "/text_list";

    public AsyncWordCountListener() {
    }

    public AsyncWordCountListener(TextListResultBackingBeanConsumer consumer) {
        this.consumer = consumer;
        //this.broadcaster = broadcaster;
    }

    /**
     * @see org.salgar.event.AsyncWorkflowListener#doResult(java.lang.String, java.lang.Object)
     */
    public void doResult(Object result) {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Sentence> returnedSentences= (List) result;
        if (valid) {
            consumer.addSentences(returnedSentences);
        }
        //broadcaster.broadcast("sentences are refreshed");
        if(LOG.isDebugEnabled()) {
        	LOG.debug("Sending asynchronous message!");
        }
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(CHANNEL , new FacesMessage(StringEscapeUtils.escapeHtml("sentences are refreshed")));
    }

    /**
     * @see org.salgar.event.AsyncWorkflowListener#isValid()
     */
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
