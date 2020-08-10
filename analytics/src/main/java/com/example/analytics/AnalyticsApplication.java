package com.example.analytics;

import com.example.analytics.model.Word;
import com.example.analytics.model.WordBinding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableBinding(WordBinding.class)
public class AnalyticsApplication {

	@Component
	public static class PageViewEventSource implements ApplicationRunner {

		private final MessageChannel pageViewsOut;

		private final Log log = LogFactory.getLog(getClass());

		public PageViewEventSource(WordBinding binding) {
			this.pageViewsOut = binding.pageViewsOutput();
		}

		@Override
		public void run(ApplicationArguments args) throws Exception {

			List<String> names =Arrays.asList("the", "and");

			Runnable runnable = () -> {

				String rName = names.get(new Random().nextInt(names.size()));

				String word = rName;

				Message<String> message = MessageBuilder
													.withPayload(word)
													.setHeader(KafkaHeaders.MESSAGE_KEY, word.getBytes())
													.build();
				try{
					this.pageViewsOut.send(message);
					log.info("SENT " + message.toString());
				}
				catch (Exception e) {
					log.error(e);
				}
			};
			Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
		}
	}

	@Component
	public static class WordProcessor {

		private final Log log = LogFactory.getLog(getClass());
		final Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

		@StreamListener
		@SendTo (WordBinding.PAGE_COUNT_OUT)
		public KStream<String, Long> process(
				@Input (WordBinding.PAGE_VIEWS_IN) KStream<String, String> events) {

			 return events
					//.filter((key, value) -> value.getDuration() > 10)
					 .flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
					.groupBy((keyIgnored, word) -> word)
//					.groupByKey()
					//.windowedBy(TimeWindows.of(1000 * 60))
					.count(Materialized.as(WordBinding.PAGE_COUNT_MV))
					 .toStream();

		}
	}

	@Component
	public static class WordCount {
		private final Log log = LogFactory.getLog(getClass());
		@StreamListener public void process(@Input(WordBinding.PAGE_COUNT_IN) KTable<String, Long> counts) {

				counts
					.toStream()
					.foreach(new ForeachAction<String, Long>() {
						@Override
						public void apply(String s, Long aLong) {
							log.info(s + " = " + aLong);
						}
					});

		}
	}

	@RestController
	public static class CountRestController {
		private final InteractiveQueryService registry;

		public CountRestController(InteractiveQueryService registry) {
			this.registry = registry;
		}

		@GetMapping("/counts")
		Map<String, Long> counts() {
			Map<String, Long> counts = new HashMap<>();

			ReadOnlyKeyValueStore<String, Long> queryableStoreType =  this.registry.getQueryableStore(WordBinding.PAGE_COUNT_MV, QueryableStoreTypes.keyValueStore());

			KeyValueIterator<String, Long> all = queryableStoreType.all();

			while(all.hasNext()) {
				KeyValue<String, Long> value = all.next();
				counts.put(value.key, value.value);
			}

			return counts;

		}
	}

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}
}
