package qdu.together.userdomin.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import qdu.together.userdomin.respository.UserRespository;

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
        UserRespository.getInstance();
        
    }
    public void Configuration(){
        UserRespository respository= UserRespository.getInstance();
        respository.UserRespositoryConfiguration();
    }

    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.Context = applicationContext;
    }

}