package qdu.together.togethercore.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.annotation.*;

/**
 * 类加载器，通过核心提供的注解列表来将包中的注解类进行解析并注入
 * @author 苏琥元
 * @version 泰建雅0.4
 */
public class ClassScanner {

   private String packageName;
   private List<Class<? extends Annotation>> ClassSet;
   private Map<String, Map<String, Class<?>>> Classes = new ConcurrentHashMap<>();

   public ClassScanner(String packageName, List<Class<? extends Annotation>> classset) throws Exception {
      this.packageName = packageName;
      ClassSet = classset;
      for (int i = 0; i < ClassSet.size(); i++) {
         Classes.put(ClassSet.get(i).getSimpleName(), new Hashtable<String, Class<?>>());
      }
      getClasses();

   }

   private Set<Class<?>> findAndAddClassesInPackageByFile(String packageName, String packagePath,Set<Class<?>> classes) {
      File dir = new File(packagePath);
      if (!dir.exists() || !dir.isDirectory()) {
         return null;
      }
      File[] dirfiles = dir.listFiles();
      for (File file : dirfiles) {
         if (file.isDirectory()) {
            findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), classes);
         } else {
            String className = file.getName().substring(0, file.getName().length() - 6);
            try {
               classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
            } catch (ClassNotFoundException e) {
               e.printStackTrace();
            }
         }
      }
      return classes;
   } 

   private Set<Class<?>> get(String packageName) throws Exception {
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
            for (Class<? extends Annotation> clazz : ClassSet) {
               if (cls.getAnnotation(clazz) != null) {
                  int beginIndex = cls.getAnnotation(clazz).toString().indexOf("=")+1;
                  int size = cls.getAnnotation(clazz).toString().length() - 1;
                  Classes.get(clazz.getSimpleName())
                        .put(cls.getAnnotation(clazz).toString().substring(beginIndex, size), cls);
               }
            }
         } 
      }
   }
   public Map<String, Map<String, Class<?>>> Classes(){
       return Classes;
   }
   
}