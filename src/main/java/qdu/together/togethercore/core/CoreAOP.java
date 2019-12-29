package qdu.together.togethercore.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import qdu.together.net.Message;

/**
 * 框架中AOP核心模块，处理注入框架中的AOP增强方法
 * @author 苏琥元
 * @version 泰建雅0.4
 */
public class CoreAOP implements Runnable {

    private Map<String, Map<String, Class<?>>> Classes;
    private Queue<Message> sendMessageQueue;
    private BlockingQueue<Message> receiveMessageQueue;
    private ThreadPoolExecutor netServicePool;//, eventServicePool, dataObjectPool;

    @Override
    public void run() {
        Map<String, Class<?>> AOPService = Classes.get("DomainAOP");
        for (Entry<String, Class<?>> aopSet : AOPService.entrySet()) {
            Class<?> clazz = aopSet.getValue();
            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation.annotationType().getSimpleName().equals("Type")) {
                    int beginIndex = annotation.toString().indexOf("=") + 1;
                    int size = annotation.toString().length() - 1;
                    String typename = annotation.toString().substring(beginIndex, size);
                    switch (typename) {
                    case "Message":
                        new Thread() {
                            public void run() {
                                MessageRun(AOPService);
                            }
                        }.start();
                        break;
                    }
                }
            }
        }
    }

    public void MessageRun(Map<String, Class<?>> AOPService) {
        Map<String, Class<?>> DomainService = Classes.get("DomainService");
        while (true) {
            try {
                Message message = (Message) receiveMessageQueue.take();
                netServicePool.execute(new Thread() {
                    public void run() {
                        try {
                            AOP aop = (AOP) AOPService.get(message.Remark).getDeclaredConstructor(Queue.class).newInstance(sendMessageQueue);
                            aop.Run(DomainService.get(message.ServiceRequest),message);
                        } catch (InstantiationException | IllegalAccessException |InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                });
                System.out.println("ThreadPool size is: "+netServicePool.getPoolSize());
            } catch (InterruptedException  e1) {
                e1.printStackTrace();
            }
        }
    }

    public CoreAOP(Map<String, Map<String, Class<?>>> classes, Queue<Message> sendMessageQueue,
            BlockingQueue<Message> receiveMessageQueue) {
        Classes = classes;
        this.sendMessageQueue = sendMessageQueue;
        this.receiveMessageQueue = receiveMessageQueue;
        netServicePool = new ThreadPoolExecutor(30, 100, 24L, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(50));
        //eventServicePool=new ThreadPoolExecutor(10, 30, 24L, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(5));
        //dataObjectPool=new ThreadPoolExecutor(10, 30, 24L, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(5));
    }

}