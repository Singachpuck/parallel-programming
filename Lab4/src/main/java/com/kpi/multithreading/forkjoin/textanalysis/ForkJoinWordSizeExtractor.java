package com.kpi.multithreading.forkjoin.textanalysis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinWordSizeExtractor {

    public Map<Integer, Integer> extractWordSizes(String text) {
        text = text.trim().replaceAll("\\s+", " ");
        final Map<Integer, Integer> wordSizes = new HashMap<>();
        final WordSizeRecursiveExtractor sizeExtractor = new WordSizeRecursiveExtractor(
                wordSizes, text, 0, text.length());

        RecursiveTask.invokeAll(sizeExtractor);
        sizeExtractor.join();
        return wordSizes;
    }

    private static class WordSizeRecursiveExtractor extends RecursiveAction {

        private static final String WORD_DELIMITER = " ";

        private final Map<Integer, Integer> wordSizes;

        private final String subtext;

        private final int beginText;

        private final int endText;

        public WordSizeRecursiveExtractor(Map<Integer, Integer> wordSizes, String subtext, int beginText, int endText) {
            this.wordSizes = wordSizes;
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
                this.countWord();
            }
        }

        private void countWord() {
            final int wordSize = subtext.length();
            synchronized (wordSizes) {
                wordSizes.compute(wordSize, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        private void splitJoin(int index) {
            final WordSizeRecursiveExtractor splitLeft = new WordSizeRecursiveExtractor(
                    wordSizes,
                    subtext.substring(0, index),
                    beginText,
                    beginText + index
            );
            final WordSizeRecursiveExtractor splitRight = new WordSizeRecursiveExtractor(
                    wordSizes,
                    subtext.substring(index + WORD_DELIMITER.length()),
                    beginText + index + WORD_DELIMITER.length(),
                    endText
            );
            ForkJoinTask.invokeAll(splitLeft, splitRight);
        }
    }
}
