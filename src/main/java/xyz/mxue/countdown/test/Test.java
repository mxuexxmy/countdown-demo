package xyz.mxue.countdown.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.mxue.countdown.entity.SendMessageContext;
import xyz.mxue.countdown.enums.ExchangeEnum;
import xyz.mxue.countdown.send.MessageSendService;

import java.util.Random;

/**
 * @author mxuexxmy
 * @date 2023/3/26 0:55
 * @version 1.0
 */
@Component
public class Test {

    @Autowired
    private MessageSendService messageSendService;


    public void testSend() throws Exception {
        SendMessageContext<Long> sendMessageContext = new SendMessageContext<>();
        sendMessageContext.setMark("/test");
        sendMessageContext.setContent(new Random().nextLong());
        messageSendService.sendToRabbitmq(ExchangeEnum.COUNTDOWN, "01", sendMessageContext);
    }

}
