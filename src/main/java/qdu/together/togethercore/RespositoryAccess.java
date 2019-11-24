package qdu.together.togethercore;
public interface RespositoryAccess{
    public Object get(Object obj);
    public void updateEntity(Object obj);
    public void deleteEntity(Object obj);
    public void putEntity(Object obj);
    public void removeEntity(Object obj);
}