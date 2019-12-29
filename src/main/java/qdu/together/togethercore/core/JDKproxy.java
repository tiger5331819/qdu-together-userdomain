package qdu.together.togethercore.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 此为JDK动态代理模块。
 * 可以通过继承此模块来完成JDK的动态代理
 * @author 苏琥元
 * @version 泰建雅0.4
 */
public abstract class JDKproxy implements InvocationHandler{
    protected Object aopMethod;
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        prepare();
        Object obj = method.invoke(aopMethod, args);
        finish();
        return obj;
    }
    public Object createProxy(Object aopMethod) {
        this.aopMethod = aopMethod;
        return Proxy.newProxyInstance(aopMethod.getClass().getClassLoader(), aopMethod.getClass().getInterfaces(),
                this);
    }

    protected abstract void prepare();
    protected abstract void finish();
}