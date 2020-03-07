package qdu.together.togethercore.respository;
/**
 * 存储库访问接口
 */
public interface RepositoryAccess{
    public Object getEntity(Object obj);
    public Boolean updateEntity(Object obj);
    public Boolean deleteEntity(Object obj);
    public Boolean putEntity(Object obj);
    public Boolean removeEntity(Object obj);
    public void Configuration();
}