package com.kpi.multithreading.forkjoin.commontextwords;

import org.junit.jupiter.api.Test;

import java.util.TreeSet;

class ForkJoinCommonWordsTest {

    private final String text1 = """
            Machine learning is a branch of artificial intelligence that involves training computers to learn from data, without being explicitly programmed. It is a rapidly growing field that has the potential to transform industries ranging from healthcare to finance to transportation.
                        
            At its core, machine learning is about building mathematical models that can identify patterns in data, and then use those patterns to make predictions or decisions about new data. These models are typically built using algorithms such as decision trees, neural networks, and support vector machines, and they are trained using large datasets of labeled examples.
                        
            One of the key advantages of machine learning is its ability to automate tasks that would otherwise be too complex or time-consuming for humans to perform. For example, machine learning algorithms can be used to analyze medical images and diagnose diseases, or to predict which customers are most likely to churn from a subscription service.
                        
            Despite its potential benefits, machine learning is not without its challenges. One of the biggest challenges is data quality: if the data used to train a machine learning model is biased or incomplete, the model will likely make inaccurate or unfair predictions. Another challenge is interpretability: because machine learning models can be highly complex and opaque, it can be difficult to understand how they are making decisions.
                        
            Despite these challenges, machine learning is a rapidly advancing field that holds tremendous promise for the future. As more and more data becomes available, and as algorithms become more sophisticated, we can expect machine learning to play an increasingly important role in our lives.
            """;

    private final String text2 = """
            Machine learning is a powerful tool that is being used across a wide range of applications, from natural language processing to computer vision to robotics. At its core, machine learning involves building mathematical models that can learn from data, without being explicitly programmed.
                        
            One of the key advantages of machine learning is its ability to discover patterns and insights in large, complex datasets. For example, machine learning algorithms can be used to analyze financial data and identify fraud patterns, or to analyze customer data and identify segments that are most likely to respond to a marketing campaign.
                        
            Another advantage of machine learning is its ability to improve over time. As more data is fed into a machine learning model, the model becomes more accurate and reliable. This is known as "learning by example," and it is a key aspect of many machine learning algorithms.
                        
            Despite its many benefits, machine learning also presents some challenges. One of the biggest challenges is the need for large amounts of high-quality data. Without sufficient data, machine learning models may not be able to generalize well to new, unseen data. Another challenge is the need for skilled professionals who can develop and train machine learning models, as well as interpret the results.
                        
            As machine learning continues to advance, we can expect it to have a major impact on a wide range of industries and fields. From healthcare to finance to transportation, machine learning is already being used to automate tasks, improve efficiency, and drive innovation. As data becomes increasingly important in our world, machine learning will continue to play a crucial role in helping us extract insights and make better decisions.
            """;

    private final ForkJoinCommonWords commonWords = new ForkJoinCommonWords();

    @Test
    void search() {
        for (String word : new TreeSet<>(commonWords.search(text1, text2))) {
            System.out.println(word);
        }
    }
}