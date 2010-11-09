package org.salgar.techdemo.web.component;

import java.util.List;

import org.salgar.techdemo.common.model.Sentence;

public interface TextListResultBackingBeanConsumer {
    public void addSentence(Sentence sentence);
    public List<Sentence> getSentences();
    public boolean isWordCountRunning();

}