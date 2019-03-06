package com.mmall.common;

import ch.qos.logback.classic.Logger;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCatch {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    //LRU算法
    private static LoadingCache<String,String> localche = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
        //默认的数据加载实现，当调用get取值的时候
        @Override
        public String load(String s) throws Exception {
            return null;
        }
    })


}
