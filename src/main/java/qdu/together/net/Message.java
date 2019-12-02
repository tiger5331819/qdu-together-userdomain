package qdu.together.net;
public class Message{
    public String ServiceRequest;
    public String ServiceRequestSource;
    public String LocalQueueName;
    public String DestinationQueueName;
    public Object Data;
    public String isSuccessBoolean;

    @Override
    public String toString() {
        return "Message [Data=" + Data + ", DestinationQueueName=" + DestinationQueueName + ", LocalQueueName="
                + LocalQueueName + ", ServiceRequest=" + ServiceRequest + ", ServiceRequestSource="
                + ServiceRequestSource + ", isSuccessBoolean=" + isSuccessBoolean + "]";
    }
    
}