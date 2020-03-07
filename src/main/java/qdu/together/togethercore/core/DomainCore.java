package qdu.together.togethercore.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import qdu.together.net.Message;
import qdu.together.togethercore.respository.DomainRepository;
import qdu.together.togethercore.respository.RepositoryAccess;
import qdu.together.togethercore.service.DomainService;

/**
 * 领域核心
 * @author 苏琥元
 * @version 泰建雅0.4
 */
public abstract class DomainCore implements ApplicationContextAware {
    protected ApplicationContext Context;
    private String DomainName;
    private CoreAOP coreAOP;
    private List<Class<? extends Annotation>> ClassSet=new ArrayList<Class<? extends Annotation>>();
    
    private Queue<Message> sendMessageQueue = new LinkedList<Message>();
    private BlockingQueue<Message> receiveMessageQueue = new LinkedBlockingDeque<Message>();
    private Map<String, Map<String, Class<?>>> Classes = new ConcurrentHashMap<>();
    

    /**
     * 核心初始化
     * @param domainName 领域名称
     * @param packageName 完整包名
     */
    protected DomainCore(String domainName, String packageName,List<Class<? extends Annotation>>classset) {
        this.DomainName = domainName;
        try {
            ClassSet.add(DomainService.class);
            ClassSet.add(DomainRepository.class);
            ClassSet.add(DomainAOP.class);
            ClassSet.addAll(classset);
            ClassScanner scanner = new ClassScanner(packageName,ClassSet);
            Classes=scanner.Classes();
            coreAOP=new CoreAOP(Classes, sendMessageQueue, receiveMessageQueue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(ApplicationContext applicationContext) {
        setApplicationContext(applicationContext);
        CoreConfiguration();       
        CoreRun();
    }

    private void CoreConfiguration() {
        Map<String, Class<?>> Respository = Classes.get("DomainRepository");
        for (Entry<String, Class<?>> res : Respository.entrySet()) {
            Class<?> anno = res.getValue();
            Method[] methods = anno.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("getInstance")) {
                    try {
                        RepositoryAccess acc= (RepositoryAccess) method.invoke(anno);
                        acc.Configuration();
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Configuration();
        System.out.println(DomainName + " Core is ready!");
    }

    public abstract void Configuration();

    protected void CoreRun() {
        Runnable aoprun=coreAOP;
        Thread thread1=new Thread(aoprun,"CoreAOP");
        thread1.start();
    }
    
    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.Context = applicationContext;
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

    /**
     * 此方法不完善
     * @param RespositoryName
     * @return
     */
    public RepositoryAccess getRepository(String RepositoryName){
        Class<?>anno=Classes.get("DomainRespository").get(RepositoryName);
        Method[] methods = anno.getMethods();
        for(Method method :methods){
            if (method.getName().equals("getInstance")) {
                try {
                    return (RepositoryAccess) method.invoke(anno);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public Class<?> getClass(String annotationName,String className){
        return Classes.get(annotationName).get(className); 
    }

} 