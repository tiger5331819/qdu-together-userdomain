package qdu.together.togethercore;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class Respository<K, V> extends Thread {
    private Map<K, V> Entity;
    private Map<K, Integer> EntityTTL;
    private Queue<V> AddEntity = new LinkedList<V>();

    protected Respository(Map<K, V> Entity, Map<K, Integer> EntityTTL) {
        this.Entity = Entity;
        this.EntityTTL = EntityTTL;
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        try {
            while (true) {
                CompletableFuture<?> future = CompletableFuture.runAsync(() -> {
                    for (Map.Entry<K, Integer> entry : EntityTTL.entrySet()) {
                        K k = entry.getKey();
                        int ttl = entry.getValue();
                        if (ttl == 10) {
                            EntityTTL.remove(k);
                            Entity.remove(k);
                        } else {
                            ttl++;
                            EntityTTL.replace(k, ttl);
                        }
                    }
                });
                future.join();
                V v = AddEntity.poll();
                if (v!=null) {         
                    K kk = GetEntityIdentity(v);
                    Entity.put(kk, v);
                    EntityTTL.put(kk, 0);
                    System.out.println("Entity Add success!");
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract K GetEntityIdentity(V v);

    public Boolean ChangeEntity(K k,V v){
        if(Entity.containsKey(k)){
            Entity.replace(k, v);
        }else{
            return false;
        }       
        return true;
    }

    public Boolean AddEntityToAddQueue(V v){
        if(AddEntity.offer(v)){
            return true;
        }else{
            return false;
        }
    }

    public V getEntity(K k){
        return Entity.get(k);
    }

    public Boolean DeleteEntity(K k){
        if(!Entity.containsKey(k)){
            return false;
        }else{
            Entity.remove(k);
            EntityTTL.remove(k);
        }
        return true;
    }

}