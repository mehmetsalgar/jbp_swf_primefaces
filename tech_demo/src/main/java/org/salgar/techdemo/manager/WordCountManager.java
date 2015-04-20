package org.salgar.techdemo.manager;

import java.util.List;

import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.listener.AsyncListener;


public interface WordCountManager {

    public abstract String asynchronousWordCount(List<Sentence> sentences, AsyncListener listener);

}