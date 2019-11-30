package together.example.demo;


import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/UserDomain")
public class Test {

    @RequestMapping("/FindUser")
    public UserValue FindUser(@RequestParam(value = "id")String id) throws InterruptedException {

        Message message = new Message();
        message.Data = id;
        message.isSuccessBoolean = "";
        message.LocalQueueName = "Test";
        message.DestinationQueueName = "UserDomain";
        message.ServiceRequest = "serviceExample";
        MQproduce.sendMessage(message);
        Message result = (Message) TestResult.queue.take();
        UserValue userValue=JSONObject.parseObject((String)result.Data, UserValue.class);
        return userValue;   
    }  
}