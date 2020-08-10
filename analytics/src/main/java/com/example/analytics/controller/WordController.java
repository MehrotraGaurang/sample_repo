//package com.example.analytics.controller;
//
//import com.example.analytics.model.WordBinding;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.state.KeyValueIterator;
//import org.apache.kafka.streams.state.QueryableStoreType;
//import org.apache.kafka.streams.state.QueryableStoreTypes;
//import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
////@RestController
////@EnableBinding(WordBinding.class)
//public class WordController {
//    private final InteractiveQueryService  registry;
//
//    public WordController(InteractiveQueryService interactiveQueryService) {
//        this.registry = interactiveQueryService;
//    }
//
////    @GetMapping("/show")
//    Map<String, Long> count() {
//        Map<String, Long> word_count = new HashMap<>();
//
//        ReadOnlyKeyValueStore<String, Long> queries =
//                this.registry.getQueryableStore(WordBinding.WORD_COUNT_MV, QueryableStoreTypes.keyValueStore());
//
//        KeyValueIterator<String,Long> all = queries.all();
//
//        while(all.hasNext()){
//            KeyValue<String , Long> wordC = all.next();
//            word_count.put(wordC.key, wordC.value);
//        }
//        return word_count;
//    }
//}
