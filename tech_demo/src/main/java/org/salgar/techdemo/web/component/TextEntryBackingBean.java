package org.salgar.techdemo.web.component;

import org.apache.commons.lang3.StringUtils;

import javax.faces.event.ActionEvent;
import java.io.Serializable;

/**
 * Text Entry Backing Bean (RVC)
 */
public class TextEntryBackingBean extends AbstractRVCBackingBean implements Serializable {
    private static final long serialVersionUID = -7191580409683620472L;

    private String textEntered;
    private TextEntryBackingBeanConsumer consumer;

    public TextEntryBackingBean() {
        super();
    }

    public boolean isDoesHaveListEntries() {
        if (consumer != null) {
            return consumer.getHasEntriesInList();
        } 
        return true;
    }

    public String getTextEntered() {
        return textEntered;
    }

    public void setTextEntered(String textEntered) {
        this.textEntered = textEntered;
    }

    public void reverseListener(ActionEvent event) {
        setTextEntered(StringUtils.reverse(getTextEntered()));
    }

    public void reset() {
        textEntered = null;
    }

    public void setConsumer(TextEntryBackingBeanConsumer consumer) {
        this.consumer = consumer;
    }

    //
    // ====================== IoD Setter for Delegation ==============================
    //

}
