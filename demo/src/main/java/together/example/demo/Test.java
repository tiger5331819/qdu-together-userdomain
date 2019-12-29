package together.example.demo;

import java.text.DateFormat;
import java.util.*;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        message.Remark="myaop";
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
    @RequestMapping("/Test")
    public Object iiiii(@RequestBody String jsonstr){
        System.out.println(jsonstr);


        JSONObject obj=JSONObject.parseObject(jsonstr);
        List<Integer>list=(List) obj.get("userBirthday");
        Calendar c= Calendar.getInstance();
        c.set(list.get(0)+1900,list.get(1),list.get(2));
        Date date=c.getTime();
        System.out.println(date);
        Calendar cc=Calendar.getInstance();
        cc.setTime(date);
        Time time=new Time(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH), cc.get(3));
        return time;
    }
}
class Time{
    private int year;
    private int month;
    private int day;

    public Time(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Time [day=" + day + ", month=" + month + ", year=" + year + "]";
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