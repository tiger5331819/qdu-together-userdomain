# qdu-together-userdomain

这是老傅的小程序用户领域 *纯后端* **（附赠WEB调用Demo）**

---

## 后台框架

工具使用：Spring boot+Spring Framework+Mybatis+RabbitMQ  
依赖项配置：配置[application.properties](https://github.com/tiger5331819/qdu-together-userdomain/blob/master/demo/src/main/resources/application.properties)  其余配置通过Java Annotation来实现

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

### 后台框架详解  

To be continue....

---

## 更新说明 *（我是真的懒，不管了就写这了）*

格式说明：

1. **Version** 为版本号
2. *Update* 为更新日期与当天更新版本号  
3. ***正文*** 为更新内容

### Vesion 0.2

Update：2019.12.2  
Version 1  
· 为核心增加包扫描功能并且增加@DomainService与@DomainRespository注解  
· DomainCore现在可以通过扫描注解来自动配置增加了@DomainRespo注解的Respository与自动路由增加了@DomainService注解的NetService  
· 同样的也可通过自定义编写接口UserNetService来继承NetService从而不再需要自动获取Core  
例如这样  

``` Java
public interface UserNetService extends NetService{
    public UserDomainCore core=UserDomainCore.getInstance();
}
```

非常建议使用依赖倒置的方法来访问Respository，即使用RespositoryAccess  

```Java
RespositoryAccess res= (UserRespository) core.getRespository("UserRespository");
```

当然也可以不使用RespositoryAccess来访问，毕竟Respository都需要继承RespositoryAccess接口  

```Java
UserRespository res= (UserRespository) core.getRespository("UserRespository");
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
