package qdu.together.togethercore.core;

import java.util.Queue;

import com.alibaba.fastjson.JSONObject;

import qdu.together.net.Message;
import qdu.together.net.rabbitmq.MQproduce;
import qdu.together.togethercore.core.AOP;
import qdu.together.togethercore.core.DomainAOP;
import qdu.together.togethercore.core.Type;
import qdu.together.togethercore.core.TypeMessage;
import qdu.together.togethercore.service.ServiceDo;

/**
 * 框架支持的NetServiceAOP增强，用于处理RabbitMQ接受到的外部服务请求
 * @author 苏琥元
 * @version 泰建雅0.4
 */
@DomainAOP(DomainAOPName = "NetServiceAOP")
@Type(TypeMessage = TypeMessage.Message)
public class NetServiceAOP extends AOP {
    private Queue<Message> sendMessageQueue;

    public NetServiceAOP(Queue<Message> sendMessageQueue) {
        super(ServiceDo.class);
        this.sendMessageQueue = sendMessageQueue;
    }

    @Override
    protected void prepare() {
        System.out.println("prepare");
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