package org.salgar.techdemo.web.consumerstrategy;

import org.apache.commons.lang.StringUtils;
import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.web.component.TextEntryBackingBean;
import org.salgar.techdemo.web.component.TextEntryBackingBeanConsumer;
import org.salgar.techdemo.web.model.TextManagedModel;

public class TextEntryConsumerStrategy implements TextEntryBackingBeanConsumer, TextConsumerStrategy {
    private TextManagedModel managedModel;

    public void setTextManagedModel(TextManagedModel managedModel) {
        this.managedModel = managedModel;
    }

    public boolean getHasEntriesInList() {
        return this.managedModel.getSentences() != null && this.managedModel.getSentences().isEmpty();
    }

    public void reverse(TextEntryBackingBean textEntryBackingBean) {
        textEntryBackingBean.setTextEntered(StringUtils.reverse(textEntryBackingBean.getTextEntered()));
    }
    
    public void addToSentences(String rawSentence) {       
        this.managedModel.getSentences().add(new Sentence(rawSentence));
    }

    public void addToSentences(Sentence sentence) {       
        this.managedModel.getSentences().add(sentence);
    }
}
