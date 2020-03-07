package qdu.together.togethercore.rpc;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;


import com.alibaba.fastjson.JSONObject;

/**
 * RPC客户端
 * @author 苏琥元

 */
public class RPCClient {

    Socket socket = null;
    InputStream is;
    OutputStream os;

    /**
     * 调用Java函数
     * @param serviceName 服务名称
     * @param methodName 函数名称
     * @param arguments 参数列表
     * @param clazz 返回结果类型
     * @return
     */
    public Object JavaRemoteProxy(String serviceName, String methodName, Object[] arguments,Class<?> clazz)  {
        try {
             
            RPCData data = new RPCData(serviceName,methodName,arguments);
            String jsonString=JSONObject.toJSONString(data);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            
            bw.write(jsonString+"\n");
            bw.flush();

            while(true){
                if(is.available()==0)continue;
                byte[] buffer=new byte[is.available()];

                is.read(buffer);              
                String mess=new String(buffer);
                return JSONObject.parseObject(mess,clazz);
            } 

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 调用.NET Core函数
     * @param methodName 函数名称
     * @param arguments 传入参数
     * @param clazz 返回类型
     * @return
     */
    public Object DotNetRemoteProxy(String methodName, Object arguments,Class<?> clazz)  {
        try {
             
            RPCData data = new RPCData(null,methodName,arguments);
            String jsonString=JSONObject.toJSONString(data);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            
            bw.write(jsonString+"\n");
            bw.flush();

            while(true){
                if(is.available()==0)continue;
                byte[] buffer=new byte[is.available()];

                is.read(buffer);              
                String mess=new String(buffer);

                return JSONObject.parseObject(mess, clazz);
            } 

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public RPCClient(final InetSocketAddress addr) {
        socket = new Socket();
        try {
            socket.connect(addr);
            is= socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class RPCData {
    public String ServiceName;
    public String FuncName;
    public Object Params;

    public RPCData(String ServiceName, String FuncName, Object Params) {
        this.ServiceName = ServiceName;
        this.FuncName = FuncName;
        this.Params = Params;
    }    
}