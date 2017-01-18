package me.cache.intf;

/**
 * Created by wangyt on 2017/1/17.
 * : 缓存接口
 */
public interface ICache<K, V> {
    V put(K key, V value);

    V get(K key);

    V remove(K key);

    void clear();
}

