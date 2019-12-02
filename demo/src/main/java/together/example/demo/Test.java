package together.example.demo;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/UserDomain")
public class Test {

    @GetMapping("/User")
    public Object FindUser(@RequestParam(value = "id") String id) throws InterruptedException {

        Message message = new Message();
        message.Data = id;
        message.isSuccessBoolean = "";
        message.LocalQueueName = "Test";
        message.DestinationQueueName = "UserDomain";
        message.ServiceRequest = "ServiceExample";
        message.ServiceRequestSource="FindUser";
        MQproduce.sendMessage(message);
        Message result = (Message) TestResult.queue.take();
        UserValue userValue=JSONObject.parseObject((String)result.Data, UserValue.class);
        ResultUser user=new ResultUser(userValue.userID,userValue.userName,userValue.userTouxiang);
        return user;   
    }  
    @RequestMapping("/test")
    public Object Tt(@RequestParam(value = "id",defaultValue = "45")String id){
        List<Ttt> list=new LinkedList<Ttt>();
        Ttt t=new Ttt();
        t.a="1";t.b="1";
        list.add(t);
        t.a="2";t.b="2";
        list.add(t);
        for(Ttt tt :list){
            System.out.println(tt);
        }

        return list;
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