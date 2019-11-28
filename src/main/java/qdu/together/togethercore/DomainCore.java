package qdu.together.togethercore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import qdu.together.net.Message;
import qdu.together.net.rabbitmq.MQproduce;

public abstract class DomainCore implements ApplicationContextAware, InvocationHandler {
    protected ApplicationContext Context;
    private String DomainName;
    private NetService netservice;
    private Queue<Message> sendMessageQueue = new LinkedList<Message>();
    private BlockingQueue<Message> receiveMessageQueue = new LinkedBlockingDeque<Message>();

    public abstract void Configuration();


    protected void CoreRun() {   
        new Thread() {
            public void run() {
                try {
                    Configuration();
                    System.out.println(DomainName+" Core is ready!");
                    while(true){
                        Message message = (Message) receiveMessageQueue.take();
                        new Thread(){
                            public void run(){
                                NetService service=(NetService)createProxy((NetService)Context.getBean(message.ServiceRequest));
                                service.doService(message);
                            }
                        }.start();                        
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
        CoreRun();
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
    protected void setDomainName(String DomainName){
        this.DomainName=DomainName;
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
    public void prepare(){
        System.out.println("prepare");
    }
    public void finish(){
        Message message=sendMessageQueue.poll();
        if(message!=null){
            MQproduce.sendMessage(message);
            System.out.println("finish");
        }
    }

    public Object createProxy(NetService netservice){
        this.netservice=netservice;
        return Proxy.newProxyInstance(this.netservice.getClass().getClassLoader(),
                                     netservice.getClass().getInterfaces(), this);
    }

} 