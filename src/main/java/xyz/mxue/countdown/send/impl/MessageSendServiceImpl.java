package xyz.mxue.countdown.send.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xyz.mxue.countdown.entity.SendMessageContext;
import xyz.mxue.countdown.enums.ExchangeEnum;
import xyz.mxue.countdown.send.MessageSendService;

import java.util.UUID;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/3/26 0:48
 * @version: 1.0
 */
@Service
public class MessageSendServiceImpl implements MessageSendService {

    @Autowired
    @Qualifier("firstRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendToRabbitmq(ExchangeEnum exchange, String routeKey, SendMessageContext<?> message) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend(exchange.getName(), routeKey,
                JSON.toJSONStringWithDateFormat(message, JSON.DEFFAULT_DATE_FORMAT,
                        SerializerFeature.WriteDateUseDateFormat), correlationId);
    }

    @Override
    public void sendToRabbitmq(ExchangeEnum exchange, SendMessageContext<?> message) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend(exchange.getName(), "msg",
                JSON.toJSONStringWithDateFormat(message, JSON.DEFFAULT_DATE_FORMAT,
                        SerializerFeature.DisableCircularReferenceDetect,
                        SerializerFeature.WriteDateUseDateFormat), correlationId);
    }
}
