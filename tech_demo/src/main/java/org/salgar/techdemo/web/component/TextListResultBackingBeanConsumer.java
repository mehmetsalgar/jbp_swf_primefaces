package org.salgar.techdemo.web.component;

import java.util.List;

import org.salgar.techdemo.common.model.Sentence;

public interface TextListResultBackingBeanConsumer {
    public void addSentences(List<Sentence> sentences);
    public List<Sentence> getSentences();
    public boolean isWordCountRunning();
    public void setWordCountRunning(boolean wordCountRunning);

}