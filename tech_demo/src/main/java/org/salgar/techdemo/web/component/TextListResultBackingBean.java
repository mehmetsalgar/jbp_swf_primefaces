package org.salgar.techdemo.web.component;

import java.util.ArrayList;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.primefaces.comet.CometContext;
import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.web.listener.GuiRefreshCallback;

/**
 * Text List Result Backing Bean (RVC)
 */
public class TextListResultBackingBean extends AbstractRVCBackingBean implements GuiRefreshCallback {
    private static final long serialVersionUID = 6706778320710585459L;
    
    private TextListResultBackingBeanConsumer consumer;

    public boolean isEnabled() {
        return consumer != null ? consumer.isWordCountRunning() : true;
    }

//    public DataModel getSentences() {
//        sentences = new SerializableListDataModel(parentDialog != null ? parentDialog.getSentences()
//                : new ArrayList<String>());
//        return sentences;
//    }
    
    public DataModel getSentences() {
        return consumer != null ? consumer.getSentences().isEmpty() ? null : new ListDataModel(consumer.getSentences()) : new ListDataModel(new ArrayList<Sentence>());
    }

    public void setSentences(DataModel sentences) {
        logger.info("Sentences are placed: " + sentences);
    }

    public void doGuiRefresh() {
        CometContext.publish("text_list", "sentences are refreshed");
    }

    public void setConsumer(TextListResultBackingBeanConsumer consumer) {
        this.consumer = consumer;
    }
}
