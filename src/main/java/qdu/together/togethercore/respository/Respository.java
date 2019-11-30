package qdu.together.togethercore.respository;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class Respository<K, V> extends Thread {
    private Map<K, V> Entity;
    private Map<K, Integer> EntityTTL;
    private Queue<V> AddEntity = new LinkedList<V>();
    private int Cachesize;
    private int Timeout;
    private int Timeout_Close=99999;

    protected Respository(Map<K, V> Entity, Map<K, Integer> EntityTTL, int Cachesize,int Timeout) {
        this.Entity = Entity;
        this.EntityTTL = EntityTTL;
        this.Cachesize = Cachesize;
        this.Timeout= Timeout < Timeout_Close ?Timeout:Timeout_Close;
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        try {
            while (true) {
                CompletableFuture<K> future = CompletableFuture.supplyAsync(() -> {
                    int MaxTTL = 0;
                    K MaxK = null;
                    for (Map.Entry<K, Integer> entry : EntityTTL.entrySet()) {
                        K k = entry.getKey();
                        int ttl = entry.getValue();

                        if (ttl == Timeout&&ttl!=Timeout_Close) {
                            V vv=Entity.get(k);
                            EntityTTL.remove(k);
                            Entity.remove(k);
                            saveEntity(vv);
                        } else {
                            ttl++;
                            EntityTTL.replace(k, ttl);
                        }                        
                        if (ttl > MaxTTL) {
                            MaxTTL = ttl;
                            MaxK = k;
                        }
                    }
                    return MaxK;
                });

                K Maxk = future.get();
                V v = AddEntity.poll();
                if (v != null) {
                    K kk = getEntityIdentity(v);
                    if(Cachesize>Entity.size()){
                        Entity.put(kk, v);
                        EntityTTL.put(kk, 0);
                        System.out.println("Entity Add success!");                        
                    }else{
                        V vv=Entity.get(Maxk);
                        EntityTTL.remove(Maxk);
                        Entity.remove(Maxk);
                        saveEntity(vv);
                        Entity.put(kk, v);
                        EntityTTL.put(kk, 0);
                        System.out.println("Entity Add success!"); 
                    }

                }
                Thread.sleep(50);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    protected abstract void saveEntity(V v);

    protected abstract K getEntityIdentity(V v);

    protected Boolean changeEntity(K k,V v){
        if(Entity.containsKey(k)){
            Entity.replace(k, v);
        }else{
            return false;
        }       
        return true;
    }

    protected Boolean addEntityToAddQueue(V v){
        if(AddEntity.offer(v)){
            return true;
        }else{
            return false;
        }
    }

    protected V get(K k){
        return Entity.get(k);
    }

    protected Boolean remove(K k){
        if(!Entity.containsKey(k)){
            return false;
        }else{
            V v=Entity.get(k);
            Entity.remove(k);
            EntityTTL.remove(k);
            saveEntity(v);
        }
        return true;
    }

    protected Boolean delete(K k){
        if(!Entity.containsKey(k)){
            return false;
        }else{
            Entity.remove(k);
            EntityTTL.remove(k);
        }
        return true;
    }
}