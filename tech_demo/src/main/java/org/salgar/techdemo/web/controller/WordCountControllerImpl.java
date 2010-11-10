package org.salgar.techdemo.web.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.atmosphere.cpr.Broadcaster;
import org.salgar.comet.CometServiceLocator;
import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.manager.WordCountManager;
import org.salgar.techdemo.web.listener.AsyncWordCountListener;
import org.salgar.techdemo.web.model.WordCountManagedBean;

/**
 * Controller to mediate between the Frontend Backing Beans and the Backend
 */
public class WordCountControllerImpl implements WordCountController {
    private static final Logger LOG = Logger.getLogger(WordCountControllerImpl.class);
    WordCountManager wordCountManager;

    /*
     * (non-Javadoc)
     *
     * @see org.salgar.web.controller.WordCountController#executeWordCount(java.util.List,
     * org.salgar.web.model.WordCountManagedBean)
     */
    public void executeWordCount(List<Sentence> sentences, WordCountManagedBean wordCountManagedBean) {
        for (Sentence sentence : sentences) {
            AsyncWordCountListener listener = new AsyncWordCountListener(wordCountManagedBean, CometServiceLocator
                    .getInstance().getBroadcaster());
            wordCountManager.asynchronousWordCount(sentence, listener);
        }
    }

    public void saveSentences(List<Sentence> sentences) {
        LOG.info("saveSentences() is going to log the following Sentences to STDOUT ...");
        LOG.info(Arrays.deepToString(sentences.toArray()));
    }

    //
    // ========================= Dependency Injection Setter =======================================
    //

    public void setWordCountManager(WordCountManager wordCountManager) {
        this.wordCountManager = wordCountManager;
    }
}
