package org.salgar.techdemo.async.workflow.service;

import org.salgar.techdemo.common.model.Sentence;

public interface WordCountService {

    public abstract Sentence countWords(Sentence sentence);

}