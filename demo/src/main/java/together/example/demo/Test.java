package together.example.demo;

import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import together.togethercore.amqp.AMQPCore;
import together.togethercore.amqp.ResultQueue;




@RestController()
@RequestMapping("/UserDomain")
@ResultQueue("UserDomain.User")
public class Test {

    @GetMapping("/User")
    public Object FindUser(@RequestParam(value = "id") String id) throws InterruptedException {

        Message message = new Message();
        message.Data = id;
        message.isSuccessBoolean = "";
        message.LocalQueueName = "Test";
        message.DestinationQueueName = "UserDomain";
        message.ServiceRequest = "ServiceExample";
        message.ServiceRequestSource="UserDomain.User";
        MQproduce.sendMessage(message);
        Message result = AMQPCore.getInstance().getResult("UserDomain.User");
        UserValue userValue=JSONObject.parseObject((String)result.Data, UserValue.class);
        ResultUser user=new ResultUser(userValue.userID,userValue.userName,userValue.userTouxiang);
        return user;   
    }  
    @GetMapping("/User2")
    public Object uiod() throws InterruptedException {

        Message message = new Message();
        message.Data = "1001";
        message.isSuccessBoolean = "";
        message.LocalQueueName = "Test";
        message.DestinationQueueName = "UserDomain";
        message.ServiceRequest = "ServiceExample";
        message.ServiceRequestSource="UserDomain.User";
        MQproduce.sendMessage(message);
        Message result = AMQPCore.getInstance().getResult("UserDomain.User");
        UserValue userValue=JSONObject.parseObject((String)result.Data, UserValue.class);
        ResultUser user=new ResultUser(userValue.userID,userValue.userName,userValue.userTouxiang);
        user.Uid="123";
        return user;   
    }
}
class ResultUser{
    public String Uid;
    public String Uname;
    public byte[] Utouxiang;

    public ResultUser(String uid, String uname, byte[] utouxiang) {
        Uid = uid;
        Uname = uname;
        Utouxiang = utouxiang;
    }
    
}