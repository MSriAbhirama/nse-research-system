package com.nse.ai.market_data_service.config;

import com.nse.ai.market_data_service.dto.v1.kafka.MarketQuoteEvent;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    // DLT Producer Configuration
    @Bean
    public ProducerFactory<String, MarketQuoteEvent> dltProducerFactory() {

        Map<String, Object> producerConfig = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class,
                ProducerConfig.ACKS_CONFIG, "all",
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true,
                ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE,
                ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5
        );

        return new DefaultKafkaProducerFactory<>(producerConfig);
    }

    // DLT Kafka Template
    @Bean
    public KafkaTemplate<String, MarketQuoteEvent> dltKafkaTemplate(
            ProducerFactory<String, MarketQuoteEvent> dltProducerFactory) {

        return new KafkaTemplate<>(dltProducerFactory);
    }

    // Consumer Configuration
    @Bean
    public ConsumerFactory<String, MarketQuoteEvent> consumerFactory() {

        JacksonJsonDeserializer<MarketQuoteEvent> jsonDeserializer =
                new JacksonJsonDeserializer<>(MarketQuoteEvent.class, false);

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, groupId,
                        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false
                ),
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    // Error Handler with DLT Recovery
    @Bean
    public DefaultErrorHandler kafkaErrorHandler(
            KafkaTemplate<String, MarketQuoteEvent> dltKafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        dltKafkaTemplate,
                        (record, exception) ->
                                new TopicPartition(
                                        record.topic() + ".DLT",
                                        record.partition()
                                )
                );

        // Treat a failed DLT publication as a recovery failure
        recoverer.setFailIfSendResultIsError(true);

        FixedBackOff fixedBackOff = new FixedBackOff(2000L, 3L);

        return new DefaultErrorHandler(recoverer, fixedBackOff);
    }

    // Kafka Listener Container
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MarketQuoteEvent>
    kafkaListenerContainerFactory(
            ConsumerFactory<String, MarketQuoteEvent> consumerFactory,
            DefaultErrorHandler kafkaErrorHandler) {

        ConcurrentKafkaListenerContainerFactory<String, MarketQuoteEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        factory.getContainerProperties()
                .setAckMode(ContainerProperties.AckMode.RECORD);

        factory.setCommonErrorHandler(kafkaErrorHandler);

        return factory;
    }
}