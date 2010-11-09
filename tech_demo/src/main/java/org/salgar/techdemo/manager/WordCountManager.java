package org.salgar.techdemo.manager;

import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.listener.AsyncListener;


public interface WordCountManager {

    public abstract String asynchronousWordCount(Sentence sentence, AsyncListener listener);

}