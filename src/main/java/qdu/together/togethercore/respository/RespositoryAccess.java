package qdu.together.togethercore.respository;
public interface RespositoryAccess{
    public Object getEntity(Object obj);
    public void updateEntity(Object obj);
    public void deleteEntity(Object obj);
    public void putEntity(Object obj);
    public void removeEntity(Object obj);
    public void Configuration();
}