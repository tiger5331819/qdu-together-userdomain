package qdu.together.userdomin.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class UserDomainCore implements ApplicationContextAware {

    public ApplicationContext Context;
    private volatile static UserDomainCore instance;

    public static UserDomainCore getInstance() {
        if (instance == null) {
            synchronized (UserDomainCore.class) {
                if (instance == null) {
                    instance = new UserDomainCore();
                }
            }
        }
        return instance;
    }

    public UserDomainCore() {

    }

    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.Context = applicationContext;
    }

}