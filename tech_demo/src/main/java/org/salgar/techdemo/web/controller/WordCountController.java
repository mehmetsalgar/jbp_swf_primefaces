package org.salgar.techdemo.web.controller;

import java.util.List;

import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.web.model.WordCountManagedBean;

public interface WordCountController {

    public abstract void executeWordCount(List<Sentence> sentences, WordCountManagedBean wordCountManagedBean);

    public abstract void saveSentences(List<Sentence> sentences);

}