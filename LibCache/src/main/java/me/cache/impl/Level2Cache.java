package me.cache.impl;

import java.util.ArrayList;
import java.util.List;

import me.cache.intf.ICache;
import me.cache.intf.ILevelCacheST;

/**
 * Created by wangyt on 2017/1/18.
 * : 二级缓存
 */
public class Level2Cache<K, V> implements ICache<K, V> {
    private List<ICache<K, V>> caches = new ArrayList<>(2);
    private ILevelCacheST<K, V> cacheStrategy;

    public void setCache(ICache<K, V> cache, int level) {
        if (level < 1 || level > 2) {
            throw new IllegalArgumentException("level must be either 1 or 2");
        }
        caches.add(level - 1, cache);
    }

    public void setCacheStrategy(ILevelCacheST<K, V> cs) {
        cacheStrategy = cs;
    }

    private void checkStrategy() {
        if (null == cacheStrategy) {
            throw new IllegalStateException("no cache Strategy");
        }
    }

    @Override
    public V put(K key, V value) {
        checkStrategy();
        return cacheStrategy.doPut(caches, key, value);
    }

    @Override
    public V get(K key) {
        checkStrategy();
        return cacheStrategy.doGet(caches, key);
    }

    @Override
    public V remove(K key) {
        checkStrategy();
        return cacheStrategy.doRemove(caches, key);
    }

    @Override
    public void clear() {
        checkStrategy();
        cacheStrategy.doClear(caches);
    }

    public void clear(int level) {
        checkStrategy();
        caches.get(level - 1).clear();
    }

}
