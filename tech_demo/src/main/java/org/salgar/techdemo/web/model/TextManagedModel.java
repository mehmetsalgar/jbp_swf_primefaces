package org.salgar.techdemo.web.model;

import java.util.List;

import org.salgar.techdemo.common.model.Sentence;

public interface TextManagedModel {

    public abstract List<Sentence> getSentences();

    public abstract void setSentences(List<Sentence> sentences);

}