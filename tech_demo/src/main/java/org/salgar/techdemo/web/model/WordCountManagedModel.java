package org.salgar.techdemo.web.model;

import java.util.List;

import org.salgar.techdemo.common.model.Sentence;

public interface WordCountManagedModel {

    public abstract List<Sentence> getSentences();

}