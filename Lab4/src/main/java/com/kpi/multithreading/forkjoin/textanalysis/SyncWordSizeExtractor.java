package com.kpi.multithreading.forkjoin.textanalysis;

import java.util.HashMap;
import java.util.Map;

public class SyncWordSizeExtractor {

    private static final String WORD_DELIMITER = " ";

    public Map<Integer, Integer> extractWordSizes(String text) {
        text = text.trim().replaceAll("\\s+", " ");

        final Map<Integer, Integer> wordsSizes = new HashMap<>();
        for (String word : text.split(WORD_DELIMITER)) {
            wordsSizes.compute(word.length(), (k, v) -> v == null ? 1 : v + 1);
        }

        return wordsSizes;
    }
}
