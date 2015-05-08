package org.salgar.techdemo.web.consumerstrategy;

import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.web.component.TextListBackingBeanConsumer;
import org.salgar.techdemo.web.model.TextManagedModel;

import java.util.ArrayList;
import java.util.List;

public class TextListConsumerStrategy implements TextConsumerStrategy, TextListBackingBeanConsumer {

   private TextManagedModel managedModel;

   public void setTextManagedModel(TextManagedModel managedModel) {
      this.managedModel = managedModel;
   }

   public List<String> getRawSentences() {
      return getRawSentencesValue();
   }

   public List<String> getRawSentencesValue() {
      List<String> rawSentences = new ArrayList<String>();
      for (Sentence sentence : this.managedModel.getSentences()) {
         rawSentences.add(sentence.getRawValue());
      }

      return rawSentences;
   }
}
