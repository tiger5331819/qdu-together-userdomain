package qdu.together.togethercore.amqp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import qdu.together.net.Message;

/**
 * AMQP核心，整合了AMQPNet
 * 提供对于@ResultQueue注解生成对应的结果队列并对后端结果进行Route
 * @author=苏琥元
 * @version=0.3
 */
public class AMQPCore {

    private volatile static AMQPCore instance;

    private Map<String, LinkedBlockingQueue<Message>> ServiceResult;
    private LinkedBlockingQueue<Message> result = new LinkedBlockingQueue<>();

    public static AMQPCore getInstance() {
        if (instance == null) {
            synchronized (AMQPCore.class) {
                if (instance == null) {
                    instance = new AMQPCore();
                    return instance;
                }
            }
        }
        return instance;
    }

    private AMQPCore() {
    }

    public void Configuration(String queueName, String packageName) throws Exception {
        AMQPNet.AMQPConfiguration(queueName);
        ClassScanner scanner = new ClassScanner(packageName);
        scanner.getClass();
        ServiceResult = scanner.getServiceResult();
        run();
    }

    private void run() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Message message = result.take();
                        ServiceResult.get(message.ServiceRequest).add(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 放置监听获得的结果
     * @param message 
     */
    public void setMessage(Message message) {
        result.add(message);
    }

    /**
     * 通过@ResultQueue中的服务名称获得结果
     * @param serviceName
     * @return Message
     */
    public Message getResult(String serviceName) {
        try {
            return ServiceResult.get(serviceName).take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class ClassScanner {

    private String packageName;
    private Map<String, LinkedBlockingQueue<Message>> ServiceResult=new ConcurrentHashMap<>();
 
    public ClassScanner(String packageName) throws Exception {
       this.packageName = packageName;
       getClasses();
    }
 
 
    private Set<Class<?>> findAndAddClassesInPackageByFile(String packageName,
                                            String packagePath, Set<Class<?>> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
           return null;
        }
        File[] dirfiles = dir.listFiles();
        for (File file : dirfiles) {
           if (file.isDirectory()) {
              findAndAddClassesInPackageByFile(packageName + "."
                          + file.getName(), file.getAbsolutePath(),classes);
           } else {
              String className = file.getName().substring(0,
                    file.getName().length() - 6);
              try {
                 classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
              } catch (ClassNotFoundException e) {
                 e.printStackTrace();
              }
           }
        }
       return classes;
     }  
 
    private  Set<Class<?>> get(String packageName) throws Exception{ 
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
           dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
           while (dirs.hasMoreElements()) {
              URL url = dirs.nextElement();
              String protocol = url.getProtocol();
              if ("file".equals(protocol)) {
                 String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                 return findAndAddClassesInPackageByFile(packageName, filePath, new HashSet<>());   
              } 
           }
        } catch (IOException e) {
           e.printStackTrace();
        }
        return null;
     }
  
 
    private void getClasses() throws Exception{  
       Set<Class<?>> classList = get(packageName);
       if (!classList.isEmpty()) {
       for (Class<?> cls : classList) {
             if (cls.getAnnotation(ResultQueue.class) != null) {
                ServiceResult.put(cls.getAnnotation(ResultQueue.class).value(),new LinkedBlockingQueue<>());
             }
          } 
       }
    }

    public Map<String, LinkedBlockingQueue<Message>> getServiceResult() {
        return ServiceResult;
    }

    
      
}