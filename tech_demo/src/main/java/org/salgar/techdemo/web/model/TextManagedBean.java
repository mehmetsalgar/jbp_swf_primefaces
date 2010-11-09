package org.salgar.techdemo.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.web.component.TextEntryBackingBean;
import org.salgar.techdemo.web.component.TextEntryBackingBeanConsumer;
import org.salgar.techdemo.web.component.TextListBackingBeanConsumer;
import org.salgar.techdemo.web.consumerstrategy.TextConsumerStrategy;
import org.springframework.beans.factory.InitializingBean;

/**
 * Managed Bean that holds the User entered Sentences
 */
public class TextManagedBean implements TextEntryBackingBeanConsumer, TextListBackingBeanConsumer, InitializingBean, Serializable, TextManagedModel {
    private static final long serialVersionUID = 5528320351208438288L;

    private static final Logger LOG = Logger.getLogger(TextManagedBean.class);

    private List<Sentence> sentences = new ArrayList<Sentence>();
    
    private TextEntryBackingBeanConsumer textEntryConsumer;
    private TextListBackingBeanConsumer textListConsumer;

    /*
     * (non-Javadoc)
     * 
     * @see org.salgar.techdemo.web.model.TextManagedModel#getSentences()
     */
    public List<Sentence> getSentences() {
        return sentences;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.salgar.techdemo.web.model.TextManagedModel#setSentences(java.util.List)
     */
    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    
  

    protected void resetSentences() {
        LOG.info("## TextManagedBean ## Reset Sentences.");
        sentences = new ArrayList<Sentence>();
    }

    //
    // ============================= Lifecycle =============================
    //

    public void init() {
        LOG.info("## TextManagedBean ## Init TextManagedBean.");
    }

    public void destroy() {
        LOG.info("## TextManagedBean ## Destroy TextManagedBean.");
        this.textListConsumer = null;
    }

    public void reset() {
        resetSentences();
    }
       
    //
    //===============================Interfaces - Consumers ================
    //
    public List<String> getRawSentences() {
        return textListConsumer != null ? textListConsumer.getRawSentences() : new ArrayList<String>(1);               
    }
    
    public boolean getHasEntriesInList() {       
        return textEntryConsumer.getHasEntriesInList();
    }
    
    public void reverse(TextEntryBackingBean textEntryBackingBean) {
        LOG.info("## TextManagedBean ## reversed Sentence: " + textEntryBackingBean.getTextEntered());
        this.textEntryConsumer.reverse(textEntryBackingBean);
    }
    
    public void addToSentences(String rawSentence) {
        LOG.info("## TextManagedBean ## added Sentence: " + rawSentence);
        this.textEntryConsumer.addToSentences(rawSentence);
    }
    
    //
    //=============================IOC - SWF=============================
    //
    public void setTextListConsumer(TextListBackingBeanConsumer consumer) {
        this.textListConsumer = consumer;
    }
    
    public void setTextEntryConsumer(TextEntryBackingBeanConsumer textEntryConsumer) {
        this.textEntryConsumer = textEntryConsumer;
    }
    
    public void afterPropertiesSet() throws Exception {
        ((TextConsumerStrategy) this.textListConsumer).setTextManagedModel(this);
        ((TextConsumerStrategy)this.textEntryConsumer).setTextManagedModel(this);
    }    
}
