package qdu.together.togethercore.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import qdu.together.net.Message;
import qdu.together.net.rabbitmq.MQproduce;
import qdu.together.togethercore.respository.RespositoryAccess;
import qdu.together.togethercore.service.NetService;

/**
 * 领域核心
 * @author 苏琥元
 * @version 0.3
 */
public abstract class DomainCore implements ApplicationContextAware, InvocationHandler {
    protected ApplicationContext Context;
    private ThreadPoolExecutor netServicePool;
    private String DomainName;
    private NetService netservice;
    private Queue<Message> sendMessageQueue = new LinkedList<Message>();
    private BlockingQueue<Message> receiveMessageQueue = new LinkedBlockingDeque<Message>();
    private Map<String, Map<String, Class<?>>> Classes = new ConcurrentHashMap<>();

    /**
     * 核心初始化
     * @param domainName 领域名称
     * @param packageName 完整包名
     */
    protected DomainCore(String domainName, String packageName) {
        this.DomainName = domainName;
        try {
            ClassScanner scanner = new ClassScanner(packageName);
            Classes.put("DomainService", scanner.getDomainServiceClass());
            Classes.put("DomainRespository", scanner.getDomainRespository());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(ApplicationContext applicationContext) {
        setApplicationContext(applicationContext);
        CoreConfiguration();
        netServicePool=new ThreadPoolExecutor(30, 100, 24L, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(50));
        CoreRun();
    }

    private void CoreConfiguration() {
        Map<String, Class<?>> Respository = Classes.get("DomainRespository");
        for (Entry<String, Class<?>> res : Respository.entrySet()) {
            Class<?> anno = res.getValue();
            Method[] methods = anno.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("getInstance")) {
                    try {
                        RespositoryAccess acc= (RespositoryAccess) method.invoke(anno);
                        acc.Configuration();
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Configuration();
    }

    public abstract void Configuration();

    protected void CoreRun() {
        new Thread() {
            public void run() {
                try {
                    Configuration();
                    System.out.println(DomainName + " Core is ready!");
                    Map<String, Class<?>> DomainService = Classes.get("DomainService");
                    while (true) {
                        Message message = (Message) receiveMessageQueue.take();
                        netServicePool.execute(
                            new Thread() {
                            public void run() {                            
                                try {
                                    NetService service = (NetService) createProxy(
                                            (NetService) DomainService.get(message.ServiceRequest).newInstance());
                                    service.doService(message);
                                } catch (InstantiationException | IllegalAccessException e) {
                                    e.printStackTrace();
                                }                              
                            }
                        });     
                        System.out.println(netServicePool.getPoolSize());                 
                    }   
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.Context = applicationContext;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)throws Throwable{
        prepare();
        Object obj=method.invoke(netservice, args);
        finish();
        return obj;
    }

    public ApplicationContext getContext(){
        return Context;
    }
    protected String getDomainName(){
        return DomainName;
    }    



    public void setNewreceiveMessage(Message message){
        receiveMessageQueue.add(message);
    }
    public void setsendMessage(Message message){
        sendMessageQueue.add(message);
    }

    public RespositoryAccess getRespository(String RespositoryName){
        Class<?>anno=Classes.get("DomainRespository").get(RespositoryName);
        Method[] methods = anno.getMethods();
        for(Method method :methods){
            if (method.getName().equals("getInstance")) {
                try {
                    return (RespositoryAccess) method.invoke(anno);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void prepare(){
        System.out.println("prepare");
    }
    public void finish(){
        Message msg=sendMessageQueue.poll();
        if(msg!=null){
            Message message=new Message();
            message.LocalQueueName=msg.DestinationQueueName;
            message.DestinationQueueName=msg.LocalQueueName;
            message.ServiceRequest=msg.ServiceRequestSource;
            message.ServiceRequestSource=msg.ServiceRequest;
            message.Data=JSONObject.toJSONString(msg.Data);
            message.isSuccessBoolean=msg.isSuccessBoolean;
            MQproduce.sendMessage(message);
            System.out.println("finish");
        }
    }

    public Object createProxy(NetService netservice){
        this.netservice=netservice;
        return Proxy.newProxyInstance(netservice.getClass().getClassLoader(),
                                     netservice.getClass().getInterfaces(), this);
    }

} 