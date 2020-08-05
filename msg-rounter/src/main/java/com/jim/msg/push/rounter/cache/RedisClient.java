package com.jim.msg.push.rounter.cache;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jim.msg.push.commons.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 14:33
 */
@Component
@Slf4j
public class RedisClient {
    private static  final Logger LOG = LoggerFactory.getLogger(RedisClient.class);
    public static JedisPool pool;
    private static Gson gson = new Gson();
    public static String REDIS_SERVER_IP ;
    private static int REDIS_PORT = 6379;
    public static String REDIS_PASSWORD ;
    public static boolean isInit;
    private static int DATA_BASE ;
    // 最大空闲连接数
    private static final int MAX_IDLE_COUNT = 20;
    // 最大连接数
    private static final int MAX_TOTAL_COUNT = 80;
    // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
    private static final int MAX_WAIT_MILLIS = 5000;
    // 默认数据过期时间
    public static final int DEFAULT_EXPIRE_SECONDS = 3 * 24 * 60 * 60;


    /**
     * redis初始化<br/>
     * server=xx<br/>
     * port=xx<br/>
     * password=xx<br/>
     * maxIdleCount=xx<br/>
     * maxTotalCount=xx<br/>
     * maxWaitMillis=xx<br/>
     *
     * @param properties
     */
    public static void init(Properties properties) {
        int maxIdleCount = MAX_IDLE_COUNT;
        int maxTotalCount = MAX_TOTAL_COUNT;
        int maxWaitMillis = MAX_WAIT_MILLIS;

        if (properties.containsKey("maxIdleCount")) {
            maxIdleCount = Integer.valueOf(properties.getProperty("maxIdleCount"));
        }
        if (properties.containsKey("maxTotalCount")) {
            maxTotalCount = Integer.valueOf(properties.getProperty("maxTotalCount"));
        }
        if (properties.containsKey("maxWaitMillis")) {
            maxWaitMillis = Integer.valueOf(properties.getProperty("maxWaitMillis"));
        }
        init(REDIS_SERVER_IP, REDIS_PORT, REDIS_PASSWORD,DATA_BASE, maxIdleCount, maxTotalCount, maxWaitMillis);
    }

    /**
     * redis初始化
     * @param ip
     * @param port
     * @param password
     */
    public static void init(String ip, int port, String password,int database) {
        init(ip, port, password, database,MAX_IDLE_COUNT, MAX_TOTAL_COUNT, MAX_WAIT_MILLIS);
    }

    /**
     * redis初始化
     * @param ip
     * @param port
     * @param password
     * @param maxIdleCount
     * @param maxTotalCount
     * @param maxWaitMillis
     */
    public static void init(String ip, int port, String password, int database,int maxIdleCount, int maxTotalCount, int maxWaitMillis) {
        if (isInit) {
            return;
        }
        isInit = true;
        LOG.info("redis ip:{}, port:{}, password:{}, database:{},maxIdleCount:{}, maxTotalCount:{}, maxWaitMillis:{}",
                ip, port, password,database, maxIdleCount, maxTotalCount, maxWaitMillis);
        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大空闲连接数
        config.setMaxIdle(maxIdleCount);
        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWaitMillis(maxWaitMillis);
        // 最大连接数, 默认8个
        config.setMaxTotal(maxTotalCount);
        // 创建连接池
        if (StringUtils.isEmpty(password)) {
            pool = new JedisPool(config, ip, port,5000);
        } else {
            pool = new JedisPool(config, ip, port, 5000, password,database);
        }
        int a =0;
    }

    /**
     * 是否存在指定的key
     *
     * @param key
     * @return
     */
    public static boolean existsKey(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.exists(key);
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        } finally {
            closeResource(jedis);
        }
    }
    /**
     * 是否存在指定的key
     *
     * @param key
     * @return
     */
    public static boolean existsKey(byte[] key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.exists(key);
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * key表达式,如：brand_*
     *
     * @param keyExpression
     * @return
     */
    public static Set<String> getKeys(String keyExpression) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.keys(keyExpression);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }
    /**
     * key表达式,如：brand_*
     *
     * @param keyExpression
     * @return
     */
    public static Set<byte[]> getKeys(byte[] keyExpression) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.keys(keyExpression);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 移除指定的key
     *
     * @param key
     */
    public static void delKey(String key) {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(key);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }
    /**
     * 移除指定的key
     *
     * @param key
     */
    public static void delKey(byte[] key) {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(key);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 获取标记key<br/>
     * flagkey = key + "_flag"
     *
     * @param key
     * @return
     */
    public static String getFlagKey(String key) {
        return key + "_flag";
    }

    /**
     * 移除标记key<br/>
     * flagkey = key + "_flag"
     *
     * @param key
     */
    public static void delFlagKey(String key) {
        delKey(getFlagKey(key));
    }

    /**
     * 事物移除指定的key及flag key（原子性）
     *
     * @param key-原始key
     */
    public static void delKeyAndFlagKey(String key) {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(getFlagKey(key));
            jedis.del(key);
//			Transaction transaction = jedis.multi();
//			transaction.del(key, getFlagKey(key));
//			transaction.exec();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 设置指定key的过期时间为默认过期时间（3天）
     *
     * @param key
     */
    public static void setKeyDefaultExpireTime(String key) {
        setKeyExpireTime(key, DEFAULT_EXPIRE_SECONDS);
    }

    /**
     * 单独设置指定key的过期时间
     *
     * @param key-key
     * @param expireSeconds--过期时间(秒)
     */
    public static void setKeyExpireTime(String key, int expireSeconds) {
        if (expireSeconds <= 0) {
            return;
        }
        Jedis jedis = pool.getResource();
        try {
            jedis.expire(key, expireSeconds);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }
    /**
     * 单独设置指定key的过期时间
     *
     * @param key-key
     * @param expireSeconds--过期时间(秒)
     */
    public static void setKeyExpireTime(byte[] key, int expireSeconds) {
        if (expireSeconds <= 0) {
            return;
        }
        Jedis jedis = pool.getResource();
        try {
            jedis.expire(key, expireSeconds);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 单独设置指定key的过期时间
     *
     * @param key-key
     * @param expireTime-过期时间，类型：yyyy-MM-dd HH:mm:ss
     */
    public static void setKeyExpireTime(String key, String expireTime) {
        long toTime = DateUtil.getTime(expireTime, DateUtil.PATTERN_DATETIME);
        int expireSeconds = (int) (toTime - System.currentTimeMillis()) / 1000;
        setKeyExpireTime(key, expireSeconds);
    }

    /**
     * 查询缓存失效时间
     *
     * @param key
     * @return -2:key不存在,
     * -1：存在但没有设置剩余生存时间时,
     * 大于0：以秒为单位，返回 key 的剩余生存时间
     * -3:redis报错
     * @author Guoden 2016年12月14日
     */
    public static long getKeyExpireTime(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.ttl(key);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return -3;
    }

    /**
     * 获取自增id
     *
     * @param key-key
     * @return
     */
    public static int getAutoIncreaseId(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.incr(key).intValue();
        } finally {
            closeResource(jedis);
        }
    }

    // ===============================key-value======================

    /**
     * 从缓存取得字符串
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        Jedis jedis = pool.getResource();
        try {
            String s = jedis.get(key);
            return s == null ? "" : s;
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        } finally {
            closeResource(jedis);
        }
    }
    /**
     * 从缓存取得byte[]
     *
     * @param key
     * @return
     */
    public static byte[] getValue(byte[] key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.get(key);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 从缓存取得对象
     *
     * @param key
     * @param c
     * @return
     */
    public static <T> T getValue(String key, Class<T> c) {
        String s = getValue(key);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
//        return gson.fromJson(s, c);
        return JSON.parseObject(s, c);
    }

    /**
     * 从缓存取得对象
     *
     * @param keys
     * @param c
     * @return
     */
    public static <T> List<T> getValues(String[] keys, Class<T> c) {
        Jedis jedis = pool.getResource();
        List<T> list = new ArrayList<T>();
        try {
            List<String> results = jedis.mget(keys);
            for (String s : results) {
                if (!StringUtils.isEmpty(s)) {
                    list.add(gson.fromJson(s, c));
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return list;
    }

    /**
     * 保存字符串(如果对象已经存在，则覆盖)
     *
     * @param key
     * @param value
     */
    public static void setValue(String key, String value) {
        setValue(key, value, 0);
    }

    /**
     * 保存byte[](如果对象已经存在，则覆盖)
     *
     * @param key
     * @param value
     */
    public static void setValue(byte[] key, byte[] value) {
        setValue(key, value, 0);
    }
    /**
     * 保存字符串(如果对象已经存在，则覆盖)
     *
     * @param key-key
     * @param value-value
     * @param expireSeconds-过期时间(秒)
     */
    public static void setValue(String key, String value, int expireSeconds) {
        setValue(key, value, expireSeconds, false);
    }
    /**
     * 保存byte[](如果对象已经存在，则覆盖)
     *
     * @param key-key
     * @param value-value
     * @param expireSeconds-过期时间(秒)
     */
    public static void setValue(byte[] key, byte[] value, int expireSeconds) {
        Jedis jedis = pool.getResource();
        try{
            String expx = "EX";
            if(0==expireSeconds){
                expx = "PX";
            }
            if(jedis.exists(key)){
                jedis.set(key,value,"XX".getBytes(),expx.getBytes(),expireSeconds);
            }else{
                jedis.set(key,value,"NX".getBytes(),expx.getBytes(),expireSeconds);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 保存byte[](如果对象已经存在，则覆盖)
     *
     * @param key-key
     * @param value-value
     * @param expireSeconds-过期时间(秒)
     */
    public static void setValueUsePipeline(byte[] key, byte[] value, int expireSeconds) {
        Jedis jedis = pool.getResource();
        try{
            Pipeline pipeline= jedis.pipelined();
            String expx = "EX";
            if(0==expireSeconds){
                expx = "PX";
            }
            if(jedis.exists(key)){
                pipeline.set(key,value,"XX".getBytes(),expx.getBytes(),expireSeconds);
            }else{
                pipeline.set(key,value,"NX".getBytes(),expx.getBytes(),expireSeconds);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 保存字符串(如果对象已经存在，则覆盖)
     *
     * @param key-key
     * @param value-value
     * @param expireSeconds-过期时间(秒)
     * @param needSetFlag-是否设置其flag，其过期时间为expireSeconds
     */
    public static void setValue(String key, String value, int expireSeconds, boolean needSetFlag) {
        Jedis jedis = pool.getResource();
        try {
            String flagKey = getFlagKey(key);
            jedis.set(key, value);
            if (needSetFlag) {
                jedis.set(flagKey, gson.toJson(true));
            }
            if (expireSeconds > 0) {
                if (needSetFlag) {
                    jedis.expire(flagKey, expireSeconds);
                }
                jedis.expire(key, expireSeconds);
            } else {// 不设置过期时间则需设置回原有的过期时间
                // 获取key过期时间，因为set后原有的key过期时间将被清空
                long expireTime = jedis.pttl(key);//剩余过期毫秒数
                if (expireTime > 0) {
                    int time = (int) Math.ceil((float) expireTime / 1000);
                    if (needSetFlag) {
                        jedis.expire(flagKey, time);
                    }
                    jedis.expire(key, time);// 再设置原有剩余秒数 向上取整，如2.1 则为3
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 保存字符串(如果对象已经存在，则覆盖)
     *
     * @param key-key
     * @param value-value
     * @param expireTime-过期时间，类型：yyyy-MM-dd HH:mm:ss
     */
    public static void setValue(String key, String value, String expireTime) {
        long toTime = DateUtil.getTime(expireTime, DateUtil.PATTERN_DATETIME);
        int expireSeconds = (int) (toTime - System.currentTimeMillis()) / 1000;
        if (expireSeconds > 0) {
            setValue(key, value, expireSeconds);
        }
    }

    /**
     * 保存单个对象(如果对象已经存在，则覆盖)
     *
     * @param key
     * @param value
     */
    public static <T> void setValue(String key, T value) {
        setValue(key, value, 0);
    }

    /**
     * 保存单个对象(如果对象已经存在，则覆盖)
     *
     * @param key
     * @param value
     * @param expireSeconds-过期时间(秒)
     */
    public static <T> void setValue(String key, T value, int expireSeconds) {
        if (value != null) {
            setValue(key, gson.toJson(value), expireSeconds);
        }
    }

    /**
     * 保存单个对象(如果对象已经存在，则覆盖)-包括flag
     *
     * @param key
     * @param value
     * @param expireSeconds-value和flag的过期时间(秒)
     * @param needSetFlag-是否设置flag，其过期时间为expireSeconds
     */
    public static <T> void setValue(String key, T value, int expireSeconds, boolean needSetFlag) {
        if (value != null) {
            setValue(key, gson.toJson(value), expireSeconds, needSetFlag);
        }
    }

    /**
     * 保存字符串(如果对象已经存在，则覆盖)
     *
     * @param key-key
     * @param value-value
     * @param expireTime-过期时间，类型：yyyy-MM-dd HH:mm:ss
     */
    public static <T> void setValue(String key, T value, String expireTime) {
        if (value != null) {
            setValue(key, gson.toJson(value), expireTime);
        }
    }

    // ===============================sexnx==================================

    /**
     * 保存对象（如果不存在key的话）
     *
     * @param key
     * @param value
     * @return 不存在key，则保存成功，返回true，存在key，则保存失败，返回false
     */
    public static <T> boolean setnx(String key, T value) {
        return setnx(key, gson.toJson(value));
    }

    /**
     * 保存对象（如果不存在key的话）
     *
     * @param key
     * @param value
     * @return 不存在key，则保存成功，返回true，存在key，则保存失败，返回false
     */
    public static <T> boolean setnx(String key, T value, int expireSeconds) {
        return setnx(key, gson.toJson(value), expireSeconds);
    }

    /**
     * 保存字符串（如果不存在key的话）
     *
     * @param key
     * @param value
     * @return 不存在key，则保存成功，返回true，存在key，则保存失败，返回false
     */
    public static boolean setnx(String key, String value) {

        return setnx(key, value, 0);
    }

    /**
     * 保存字符串（如果不存在key的话）
     *
     * @param key
     * @param value
     * @return 不存在key，则保存成功，返回true，存在key，则保存失败，返回false
     */
    public static boolean setnx(String key, String value, int expireSeconds) {

        Jedis jedis = pool.getResource();
        try {
            // 1 if the key was set 0 if the key was not set
            boolean setnxOK = jedis.setnx(key, value) == 1;

            // 设置过期时间
            if (expireSeconds > 0) {
                if (setnxOK) { // 设置成功了，则设置失效时间
                    jedis.expire(key, expireSeconds);
                } else if (!setnxOK && jedis.pttl(key) < 0) {// 或者由于某些异常状态setnx执行成功 但expire没有成功，可能会导致锁永远释放不掉，这里强制设置过期时间
                    jedis.expire(key, expireSeconds);
                }
            }

            if (setnxOK) {
                return true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }

        return false;

    }

    private static int k = 0;

    // ===============================key-list======================

    /**
     * 添加一个list列表到原有列表尾部
     *
     * @param key
     * @param list
     */
    public static <T> void addList(String key, List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        Jedis jedis = pool.getResource();
        try {
            String[] ss = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                ss[i] = gson.toJson(list.get(i));
            }
            jedis.rpush(key, ss);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 添加一个value到原有列表到尾部
     *
     * @param key
     * @param value
     */
    public static <T> void addList(String key, T value) {
        Jedis jedis = pool.getResource();
        try {
            String[] ss = new String[1];
            ss[0] = gson.toJson(value);
            jedis.rpush(key, ss);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }


    /**
     * 添加一个value到原有列表到尾部
     *
     * @param key
     * @param value
     */
    public static <T> void addList(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            jedis.rpush(key, value);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 添加一个list列表到原有列表头部
     *
     * @param key
     * @param list
     */
    public static <T> void addListToHead(String key, List<T> list) {

        if (list == null || list.isEmpty()) {
            System.out.println("list is null");
            return;
        }

        Jedis jedis = pool.getResource();
        try {
            String[] ss = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                ss[i] = gson.toJson(list.get(i));
            }
            jedis.lpush(key, ss);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 添加一个value到原有列表头部
     *
     * @param key
     * @param value
     */
    public static <T> void addListToHead(String key, T value) {
        Jedis jedis = pool.getResource();
        try {
            String[] ss = new String[1];
            ss[0] = gson.toJson(value);
            jedis.lpush(key, gson.toJson(value));
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 添加一个value到原有列表头部
     *
     * @param key
     * @param value
     */
    public static <T> void addListToHead(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            jedis.lpush(key, value);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 设置list中指定索引的值
     *
     * @param key
     * @param index-索引
     * @param value
     */
    public static <T> void setListElement(String key, int index, T value) {
        Jedis jedis = pool.getResource();
        try {
            jedis.lset(key, index, gson.toJson(value));
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 返回某个范围内的集合，无结果集则返回空list
     *
     * @param key
     * @param start-起始索引（包含，从0开始）
     * @param end-结束索引（包含）
     * @return
     */
    public static List<String> getListRange(String key, int start, int end) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

  /*  *//**
     * 返回某个范围内的集合，无结果集则返回空list
     *
     * @param key
     * @param start-起始索引（包含，从0开始）
     * @param end-结束索引（包含）
     * @param c-具体类
     * @return
     *//*
    public static <T> List<T> getListRange(String key, int start, int end, Class<T> c) {
        List<String> slist = getListRange(key, start, end);
        List<T> list = new ArrayList<T>();
        for (String s : slist) {
            list.add(gson.fromJson(s, c));
        }
        return list;
    }*/

   /* *//**
     * 分页获取list
     *
     * @param key
     * @param pageNow-当前页数
     * @param pageSize-每页记录数
     * @param c
     * @return
     *//*
    public static <T> List<T> getListPage(String key, int pageNow, int pageSize, Class<T> c) {
        int startIndex = (pageNow - 1) * pageSize;
        int endIndex = pageNow * pageSize - 1;
        return getListRange(key, startIndex, endIndex, c);
    }*/

   /* *//**
     * 获取指定key下的列表所有记录
     *
     * @param key
     * @param c
     * @return
     *//*
    public static <T> List<T> getAllList(String key, Class<T> c) {
        return getListRange(key, 0, -1, c);
    }*/

    /**
     * 获取列表第一个元素
     *
     * @param key
     * @param c
     * @return
     */
    public static <T> T getListFirstElement(String key, Class<T> c) {
        Jedis jedis = pool.getResource();
        try {
            return gson.fromJson(jedis.lindex(key, 0), c);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 返回并删除list中的首元素
     *
     * @param key
     * @return
     */
    public static <T> T getListPop(String key, Class<T> c) {
        Jedis jedis = pool.getResource();
        try {
            return gson.fromJson(jedis.lpop(key), c);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 返回并删除list中的首元素
     *
     * @param key
     * @return
     */
    public static <T> String getListPop(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.lpop(key);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 获取列表最后一个元素
     *
     * @param key
     * @param c
     * @return
     */
    public static <T> T getListLastElement(String key, Class<T> c) {
        Jedis jedis = pool.getResource();
        try {
            return gson.fromJson(jedis.lindex(key, -1), c);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 返回list的集合个数
     *
     * @param key
     * @return
     */
    public static int getListSize(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.llen(key).intValue();
        } catch (Exception e) {

            e.printStackTrace();
            return 0;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 删除list的指定对象
     *
     * @param key
     * @param values
     */
    @SuppressWarnings("unchecked")
    public static <T> void removeValueFromList(String key, String... values) {
        Jedis jedis = pool.getResource();
        try {
            for (String v : values) {
                jedis.lrem(key, 0, v);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }
    /**
     * 删除一个列表
     *
     * @param key
     */
    public static void removeList(String key) {
        Jedis jedis = pool.getResource();
        try {
            if (jedis.llen(key) > 0) {
                jedis.ltrim(key, 1, 0);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 保存Java map to Redis map
     *
     * @param hashKey
     * @param map
     */
    public static <T> void addToHashMap(String hashKey, Map<String, T> map) {
        if (map == null || map.isEmpty()) {
            System.out.print("map is null");
            return;
        }
        Jedis jedis = pool.getResource();
        try {
            for (String key : map.keySet()) {
                String value = gson.toJson(map.get(key));
                jedis.hset(hashKey, key, value);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 保存Java map to Redis map
     *
     * @param hashKey
     * @param map
     */
    public static <K, V> void addToHashMap2(String hashKey, Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            System.out.print("map is null");
            return;
        }
        Jedis jedis = pool.getResource();
        try {
            for (K key : map.keySet()) {
                String value = gson.toJson(map.get(key));
                jedis.hset(hashKey, String.valueOf(key), value);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 保存Java value to Redis map
     *
     * @param hashKey
     * @param key
     * @param value
     */
    public static <T> void addToHashMap(String hashKey, String key, T value) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hset(hashKey, key, gson.toJson(value));
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 从hashmap中返回某个key的值
     *
     * @param hashKey
     * @param key
     * @param c-类型
     * @return
     */
    public static <T> T getValueFromHashMap(String hashKey, String key, Class<T> c) {
        Jedis jedis = pool.getResource();
        try {
            String s = jedis.hget(hashKey, key);
            if (StringUtils.isEmpty(s)) {
                return null;
            }
            return gson.fromJson(s, c);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 从hashmap中返回某个key的值
     *
     * @param hashKey
     * @param key
     * @param typeOfT 类型
     * @return
     */
    public static <T> T getValueFromHashMap(String hashKey, String key, Type typeOfT) {
        Jedis jedis = pool.getResource();
        try {
            String s = jedis.hget(hashKey, key);
            if (StringUtils.isEmpty(s)) {
                return null;
            }
            return gson.fromJson(s, typeOfT);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 返回map对象
     *
     * @param hashKey
     * @return
     */
    public static <T> Map<String, T> getAllFromHashMap(String hashKey, Class<T> c) {
        Jedis jedis = pool.getResource();
        try {
            Map<String, String> map = jedis.hgetAll(hashKey);
            Map<String, T> tMap = new HashMap<String, T>(map.size());
            for (String key : map.keySet()) {
                T value = gson.fromJson(map.get(key), c);
                tMap.put(key, value);
            }
            return tMap;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * hashmap的size
     *
     * @param hashKey
     * @return
     */
    public static long getSizeFromHashMap(String hashKey) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hlen(hashKey);
        } catch (Exception e) {

            e.printStackTrace();
            return 0;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 删除
     *
     * @param hashKey
     * @param keys
     */
    public static void removeFromHashMap(String hashKey, String... keys) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hdel(hashKey, keys);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 获取某个Map的所有key集合
     *
     * @param hashKey
     * @return
     */
    public static Set<String> getKeysFromHashMap(String hashKey) {

        Jedis jedis = pool.getResource();
        try {
            return jedis.hkeys(hashKey);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 删除某Map所有的元素
     *
     * @param hashKey
     */
    public static void removeHashMap(String hashKey) {

        if (StringUtils.isEmpty(hashKey)) {
            return;
        }

        Set<String> keySet = getKeysFromHashMap(hashKey);

        if (keySet == null || keySet.isEmpty()) {
            return;
        }

        // 化成数组
        String[] keyArr = keySet.toArray(new String[]{});

        if (keyArr == null || keyArr.length == 0) {
            return;
        }
        // 批量删除
        removeFromHashMap(hashKey, keyArr);
    }

    /**
     * 在hashmap中是否存在指定的key
     *
     * @param hashKey
     * @param key
     */
    public static boolean hasKeyFromHashMap(String hashKey, String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hexists(hashKey, key);
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        } finally {
            closeResource(jedis);
        }
    }

    public static void closeResource(Jedis jedis) {
        try {
//			pool.returnResource(jedis);
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRedisServerIp() {
        return REDIS_SERVER_IP;
    }
    @Value("${redis.server}")
    public  void setRedisServerIp(String redisServerIp) {
        REDIS_SERVER_IP = redisServerIp;
    }

    public static int getRedisPort() {
        return REDIS_PORT;
    }

    @Value("${redis.port}")
    public  void setRedisPort(int redisPort) {
        REDIS_PORT = redisPort;
    }

    public static String getRedisPassword() {
        return REDIS_PASSWORD;
    }
    @Value("${redis.password}")
    public  void setRedisPassword(String redisPassword) {
        REDIS_PASSWORD = redisPassword;
    }
    @Value("${redis.database}")
    public  void setDataBase(int dataBase) {
        DATA_BASE = dataBase;
    }
}
