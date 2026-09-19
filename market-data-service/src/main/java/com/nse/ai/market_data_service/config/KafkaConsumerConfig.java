package com.nse.ai.market_data_service.config;

import com.nse.ai.market_data_service.dto.v1.kafka.MarketQuoteEvent;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, MarketQuoteEvent> consumerFactory() {

        JacksonJsonDeserializer<MarketQuoteEvent> jsonDeserializer =
                new JacksonJsonDeserializer<>(MarketQuoteEvent.class, false);

        return new DefaultKafkaConsumerFactory<>(
                java.util.Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, groupId
                ),
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MarketQuoteEvent>
    kafkaListenerContainerFactory(
            ConsumerFactory<String, MarketQuoteEvent> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, MarketQuoteEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}