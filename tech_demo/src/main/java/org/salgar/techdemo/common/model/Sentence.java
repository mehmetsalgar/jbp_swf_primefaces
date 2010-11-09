package org.salgar.techdemo.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Sentence as a Domain object (used in textdemo-portlet, please ignore)
 */
public class Sentence implements Serializable {
    private static final long serialVersionUID = -264494451067317385L;
    private String rawValue;
    private List<Word> words;

    public Sentence() {}
    public Sentence(String sentence) {
        this.rawValue = sentence;
    }

    public String getRawValue() {
        return rawValue;
    }
    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public int getWordCount() {
        return words != null? words.size():0;
    }

    public List<Word> getWords() {
        return words;
    }
    public void setWords(List<Word> words) {
        this.words = words;
    }

    public String toString() {
        return rawValue;
    }
}
