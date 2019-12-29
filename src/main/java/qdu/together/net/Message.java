package qdu.together.net;

/**
 * 自定义协议，用于规范系统传入传出数据
 * ClassType：ValueObject
 */
public class Message{
    public String ServiceRequest;//目的服务名称
    public String ServiceRequestSource;//本地服务名称
    public String LocalQueueName;//本地通道名称
    public String DestinationQueueName;//目的通道名称
    public Object Data;//携带的数据
    public String isSuccessBoolean;//所请求服务状态标识
    public String Remark="NetServiceAOP";

    @Override
    public String toString() {
        return "Message [Data=" + Data + ", DestinationQueueName=" + DestinationQueueName + ", LocalQueueName="
                + LocalQueueName + ", ServiceRequest=" + ServiceRequest + ", ServiceRequestSource="
                + ServiceRequestSource + ", isSuccessBoolean=" + isSuccessBoolean + "]";
    }
    
}