package org.salgar.techdemo.web.listener;

import org.apache.log4j.Logger;
import org.atmosphere.cpr.Broadcaster;
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
    private volatile Broadcaster broadcaster;
    private boolean valid = true;

    public AsyncWordCountListener() {
    }

    public AsyncWordCountListener(TextListResultBackingBeanConsumer consumer, Broadcaster broadcaster) {
        this.consumer = consumer;
        this.broadcaster = broadcaster;
    }

    /**
     * @see de.tmobile.tvpp.event.AsyncWorkflowListener#doResult(java.lang.String, java.lang.Object)
     */
    public void doResult(String correlationID, Object result) {
        Sentence returnedSentence = (Sentence) result;
        if (valid) {
            consumer.addSentence(returnedSentence);
        }
        broadcaster.broadcast("sentences are refreshed");
    }

    /**
     * @see de.tmobile.tvpp.event.AsyncWorkflowListener#isValid()
     */
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
