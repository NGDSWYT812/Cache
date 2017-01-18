package me.cache;

import android.os.Environment;

import me.cache.impl.DiskCache;
import me.cache.impl.Level2Cache;
import me.cache.impl.Level2CacheStrategy;
import me.cache.impl.MemCache;
import me.cache.intf.ICache;

/**
 * Created by wangyt on 2017/1/18.
 * : 缓存建造者
 */
public class CacheBuilder<K, V> {

    public enum CacheType {
        MEM_CACHE, DISK_CACHE, LEVEL_CACHE
    }

    private CacheType type;
    private int memMaxSize = -1;//-1表示没有限制

    private int diskMaxSize = -1;//-1表示没有限制
    private long diskExpiredPeriod = -1;//-1表示永久保存
    private String diskPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 缓存类型
     *
     * @param t
     * @return
     */
    public CacheBuilder<K, V> type(CacheType t) {
        type = t;
        return this;
    }

    /**
     * 内存缓存最大值
     *
     * @param size
     * @return
     */
    public CacheBuilder<K, V> memMaxSize(int size) {
        memMaxSize = size;
        return this;
    }

    /**
     * 文件路径
     *
     * @param path
     * @return
     */
    public CacheBuilder<K, V> diskPath(String path) {
        diskPath = path;
        return this;
    }

    /**
     * 文件缓存最大值
     *
     * @param size
     * @return
     */
    public CacheBuilder<K, V> diskMaxSize(int size) {
        diskMaxSize = size;
        return this;
    }

    /**
     * 文件缓存过期时间
     *
     * @param time
     * @return
     */
    public CacheBuilder<K, V> diskExpiredPeriod(long time) {
        diskExpiredPeriod = time;
        return this;
    }

    public ICache<K, V> build() {
        if (type == null) {
            throw new IllegalStateException();
        }
        switch (type) {
            case MEM_CACHE:
                return createMemCache();
            case DISK_CACHE:
                return createFileCache();
            case LEVEL_CACHE:
                return createLevelCache();
            default:
                throw new UnsupportedOperationException();
        }
    }

    private MemCache<K, V> createMemCache() {
        return new MemCache<K, V>(memMaxSize);
    }

    private DiskCache<K, V> createFileCache() {
        return new DiskCache<K, V>(diskPath);
    }

    private Level2Cache<K, V> createLevelCache() {
        if (null == diskPath) {
            throw new IllegalStateException();
        }
        Level2Cache<K, V> cache = new Level2Cache<>();
        cache.setCache(new MemCache<K, V>(memMaxSize), 1);

        DiskCache<K, V> diskCache = new DiskCache<>(diskPath);
        diskCache.setExpireTime(diskExpiredPeriod);
        diskCache.setMaxSpace(diskMaxSize);
        cache.setCache(diskCache, 2);
        cache.setCacheStrategy(new Level2CacheStrategy<K, V>());

        return cache;
    }

}
