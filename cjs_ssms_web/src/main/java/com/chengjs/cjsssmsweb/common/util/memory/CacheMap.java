package com.chengjs.cjsssmsweb.common.util.memory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * CacheMap: 存储短暂对象的缓存类，实现Map接口，内部有一个定时器用来清除过期（DEFAULT_TIMEOUT秒）的对象
 *           为避免创建过多线程，没有特殊要求请使用getDefault()方法来获取本类的实例。
 *
 * issue1: 由于线程获取cpu的资源问题,清除时间太短,则不能够准确的在规定时间内将数据清楚 请务必注意;
 * issue2: 另外这个缓存能够承受多大的并行压力 也没有进行测试 还需要研究下
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
 * @version: 1.0.0, 2017-09-08 
 *
 * @param <K>
 * @param <V>
 *
 * ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED Shanghai Servyou Ltd 2017
 **/
public class CacheMap<K, V> extends AbstractMap<K, V> {
    private static final Log logger = LogFactory.getFactory().getLog(CacheMap.class);

  /**
   * 默认缓存超时清除时间 单位"ms毫秒"
   */
    private static final long DEFAULT_TIMEOUT = 180000;
    private static CacheMap<Object, Object> defaultInstance;

    public static synchronized final CacheMap<Object, Object> getDefault() {
        if (defaultInstance == null) {
            defaultInstance = new CacheMap<Object, Object>(DEFAULT_TIMEOUT);
        }
        return defaultInstance;
    }
    
    private class CacheEntry implements Entry<K, V> {
        long time;
        V value;
        K key;
        CacheEntry(K key, V value) {
            super();
            this.value = value;
            this.key = key;
            this.time = System.currentTimeMillis();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }
    }

    private class ClearThread extends Thread {
        ClearThread() {
            setName("clear cache thread");
        }

        public void run() {
            while (true) {
                try {
                    long now = System.currentTimeMillis();
                    Object[] keys = map.keySet().toArray();
                    for (Object key : keys) {
                        CacheEntry entry = map.get(key);
                        if (now - entry.time >= cacheTimeout) {
                            synchronized (map) {
                                map.remove(key);
                                if (logger.isDebugEnabled()) {
                                    logger.debug("清除对象 key "+key+"  时间:"+now);
                                }
                            }
                        }
                    }
                    Thread.sleep(cacheTimeout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long cacheTimeout;
    private Map<K, CacheEntry> map = new HashMap<K, CacheEntry>();

    public CacheMap(long timeout) {
        this.cacheTimeout = timeout;
        new ClearThread().start();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<Entry<K, V>>();
        Set<Entry<K, CacheEntry>> wrapEntrySet = map.entrySet();
        for (Entry<K, CacheEntry> entry : wrapEntrySet) {
            entrySet.add(entry.getValue());
        }
        return entrySet;
    }

    @Override
    public V get(Object key) {
        CacheEntry entry = map.get(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public V put(K key, V value) {
        CacheEntry entry = new CacheEntry(key, value);
        synchronized (map) {
            map.put(key, entry);
            if (logger.isDebugEnabled()) {
                logger.debug("缓存中放入对象 key: "+key+" 时间:"+ System.currentTimeMillis());
            }
        }
        return value;
    }

}