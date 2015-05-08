package org.salgar.techdemo.web.consumerstrategy;

import org.salgar.techdemo.common.model.Sentence;
import org.salgar.techdemo.web.component.TextListResultBackingBeanConsumer;
import org.salgar.techdemo.web.model.WordCountManagedModel;

import java.util.List;

public class WordCountConsumerStrategyImpl implements WordCountConsumerStrategy, TextListResultBackingBeanConsumer {

   private WordCountManagedModel countManagedModel;

   public void setWordCountManagedModel(WordCountManagedModel countManagedModel) {
      this.countManagedModel = countManagedModel;
   }

   public List<Sentence> getSentences() {
      return countManagedModel.getSentences();
   }

   public boolean isWordCountRunning() {
      return false;
   }

   public void addSentences(List<Sentence> sentence) {
      countManagedModel.getSentences().addAll(sentence);
   }

   public void setWordCountRunning(boolean wordCountRunning) {
      countManagedModel.setWordCountRunning(wordCountRunning);

   }

}
