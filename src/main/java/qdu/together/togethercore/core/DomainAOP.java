package qdu.together.togethercore.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 领域AOP注解，通过此注解注入框架中。
 * @author 苏琥元
 * @version 泰建雅0.4
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DomainAOP{
    String DomainAOPName();
}