package qdu.together.togethercore.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 框架AOP支持类，如果需要注入框架运行则必须继承此类。
 * 如果不需要注入框架运行则建议直接继承JDKproxy。
 * @see JDKproxy
 * @author 苏琥元
 * @version 泰建雅0.4
 */
public abstract class AOP extends JDKproxy {
    private Class<?> classDo;

    protected AOP(Class<?> classDo){
        this.classDo=classDo;
    }

    public void Run(Class<?> clazz, Object message) {
        for (Method method : clazz.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation.annotationType().getSimpleName().equals(classDo.getSimpleName())) {
                    prepare();
                    try {
                        method.invoke(clazz.newInstance(), message);
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
                            | InstantiationException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }
        }
    }
}