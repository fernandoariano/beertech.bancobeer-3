package br.com.beertechtalents.lupulo.pocmq.config;

import br.com.beertechtalents.lupulo.pocmq.rest.TransacaoAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@RequiredArgsConstructor
public class RabbitConsumer {

    final TransacaoAdapter transacaoAdapter;

    @Value("${rabbitmq.operation.topic.name}")
    private String RABBITMQ_OPERATION_TOPIC_NAME;

    @Value("${rabbitmq.operation.queue.name}")
    private String RABBITMQ_OPERATION_QUEUE_NAME;

    @Value("${rabbitmq.transfer.topic.name}")
    private String RABBITMQ_TRANSFER_TOPIC_NAME;

    @Value("${rabbitmq.transfer.queue.name}")
    private String RABBITMQ_TRANSFER_QUEUE_NAME;

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public TopicExchange operationExchange() {
        return new TopicExchange(RABBITMQ_OPERATION_TOPIC_NAME, true, false);
    }

    @Bean
    public Queue inboundOperationQueue() {
        return new Queue(RABBITMQ_OPERATION_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding inboundOperationExchangeBinding() {
        return BindingBuilder.bind(inboundOperationQueue()).to(operationExchange()).with("*");
    }

    @Bean
    public TopicExchange transferExchange() {
        return new TopicExchange(RABBITMQ_TRANSFER_TOPIC_NAME, true, false);
    }

    @Bean
    public Queue inboundTransferQueue() {
        return new Queue(RABBITMQ_TRANSFER_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding inboundTransferExchangingBinding() {
        return BindingBuilder.bind(inboundTransferQueue()).to(transferExchange()).with("*");
    }

    @RabbitListener(queues = {"${rabbitmq.operation.queue.name}"})
    public void receiveOperation(final String msg) {
        System.out.println(msg);
        transacaoAdapter.callOperation(msg);
    }

    @RabbitListener(queues = {"${rabbitmq.transfer.queue.name}"})
    public void receiveTransfer(final String msg) {
        System.out.println(msg);
        transacaoAdapter.callTransfer(msg);
    }

}
