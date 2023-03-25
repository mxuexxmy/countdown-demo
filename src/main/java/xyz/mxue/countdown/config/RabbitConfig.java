package xyz.mxue.countdown.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import xyz.mxue.countdown.constants.MqConstants;
import xyz.mxue.countdown.enums.ExchangeEnum;

/**
 * @author mxuexxmy
 * @date 2023/3/26 0:13
 * @version 1.0
 */
@Configuration
public class RabbitConfig {

    @Bean(name = "firstConnectionFactory")
    @Primary
    public ConnectionFactory firstConnectionFactory(
            @Value("${spring.rabbitmq.first.host}") String host,
            @Value("${spring.rabbitmq.first.port}") int port,
            @Value("${spring.rabbitmq.first.username}") String username,
            @Value("${spring.rabbitmq.first.password}") String password,
            @Value("${spring.rabbitmq.first.virtualHost}") String virtualHost
    ) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean(name = "secondConnectionFactory")
    public ConnectionFactory secondConnectionFactory(
            @Value("${spring.rabbitmq.second.host}") String host,
            @Value("${spring.rabbitmq.second.port}") int port,
            @Value("${spring.rabbitmq.second.username}") String username,
            @Value("${spring.rabbitmq.second.password}") String password,
            @Value("${spring.rabbitmq.second.virtualHost}") String virtualHost,
            @Value("${spring.rabbitmq.second.publisher-returns}") boolean publisherReturns
    ) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(publisherReturns);
        return connectionFactory;
    }

    @Bean(name = "firstRabbitTemplate")
    @Primary
    public RabbitTemplate firstRabbitTemplate(@Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate firstRabbitTemplate = new RabbitTemplate(connectionFactory);
        return firstRabbitTemplate;
    }

    @Bean(name = "secondRabbitTemplate")
    public RabbitTemplate secondRabbitTemplate(@Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate secondRabbitTemplate = new RabbitTemplate();
        secondRabbitTemplate.setConnectionFactory(connectionFactory);
        secondRabbitTemplate.setMandatory(true);
        return secondRabbitTemplate;
    }

    @Bean(name = "firstFactory")
    public SimpleRabbitListenerContainerFactory firstFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(name = "secondFactory")
    public SimpleRabbitListenerContainerFactory secondFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean("firstRabbitAdmin")
    public RabbitAdmin rabbitAdmin(@Qualifier("firstRabbitTemplate") RabbitTemplate rabbitTemplate) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        //默认就是true
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 初始化交换机
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean("createExchange")
    public Object createExchange(@Qualifier("firstRabbitAdmin") RabbitAdmin rabbitAdmin) {
        // 遍历交换机枚举
        ExchangeEnum.toList().forEach(exchangeEnum -> {
            // 创建路由交换机
            rabbitAdmin.declareExchange(new DirectExchange(exchangeEnum.getName(), true, false));
        });
        return null;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MqConstants.EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(MqConstants.QUEUE);
    }

    @Bean
    public Binding queueBindingExchange(@Qualifier("queue") Queue queue, @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#").noargs();
    }

}
