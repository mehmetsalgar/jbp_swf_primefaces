package org.salgar.techdemo.async.workflow.service;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.common.model.Word;


/**
 * Simple Implementation of a WordCount Backend Service that sleeps a second for every word contained in the sentence.
 */
public class WordCountServiceImpl implements WordCountService {
    private static final Logger logger = Logger.getLogger(WordCountServiceImpl.class);
    private long sleepTimePerWord = 1000;

    /* (non-Javadoc)
     * @see de.tmobile.tvpp.async.workflow.service.WordCountService#countWords(de.tmobile.tvpp.common.model.Sentence)
     */
    public Sentence countWords(Sentence sentence) {

        ArrayList<Word> words = new ArrayList<Word>();
        StringTokenizer tokenizer = new StringTokenizer(sentence.getRawValue(), " \t\n\r\f.,;");
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            words.add(new Word(token));
        }
        sentence.setWords(words);

        try {
            Thread.sleep(2 * sleepTimePerWord);  // sleep a second for every word ...
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        return sentence;
    }


    /**
     * Sets the time to sleep per Word in milliseconds
     *
     * @param timeinMs
     */
    public void setSleepTimePerWord(long timeinMs) {
        sleepTimePerWord = timeinMs;
    }

}
