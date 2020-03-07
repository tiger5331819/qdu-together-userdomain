package qdu.together.togethercore.rpc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class RPCService implements RPCServer {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final Map<String, Class<?>> serviceRegistry = new ConcurrentHashMap<String, Class<?>>();

    private static boolean isRunning = false;

    private int port;

    private static volatile RPCService instance=null;

    public static RPCService getInstance(int port) {
        if (instance == null) {
            synchronized (RPCService.class) {
                if (instance == null) {
                    instance = new RPCService(port);
                }
            }
        }
        return instance;
    }

    public RPCService(int port) {
        this.port = port;
    }

    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    public void start() {
        ServerSocket server = null;
        try {
            server=new ServerSocket(port);
            System.out.println("start server");
            isRunning=true;
            while (true) {
                executor.execute(new ServiceTask(server.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void register(String serviceName, Class<?> impl) {
        serviceRegistry.put(serviceName, impl);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getPort() {
        return port;
    }

    private static class ServiceTask implements Runnable {
        Socket client = null;
        

        public ServiceTask(Socket client) {
            this.client = client;
        }

        public void run() {     
               
            while (true) {
                try {
                    //获取json
                    if(client.getInputStream().available()==0)continue;
                    byte[] buffer=new byte[client.getInputStream().available()];
                    client.getInputStream().read(buffer);              
                    String mess=new String(buffer);

                    
                    JSONObject jsonIN = JSONObject.parseObject(mess);
                    if (jsonIN==null)
                        continue;
                    Class<?> serviceClass = serviceRegistry.get(jsonIN.getString("ServiceName"));//找对应的服务名称
                    if (serviceClass == null) {
                        throw new ClassNotFoundException(jsonIN.getString("ServiceName") + " not found");
                    }
                    /**
                     * 通过反射找寻注解
                     */
                    for (Method method : serviceClass.getMethods()) {
                        for (Annotation annotation : method.getAnnotations()) {

                            if (annotation.annotationType().getSimpleName().equals("RPCMethod")) {
                                String methodName = annotation.toString().substring(
                                        annotation.toString().toString().indexOf("=") + 1,
                                        annotation.toString().length() - 1);
                                if (methodName.equals(jsonIN.getString("FuncName")))
                                    try {
                                       
                                        JSONArray array = jsonIN.getJSONArray("Params"); 
                                        List<Object> objlist =  array.toJavaList(Object.class);
                                        Type[] types=method.getGenericParameterTypes();
                                        Object[] params=new Object[types.length];

                                        if(objlist.size()==1){
                                            JSONObject result=JSONObject.parseObject(array.getString(0));
                                            int flag=0;
                                            for(Parameter para : method.getParameters()){
                                                params[flag] = result.get(para.getName());
                                                flag++;
                                            }
                                        }else{
                                            int flag=0;
                                            for (Type type : types) {
                                                params[flag]=objlist.get(flag);
                                                flag++;
                                            }
                                        }
                                       
                                        //调用函数
                                        Object result = method.invoke(serviceClass.newInstance(),params);
                                        //发送结果
                                        String resultString = JSONObject.toJSONString(result);
                                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                                        bw.write(resultString+"\n");
                                        bw.flush();
                                        break;
                                    } catch (IllegalArgumentException | IllegalAccessException
                                            | InvocationTargetException | InstantiationException e) {
                                        e.printStackTrace();
                                    }
                                else {
                                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                                    bw.write("error");
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } 
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}