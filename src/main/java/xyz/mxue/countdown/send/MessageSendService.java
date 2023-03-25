package xyz.mxue.countdown.send;

import xyz.mxue.countdown.entity.SendMessageContext;
import xyz.mxue.countdown.enums.ExchangeEnum;

/**
 * @author mxuexxmy
 * @date 2023/3/26 0:46
 * @version 1.0
 */
public interface MessageSendService {

    /**
     * 发送到交换机 绑定指定路由键
     * 适用发送到指定机组队列，
     * 如机组监控信息 默认使用机组编码当路邮键
     *
     * @param exchange   交换机名称
     * @param routeKey   路由键
     * @param message 消息体
     */
    void sendToRabbitmq(ExchangeEnum exchange, String routeKey, SendMessageContext<?> message);


    /**
     *  发送到交换机 绑定默认路由键
     *  适用于发送不区分机组的消息
     *  如大屏，看板，车间监控总览，这类数据不区分机组
     *
     * @param exchange   交换机名称
     * @param message 消息体
     */
    void sendToRabbitmq(ExchangeEnum exchange, SendMessageContext<?> message);
}
