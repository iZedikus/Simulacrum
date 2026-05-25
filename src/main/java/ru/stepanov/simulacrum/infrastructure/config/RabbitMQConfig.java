package ru.stepanov.simulacrum.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "simulacrum.events";
    public static final String TRANSACTION_CREATED_KEY = "transaction.created";
    public static final String ORACLE_QUEUE = "oracle.inbox";
    public static final String ORACLE_DLQ = "oracle.inbox.dlq";

    @Bean
    TopicExchange simulacrumExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    Queue oracleInbox() {
        return QueueBuilder.durable(ORACLE_QUEUE).deadLetterExchange("").deadLetterRoutingKey(ORACLE_DLQ).build();
    }

    @Bean
    Queue oracleInboxDlq() {
        return QueueBuilder.durable(ORACLE_DLQ).build();
    }

    @Bean
    Binding oracleBinding() {
        return BindingBuilder.bind(oracleInbox()).to(simulacrumExchange()).with(TRANSACTION_CREATED_KEY);
    }

    @Bean
    MessageConverter rabbitMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
