package me.cache.impl;

import java.util.List;

import me.cache.intf.ICache;
import me.cache.intf.ILevelCacheST;

/**
 * Created by wangyt on 2017/1/18.
 * : 二级缓存控制
 */
public class Level2CacheStrategy<K, V> implements ILevelCacheST<K, V> {
    private static final int TAG_FIRST_CACHE = 0;
    private static final int TAG_SECOND_CACHE = 1;

    @Override
    public V doPut(List<ICache<K, V>> caches, K key, V value) {
        V result = null;
        if (null != value) {
            result = caches.get(TAG_SECOND_CACHE).put(key, value);
        }
        return result;
    }

    @Override
    public V doGet(List<ICache<K, V>> caches, K key) {
        V value = caches.get(TAG_FIRST_CACHE).get(key);
        if (null != value) {
            return value;
        } else {
            value = caches.get(TAG_SECOND_CACHE).get(key);
        }

        if (null != value) {
            caches.get(TAG_FIRST_CACHE).put(key, value);
            return value;
        } else {
            return null;
        }
    }

    @Override
    public V doRemove(List<ICache<K, V>> caches, K key) {
        V result = null;
        for (ICache<K, V> iCache : caches) {
            V v = iCache.remove(key);
            if (null == result) {
                result = v;
            }
        }
        return result;
    }

    @Override
    public void doClear(List<ICache<K, V>> caches) {
        for (ICache<K, V> iCache : caches) {
            iCache.clear();
        }
    }

}
