package org.salgar.techdemo.web.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 * Text List Backing Bean (RVC)
 */
public class TextListBackingBean extends AbstractRVCBackingBean {
    private static final long serialVersionUID = -7167773272034660629L;
    // private DataModel sentences;
    private TextListBackingBeanConsumer consumer;

    public DataModel getSentences() {
        return (consumer != null) ? new ListDataModel(consumer.getRawSentences()) : new ListDataModel(new ArrayList<String>(1));
    }

    public String getSentencesAsString() {
        if (consumer != null) {
            List<String> rawList = consumer.getRawSentences();
            return rawList != null ? Arrays.deepToString(rawList.toArray()) : "";
        } else {
            return "";
        }        
    }

    public void setConsumer(TextListBackingBeanConsumer consumer) {
        this.consumer = consumer;
    }
}
