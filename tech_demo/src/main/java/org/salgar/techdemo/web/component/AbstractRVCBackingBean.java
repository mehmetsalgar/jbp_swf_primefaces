package org.salgar.techdemo.web.component;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Common Reusable View Component (RVC) Base Class
 */
public abstract class AbstractRVCBackingBean implements Serializable {
    private static final long serialVersionUID = -1365363456234L;
    protected static final Logger logger = Logger.getLogger(AbstractRVCBackingBean.class);

    /**
     * Subclasses are supposed to set and cast the Dialog to its concrete Consumer Interface to satisfy the needs of the
     * RVC in question
     * 
     * @param parentDialog
     */
    // public abstract void setParentDialog(AbstractDialogBackingBean parentDialog);
}
