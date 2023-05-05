package com.kpi.multithreading.forkjoin.commontextwords;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ForkJoinCommonWords {

    public Set<String> search(String... texts) {
        if (texts.length == 0) {
            return null;
        }
        final List<Set<String>> allWords = new ArrayList<>();

        final List<RecursiveAction> actions = new ArrayList<>();
        for (String text : texts) {
            text = text.trim().toLowerCase().replaceAll("\\s+", " ");
            final Set<String> words = new HashSet<>();
            actions.add(new FindWordsRecursiveAction(words, text, 0, text.length()));
            allWords.add(words);
        }

        ForkJoinTask.invokeAll(actions);

        final Set<String> first = allWords.get(0);
        for (int i = 1; i < allWords.size(); i++) {
            first.retainAll(allWords.get(i));
        }

        return first;
    }

    static class FindWordsRecursiveAction extends RecursiveAction {

        private final Collection<String> words;

        private static final String WORD_DELIMITER = " ";

        private final String subtext;

        private final int beginText;

        private final int endText;

        public FindWordsRecursiveAction(Collection<String> words, String subtext, int beginText, int endText) {
            this.words = words;
            this.subtext = subtext;
            this.beginText = beginText;
            this.endText = endText;
        }

        @Override
        protected void compute() {
            final int center = subtext.length() / 2;
            final int indexRight = subtext.indexOf(WORD_DELIMITER, center);
            final int indexLeft = subtext.lastIndexOf(WORD_DELIMITER, center);

            if (indexRight != -1 || indexLeft != -1) {
                if (center - indexLeft > Math.abs(indexRight - center)) {
                    this.splitJoin(indexRight);
                } else {
                    this.splitJoin(indexLeft);
                }
            } else {
                this.computeWord();
            }
        }

        private void computeWord() {
            words.add(subtext.replaceAll("^\\W", "").replaceAll("\\W$", ""));
        }

        private void splitJoin(int index) {
            final FindWordsRecursiveAction splitLeft = new FindWordsRecursiveAction(
                    words,
                    subtext.substring(0, index),
                    beginText,
                    beginText + index
            );
            final FindWordsRecursiveAction splitRight = new FindWordsRecursiveAction(
                    words,
                    subtext.substring(index + WORD_DELIMITER.length()),
                    beginText + index + WORD_DELIMITER.length(),
                    endText
            );
            ForkJoinTask.invokeAll(splitLeft, splitRight);
        }
    }
}
