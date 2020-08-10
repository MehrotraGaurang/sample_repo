//package com.example.analytics.service;
//
//import com.example.analytics.model.WordBinding;
//import org.apache.juli.logging.Log;
//import org.apache.juli.logging.LogFactory;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.Materialized;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.messaging.handler.annotation.SendTo;
//
////@EnableBinding(WordBinding.class)
//public class CountWords {
//    private final Log log = LogFactory.getLog(getClass());
//
////    @StreamListener
////    @SendTo(WordBinding.WORD_COUNT_OUT)
//    public KStream<String, Long> process(@Input(WordBinding.WORD_GEN_IN) KStream<String, String> words) {
//        return words
//                .map((key, value) -> new KeyValue<>(value, "0"))
//                .groupByKey()
//                .count(Materialized.as(WordBinding.WORD_COUNT_MV))
//                .toStream();
//    }
//
//}
