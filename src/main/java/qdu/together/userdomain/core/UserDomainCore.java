package qdu.together.userdomain.core;

import qdu.together.togethercore.amqp.AMQPNet;
import qdu.together.togethercore.core.DomainCore;

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
          super("UserDomain","qdu.together.userdomain");
    }

    @Override
    public void Configuration(){
        AMQPNet.AMQPConfiguration(getDomainName());
    }
}