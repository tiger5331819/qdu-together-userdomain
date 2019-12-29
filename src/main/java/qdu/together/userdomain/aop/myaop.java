package qdu.together.userdomain.aop;

import java.util.Queue;

import com.alibaba.fastjson.JSONObject;

import qdu.together.net.Message;
import qdu.together.net.rabbitmq.MQproduce;
import qdu.together.togethercore.core.AOP;
import qdu.together.togethercore.core.DomainAOP;
import qdu.together.togethercore.core.Type;
import qdu.together.togethercore.core.TypeMessage;

/**
 * 此乃自定义AOP
 * 通过@DomainAOP来注入核心中
 * 通过@Type来确定此AOP处理的消息类型
 */
@DomainAOP(DomainAOPName = "myaop")
@Type(TypeMessage = TypeMessage.Message)
public class myaop extends AOP {
    private Queue<Message> sendMessageQueue;

    public myaop(Queue<Message> sendMessageQueue) {
        super(myannotation.class);
        this.sendMessageQueue=sendMessageQueue;
    }

    @Override
    protected void prepare() {
        System.out.println("myAOP");
    }

    @Override
    protected void finish() {
        Message msg = sendMessageQueue.poll();
        if (msg != null) {
            MQproduce.sendMessage(takePackage(msg));
            System.out.println("finish");
        }

    }
    private Message takePackage(Message msg) {
        Message message = new Message();
        message.LocalQueueName = msg.DestinationQueueName;
        message.DestinationQueueName = msg.LocalQueueName;
        message.ServiceRequest = msg.ServiceRequestSource;
        message.ServiceRequestSource = msg.ServiceRequest;
        message.Data = JSONObject.toJSONString(msg.Data);
        message.isSuccessBoolean = msg.isSuccessBoolean;
        message.Remark=msg.Remark;
        return message;
    }
    
}

