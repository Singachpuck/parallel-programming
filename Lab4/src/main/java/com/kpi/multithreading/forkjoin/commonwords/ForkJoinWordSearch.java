package com.kpi.multithreading.forkjoin.commonwords;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinWordSearch {

    public List<Position> search(String word, String text) {
        text = text.trim().replaceAll("\\s+", " ");

        final FindWordRecursiveTask findWords = new FindWordRecursiveTask(word, text, 0, text.length());
        final ForkJoinPool commonPool = ForkJoinPool.commonPool();
        return commonPool.invoke(findWords);
    }

    private static class FindWordRecursiveTask extends RecursiveTask<List<Position>> {

        private static final String WORD_DELIMITER = " ";

        private final String word;

        private final String subtext;

        private final int beginText;

        private final int endText;

        public FindWordRecursiveTask(String word, String subtext, int beginText, int endText) {
            this.word = word;
            this.subtext = subtext;
            this.beginText = beginText;
            this.endText = endText;
        }

        @Override
        protected List<Position> compute() {
            final int center = subtext.length() / 2;
            final int indexRight = subtext.indexOf(WORD_DELIMITER, center);
            final int indexLeft = subtext.lastIndexOf(WORD_DELIMITER, center);

            if (indexRight != -1 || indexLeft != -1) {
                if (center - indexLeft > Math.abs(indexRight - center)) {
                    return splitJoin(indexRight);
                } else {
                    return splitJoin(indexLeft);
                }
            } else {
                return this.verifyWord();
            }
        }

        private List<Position> verifyWord() {
            final List<Position> positions = new ArrayList<>();
            if (subtext.equalsIgnoreCase(word)) {
                positions.add(new Position(beginText, endText));
            }
            return positions;
        }

        private List<Position> splitJoin(int index) {
            final FindWordRecursiveTask splitLeft = new FindWordRecursiveTask(
                    word,
                    subtext.substring(0, index),
                    beginText,
                    beginText + index
            );
            final FindWordRecursiveTask splitRight = new FindWordRecursiveTask(
                    word,
                    subtext.substring(index + WORD_DELIMITER.length()),
                    beginText + index + WORD_DELIMITER.length(),
                    endText
            );
            ForkJoinTask.invokeAll(splitLeft, splitRight);
            final List<Position> positions = new ArrayList<>();
            positions.addAll(splitLeft.join());
            positions.addAll(splitRight.join());
            return positions;
        }
    }
}
