package org.salgar.techdemo.manager;

import java.util.List;

import org.salgar.techdemo.async.workflow.service.SimpleParallelWorkflowServiceImpl;
import org.salgar.techdemo.async.workflow.service.WordCountServiceImpl;
import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.listener.AsyncListener;

/**
 * Backend Manager for counting words ...
 */
public class WordCountManagerImpl implements WordCountManager {
    SimpleParallelWorkflowServiceImpl parallelWorkflowService;

    /**
     * @see org.salgar.manager.WordCountManager#asynchronousWordCount(org.salgar.techdemo.common.model.Sentence,
     *      org.salgar.event.AsyncWorkflowListener)
     */
    public String asynchronousWordCount(List<Sentence> sentences, AsyncListener listener) {
        Object[] arguments = { sentences };
        return parallelWorkflowService.asyncExecute(WordCountServiceImpl.class, "countWords", arguments, 10000L,
                listener);
    }

    public void setParallelWorkflowService(SimpleParallelWorkflowServiceImpl parallelWorkflowService) {
        this.parallelWorkflowService = parallelWorkflowService;
    }
}
