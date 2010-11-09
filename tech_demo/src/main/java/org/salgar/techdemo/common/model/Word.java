/**
 *
 */
package org.salgar.techdemo.common.model;

import java.io.Serializable;

/**
 * Word Class (used in textdemo-portlet, please ignore)
 */
public class Word implements Serializable {
    private static final long serialVersionUID = 711849065490654502L;
    private String value;

    public Word() {}
    public Word(String word) {
        value = word;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
