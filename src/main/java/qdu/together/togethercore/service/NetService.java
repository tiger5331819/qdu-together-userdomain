package qdu.together.togethercore.service;

import qdu.together.net.Message;

/**
 * 领域响应请求服务接口
 */
public interface NetService {
    public void doService(Message message);
}