package org.salgar.techdemo.web.component;

public interface TextEntryBackingBeanConsumer {
    public boolean getHasEntriesInList();
    public void reverse(TextEntryBackingBean textEntryBackingBean);
    public void addToSentences(String rawSentence);
}
