package com.kpi.multithreading.forkjoin.textkeyword;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

public class TextKeywordMatcher {

    public boolean matchesKeywords(String[] keywords, String text) {
        text = text.trim().replaceAll("\\s+", " ");

        final Map<String, Boolean> keywordsMap = Arrays.stream(keywords)
                .collect(Collectors.toMap(keyword -> keyword, keyword -> false));

        final TextMatchRecursive textMatch = new TextMatchRecursive(keywordsMap, text, 0, text.length());
        TextMatchRecursive.invokeAll(textMatch);

        return keywordsMap
                .values()
                .stream()
                .allMatch(bool -> bool);
    }

    private static class TextMatchRecursive extends RecursiveAction {
        private static final String WORD_DELIMITER = " ";

        private final Map<String, Boolean> keywords;

        private final String subtext;

        private final int beginText;

        private final int endText;

        public TextMatchRecursive(Map<String, Boolean> keywords, String subtext, int beginText, int endText) {
            this.keywords = keywords;
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
                this.verifyWord();
            }
        }

        private void verifyWord() {
            for (String keyword : keywords.keySet()) {
                if (subtext.equalsIgnoreCase(keyword)) {
                    synchronized (keywords) {
                        keywords.put(keyword, true);
                    }
                }
            }
        }

        private void splitJoin(int index) {
            final TextMatchRecursive splitLeft = new TextMatchRecursive(
                    keywords,
                    subtext.substring(0, index),
                    beginText,
                    beginText + index
            );
            final TextMatchRecursive splitRight = new TextMatchRecursive(
                    keywords,
                    subtext.substring(index + WORD_DELIMITER.length()),
                    beginText + index + WORD_DELIMITER.length(),
                    endText
            );
            ForkJoinTask.invokeAll(splitLeft, splitRight);
        }
    }
}
