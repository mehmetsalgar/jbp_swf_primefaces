package org.salgar.techdemo.async.workflow.service;

import java.util.List;

import org.salgar.techdemo.common.model.Sentence;

public interface WordCountService {

    public abstract List<Sentence> countWords(List<Sentence> sentences);

}