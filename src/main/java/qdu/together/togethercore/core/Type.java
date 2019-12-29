package qdu.together.togethercore.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解用来标注注入框架中的自定义AOP消息接受类型
 * @author 苏琥元
 * @version 泰建雅0.4
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Type{
    TypeMessage TypeMessage(); 
}