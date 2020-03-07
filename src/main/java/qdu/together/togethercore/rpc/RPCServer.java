package qdu.together.togethercore.rpc;

import java.io.IOException;

/**
 * RPC函数端接口
 * @author 苏琥元
 */
public interface RPCServer {
    /**
     * 关闭服务端
     */
    public void stop();
 
    /**
     * 启用服务端
     * @throws IOException
     */
    public void start() throws IOException;
 
    /**
     * 服务注册
     * @param serviceName 服务名
     * @param impl 服务实现类
     */
    public void register(String serviceName, Class<?> impl);
 
    /**
     * 运行状态
     * @return
     */
    public boolean isRunning();
 
    /**
     * 获得端口
     */
    public int getPort();
}