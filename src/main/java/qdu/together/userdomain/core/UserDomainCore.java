package qdu.together.userdomain.core;

import java.util.ArrayList;

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

    private UserDomainCore() {
          super("UserDomain","qdu.together",new ArrayList<>());
    }

    @Override
    public void Configuration(){
        AMQPNet.AMQPConfiguration(getDomainName());
    }
}