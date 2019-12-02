package qdu.together.togethercore.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import qdu.together.togethercore.respository.DomainRespository;
import qdu.together.togethercore.service.DomainService;

public class ClassScanner {

   private String packageName;
   private Map<String, Class<?>> DomainServiceclasses = new Hashtable<>();
   private Map<String, Class<?>> DomainRespositoryClasses = new Hashtable<>();

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
            if (cls.getAnnotation(DomainService.class) != null) {
               DomainServiceclasses.put(cls.getAnnotation(DomainService.class).ServiceName(),cls);
            }
            if(cls.getAnnotation(DomainRespository.class)!=null){
               DomainRespositoryClasses.put(cls.getAnnotation(DomainRespository.class).RespositoryName(), cls);
            } 
         } 
      }
   }

   public Map<String,Class<?>> getDomainServiceClass(){
       return DomainServiceclasses;
   }
   public Map<String,Class<?>> getDomainRespository(){
      return DomainRespositoryClasses;
   }


/*     public void getMapping(String packageName) throws Exception{
       
       for (Class<?> cls :getClasses(packageName) ) {
         System.out.println(cls.getName());
          interfacett t= (interfacett) cls.newInstance();
          t.print(); 
       }
    } */

   
}