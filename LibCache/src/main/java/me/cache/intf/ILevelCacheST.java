package me.cache.intf;

import java.util.List;

/**
 * Created by wangyt on 2017/1/17.
 * : 多级缓存策略
 */
public interface ILevelCacheST<K, V> {
    V doPut(List<ICache<K, V>> caches, K key, V value);

    V doGet(List<ICache<K, V>> caches, K key);

    V doRemove(List<ICache<K, V>> caches, K key);

    void doClear(List<ICache<K, V>> caches);
}
