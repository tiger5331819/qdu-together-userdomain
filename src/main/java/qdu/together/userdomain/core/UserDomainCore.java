package qdu.together.userdomain.core;

import qdu.together.togethercore.AMQPNet;
import qdu.together.togethercore.DomainCore;
import qdu.together.userdomain.respository.UserRespository;

public class UserDomainCore extends DomainCore {

    
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
          setDomainName("UserDomain");
    }

    @Override
    public void Configuration(){
        UserRespository respository= UserRespository.getInstance();
        respository.UserRespositoryConfiguration();
        System.out.println(getDomainName());
        AMQPNet.AMQPConfiguration(getDomainName());
    }
}