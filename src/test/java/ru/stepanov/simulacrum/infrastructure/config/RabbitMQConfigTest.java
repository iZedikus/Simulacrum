package ru.stepanov.simulacrum.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class RabbitMQConfigTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(RabbitMQConfig.class);

    @Test
    void shouldDeclareOnlySimulacrumOwnedExchanges() {
        contextRunner.run(context -> {
            assertThat(context).hasBean("simulacrumExchange");
            assertThat(context).hasBean("simulacrumDeadLetterExchange");

            TopicExchange exchange = context.getBean("simulacrumExchange", TopicExchange.class);
            assertThat(exchange.getName()).isEqualTo("simulacrum.events");
            assertThat(exchange.isDurable()).isTrue();
            assertThat(exchange.isAutoDelete()).isFalse();

            TopicExchange dlx = context.getBean("simulacrumDeadLetterExchange", TopicExchange.class);
            assertThat(dlx.getName()).isEqualTo("simulacrum.events.dlx");
            assertThat(dlx.isDurable()).isTrue();
            assertThat(dlx.isAutoDelete()).isFalse();
        });
    }

    @Test
    void shouldNotDeclareOracleQueues() {
        contextRunner.run(context -> {
            assertThat(context).doesNotHaveBean("oracle.inbox");
            assertThat(context).doesNotHaveBean("oracle.inbox.dlq");
            assertThat(context).doesNotHaveBean("oracleInbox");
            assertThat(context).doesNotHaveBean("oracleInboxDlq");

            assertThat(context.getBeansOfType(Queue.class).values())
                    .extracting(Queue::getActualName)
                    .doesNotContain("oracle.inbox", "oracle.inbox.dlq");
        });
    }
}
