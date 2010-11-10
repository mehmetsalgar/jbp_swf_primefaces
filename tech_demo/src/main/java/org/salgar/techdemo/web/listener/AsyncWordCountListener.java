package org.salgar.techdemo.web.listener;

import org.apache.log4j.Logger;
import java.util.List;
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
     * @see org.salgar.event.AsyncWorkflowListener#doResult(java.lang.String, java.lang.Object)
     */
    public void doResult(String correlationID, Object result) {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Sentence> returnedSentences= (List) result;
        if (valid) {
            consumer.addSentences(returnedSentences);
        }
        broadcaster.broadcast("sentences are refreshed");
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
