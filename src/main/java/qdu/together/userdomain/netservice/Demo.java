package qdu.together.userdomain.netservice;

import java.net.InetSocketAddress;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qdu.together.togethercore.rpc.RPCClient;
import qdu.together.togethercore.rpc.RPCService;

@RestController()
@RequestMapping("/Demo")
public class Demo {

    @GetMapping("/RPCDemo")
    public String Test() throws InterruptedException {
        new Thread(new Runnable(){     
            @Override
            public void run() {   
                //启动RPC服务器
                RPCService service = new RPCService(9010);
                //服务注册
                service.register("RPCTest", RPCTest.class);

                service.start();
            }
        }).start();
        //启动RPC客户端
        RPCClient Client=new RPCClient(new InetSocketAddress("localhost",9010));
        //选择调用的方式
        //Param2  obj= (Param2)Client.DotNetRemoteProxy("Test", new Param("苏琥元","100"), Param2.class);
        Param2  obj= (Param2)Client.JavaRemoteProxy("RPCTest", "Test", new Object[]{new Param("苏琥元","100")}, Param2.class);
        //Param2  obj=(Param2)Client.JavaRemoteProxy("RPCTest", "Test", new Object[]{"苏琥元","100"}, Param2.class);
        return obj.ss;

    }

}

