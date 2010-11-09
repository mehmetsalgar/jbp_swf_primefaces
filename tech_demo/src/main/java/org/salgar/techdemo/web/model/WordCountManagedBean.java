package org.salgar.techdemo.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.web.component.TextListResultBackingBeanConsumer;
import org.salgar.techdemo.web.consumerstrategy.WordCountConsumerStrategy;
import org.springframework.beans.factory.InitializingBean;

/**
 *  Managed Bean that holds the Sentences that are already calculated for its word counts among other state related to
 *  the Word Count functionality.
 *
 *  WordCountManagedBean serves as a process centric Managed Bean (Session/Global Session scope)
 */
public class WordCountManagedBean implements TextListResultBackingBeanConsumer, WordCountManagedModel, InitializingBean, Serializable  {
    private static final long serialVersionUID = 8876415198285682580L;
    
    private static final Logger LOG = Logger.getLogger(WordCountManagedBean.class);
    private List<Sentence> sentences = new ArrayList<Sentence>();
    private TextListResultBackingBeanConsumer consumer;

    //
    // ============================= Lifecycle =============================
    //

    public void init() {
        LOG.info("## WordCountManagedBean ## Init WordCountManagedBean.");
    }

    public void destroy() {
        LOG.info("## WordCountManagedBean ## Destroy WordCountManagedBean.");
    }

    /* (non-Javadoc)
     * @see org.salgar.techdemo.web.model.WordCountManagedModel#getSentences()
     */
    public void reset() {
       sentences = new ArrayList<Sentence>();
    }
        
    /**
     * TextListResultBackingBeanConsumer
     */    
    public List<Sentence> getSentences() {
        return sentences;
    }
    
    public boolean isWordCountRunning() {       
        return consumer.isWordCountRunning();
    }
    
    public void addSentence(Sentence sentence) {
       consumer.addSentence(sentence);        
    }

    public void afterPropertiesSet() throws Exception {
        ((WordCountConsumerStrategy)this.consumer).setWordCountManagedModel(this);
    }

    public void setConsumer(TextListResultBackingBeanConsumer consumer) {
        this.consumer = consumer;
    }
}
