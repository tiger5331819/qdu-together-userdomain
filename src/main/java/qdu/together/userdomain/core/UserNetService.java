package qdu.together.userdomain.core;

import qdu.together.togethercore.service.NetService;

/**
 * UserNetService接口继承与NetService接口
 * 增加了对于核心的引用
 */
public interface UserNetService extends NetService{
    public UserDomainCore core=UserDomainCore.getInstance();
}