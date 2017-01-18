package me.cache.impl;

import android.support.v4.util.LruCache;

import me.cache.intf.ICache;

/**
 * Created by wangyt on 2017/1/17.
 * : 内存缓存
 */
public class MemCache<K, V> extends LruCache<K, V> implements ICache<K, V> {
    private static final int DEFAULT_MEM_SIZE = 1024 * 1024; // 1MB

    public MemCache(int maxSize) {
        super(maxSize);
    }

    public MemCache() {
        super(DEFAULT_MEM_SIZE);
    }

    @Override
    public void clear() {
        super.evictAll();
    }
}
