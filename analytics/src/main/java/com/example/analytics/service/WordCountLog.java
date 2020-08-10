//package com.example.analytics.service;
//
//import com.example.analytics.model.WordBinding;
//import org.apache.juli.logging.Log;
//import org.apache.juli.logging.LogFactory;
//import org.apache.kafka.streams.kstream.ForeachAction;
//import org.apache.kafka.streams.kstream.KTable;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.stereotype.Component;
//
////@Component("WordCountLog")
////@EnableBinding(WordBinding.class)
//public class WordCountLog {
//    private final Log log = LogFactory.getLog(getClass());
//
////    @StreamListener
//    public void process(@Input(WordBinding.WORD_COUNT_IN)KTable<String, Long> words) {
//        words
//                .toStream()
//                .foreach(new ForeachAction<String, Long>() {
//                    @Override
//                    public void apply(String word, Long count) {
//                        log.info(word + " = " + count);
//                    }
//                });
//    }
//}
