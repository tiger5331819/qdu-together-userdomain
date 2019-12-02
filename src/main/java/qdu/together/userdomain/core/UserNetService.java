package qdu.together.userdomain.core;

import qdu.together.togethercore.service.NetService;

public interface UserNetService extends NetService{
    public UserDomainCore core=UserDomainCore.getInstance();
}