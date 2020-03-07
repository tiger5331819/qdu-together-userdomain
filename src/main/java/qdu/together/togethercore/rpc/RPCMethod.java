package qdu.together.togethercore.rpc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC函数注解
 * 通过此注解标注RPC服务端调用的方法
 * @author 苏琥元
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RPCMethod{
    /**
     * 注册在RPC服务端的函数名称
     * @return
     */
    String MethodName();
}