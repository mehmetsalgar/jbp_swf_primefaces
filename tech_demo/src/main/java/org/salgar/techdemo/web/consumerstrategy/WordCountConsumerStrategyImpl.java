package org.salgar.techdemo.web.consumerstrategy;

import java.util.List;

import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.web.component.TextListResultBackingBeanConsumer;
import org.salgar.techdemo.web.model.WordCountManagedModel;

public class WordCountConsumerStrategyImpl implements WordCountConsumerStrategy, TextListResultBackingBeanConsumer {
    private WordCountManagedModel countManagedModel;

    public void setWordCountManagedModel(WordCountManagedModel countManagedModel) {
        this.countManagedModel = countManagedModel;
    }

    public List<Sentence> getSentences() {
        return countManagedModel.getSentences();
    }

    public boolean isWordCountRunning() {
        return false;
    }

    public void addSentences(List<Sentence> sentence) {
        countManagedModel.getSentences().addAll(sentence);
    }

}
