# qdu-together-userdomain

这是老傅的小程序用户领域 *纯后端* **（附赠WEB调用Demo）**

---

## 后台框架

工具使用：Spring boot+Spring Framework+Mybatis+RabbitMQ  
依赖项配置：配置[application.properties](https://github.com/tiger5331819/qdu-together-userdomain/blob/master/src/main/resources/application.properties)  其余配置通过Java Annotation来实现

基本思想：DDD（*Domain-Driven-Design*）**[Eric Evans]**
> · 通过spring-mybatis工具来进行Spring与Mybatis整合  
· 通过spring-amqp工具来进行Spring与RabbitMQ整合  
· Respository是对Mybatis工具集的抽象与二次封装，并实现简单的LRU  
· RabbitMQ作为对外适配器，并由Core进行服务Route  
· Core通过AOP对服务请求进行响应  
· 领域对象为Entity，通过访问Entity根完成简单的领域服务  

架构风格：独立构件与调用返回  
架构选择：层次架构（**六边形架构**）与微内核架构  
层次调用顺序：业务逻辑层⇋领域服务层⇋领域实体  
微内核提供基础服务与系统管理  

![六边形架构 ](https://github.com/tiger5331819/qdu-together-userdomain/blob/master/六边形架构.png "六边形架构")  

### 后台框架详解  

![系统类图 ](https://github.com/tiger5331819/qdu-together-userdomain/blob/master/系统类图.png "系统类图")  

核心：在DomainCore中实现类扫描与AOP，通过注解的方式来进行流程管理。  

To be continue  

---

## 更新说明 *（我是真的懒，不管了就写这了）*

格式说明：

1. **Version** 为版本号
2. *Update* 为更新日期与当天更新版本号  
3. ***正文*** 为更新内容

### Vesion 0.4  

Update：2019.12.30  
version 1  
· 核心名字正式更名  
· 核心优化：  

1. 增加了类加载模块与核心AOP解析模块，现已经可以通过对框架注解标注的类进行解析并自动装载。  

2. 增加了可自定义AOP的模块与JDK代理模块，现已经可以通过使用模块来快速构建AOP方法增强。  

3. 增加了自定义注解的解析功能，现已经可以通过核心来获得自定义类注解标注的类的反射。  

4. 优化了框架方法的调用编程难度，现已经不需要继承接口，通过注解的方式来装载方法，并且支持自定义方法注解，前提是需要有注入框架中的对应AOP增强类，如下所示：  

#### 代码示例  

##### 通过注解装载方法  

```Java
@DomainService(ServiceName = "ServiceExample")
public class ServiceExample{

    @ServiceDo
    public void doService(Message message) {
        ..............
    }

    @myannotation
    public void dome(Message message){
        ..............
    }
}  
```

##### 自定义AOP  

如果需要添加到框架中则需要```@DomainAOP```与```@Type```进行标注，如下所示：  

```Java
@DomainAOP(DomainAOPName = "myaop")
@Type(TypeMessage = TypeMessage.Message)
public class myaop extends AOP {

    public myaop(..........) {
        super(myannotation.class);
    }

    ................
}
```

如果不需要添加到框架中则不需要注解进行标注，并建议使用JDKproxy模块：

```Java
public class myaop extends JDKproxy{
    ............
}
```

并通过此方法来创建JDK代理

```Java
public Object createProxy(Object aopMethod);
```

##### 自定义方法调用**(必须有自定义的AOP添加到框架中)**  

```Java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface myannotation{
}
```

##### 源码解析  

通过```@DomainAOP(DomainAOPName="AOPName")``` 标注的AOP来寻找到被自定义注解标注的方法  

```Java
for (Annotation annotation : method.getAnnotations()) {
    if (annotation.annotationType().getSimpleName().equals(classDo.getSimpleName())){
        ...............
        method.invoke(clazz.newInstance(), object);
        ...............
    }
```

核心AOP模块通过```@DomainAOP```的AOP类来对```@Type```标注的类型进行不同的消息传递处理。

```Java
for (Annotation annotation : clazz.getAnnotations()) {
    if (annotation.annotationType().getSimpleName().equals("Type")) {
        ..............
    }
```

PS： 所有的AOP模块都提供了默认的prepare与finsh这两个切面，如果需要多个切面或者别的增强方式，需要自己与prepare()与finsh()方法中进行定义，如果对AOP功能有着更多的需求，需要使用与spring整合的AspectJ框架。  

### Vesion 0.3

Update：2019.12.4
Version 1
· 在DomainCore中增加线程池ThreadPoolExecutor来进行多线程编程  
· 增加AMQPCore用于对消息队列的控制和结果的Route（用于上游）  
因此在监听函数中新的方法如下：  

``` Java
@RabbitListener(queues = "QueueName")
public void getMessage(Message message){
    AMQPCore.getInstance().setMessage(message);
}
```

主函数中有了对AMQP新的配置方法：  

```Java
public static void main(String[] args) throws Exception {
    SpringApplication.run(DemoApplication.class, args);
    AMQPCore.getInstance().Configuration("QueueName", "ClassPackageName");
}
```

因此就可以在Controller中通过结果队列来获得特定的结果：  

```Java
 Message result = AMQPCore.getInstance().getResult("ResultQueueName");
```

ResultQueueName从注解@ResultQueue中获得，注解使用方法：  

```Java
@RestController()
@ResultQueue("ResultQueueName")
```

### Vesion 0.2

Update：2019.12.2  
Version 1  
· 为核心增加包扫描功能并且增加@DomainService与@DomainRespository注解  
· DomainCore现在可以通过扫描注解来自动配置增加了@DomainRespository注解的Respository与自动路由增加了@DomainService注解的NetService  

```Java
@DomainService(ServiceName = "ServiceName")
public class ServiceExample implements NetService {

    @Override
    public void doService(Message message) {
    .........
    }
    
}
```

```Java
@DomainRespository(RespositoryName = "RespositoryName")
public class TypeRespository extends Respository<EntityIdentity,Entity> 
                             implements RespositoryAccess{
            ...............                             
}
```


· 同样的也可通过自定义编写接口UserNetService来继承NetService从而不再需要自动获取Core  
例如这样  

``` Java
public interface UserNetService extends NetService{
    public UserDomainCore core=UserDomainCore.getInstance();
}
```

非常建议使用依赖倒置的方法来访问Respository，即使用RespositoryAccess  

```Java
RespositoryAccess res= (RespositoryType) core.getRespository("RespositoryName");
```

当然也可以不使用RespositoryAccess来访问，毕竟Respository都需要继承RespositoryAccess接口  

```Java
UserRespository res= (RespositoryType) core.getRespository("RespositoryName");
```

· 还有一点忘了说明了，关于Core的启动方式也迎来了新的改变  

```Java
UserDomainCore.getInstance().run(SpringApplication.run(Application.class, args));
```

· 关于Message等的小改动这个直接看代码就知道了这里就不再做赘述。  
这次DomainCore增加了简单的IOC功能（*Core容器*），日后有需要的话可以做拓展。（*这感觉非常的cooooooooooooooool*）  

### Vesion 0.1

Update：2019.11.30  
Version 1  
· 新增启动DEMO以及部分优化  
· DEMO为单独的spirngMVC项目，用来展示Controller如何通过RabbitMQ来连接后端框架  
