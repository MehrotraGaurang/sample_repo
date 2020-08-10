//package com.example.analytics.data_source;
//
//import com.example.analytics.model.Word;
//import com.example.analytics.model.WordBinding;
//import org.apache.juli.logging.Log;
//import org.apache.juli.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
////@Repository
////@EnableBinding(WordBinding.class)
//public class GenerateData implements ApplicationRunner {
//
//    private final MessageChannel outputWords;
//    private final Log log = (Log) LogFactory.getLog(getClass());
//
//    @Autowired
//    public GenerateData(WordBinding binding) {
//        this.outputWords = binding.wordsGenOutput();
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        List<String> sample_words = Arrays.asList("the", "and");
//
//        Runnable runnable = () -> {
//            String rword = sample_words.get(new Random().nextInt(sample_words.size()));
//
//            Word word = new Word(rword);
//
//            Message<Word> message = MessageBuilder
//                    .withPayload(word)
//                    .setHeader(KafkaHeaders.MESSAGE_KEY, word.getWord().getBytes())
//                    .build();
//
//            System.out.println("\nReached Till HERE!!!\n");
//
//            try{
//                this.outputWords.send(message);
//                log.info("SENT " + message.toString());
//            } catch (Exception e){
//                log.info("\n ENCOUNTERED ERROR!!!! \n");
//                log.error(e);
//            }
//        };
//    }
//}
