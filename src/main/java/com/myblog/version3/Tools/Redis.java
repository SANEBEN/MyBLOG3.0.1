package com.myblog.version3.Tools;

import com.myblog.version3.entity.Article;
import com.myblog.version3.mapper.articleMapper;
import com.myblog.version3.mapper.messageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class Redis {

    private Logger logger = LoggerFactory.getLogger(Redis.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private articleMapper articleMapper;

    @Autowired
    private messageMapper messageMapper;

    /**
     * 创建一个键值对
     *
     * @param key   指定的key值
     * @param value 指定的value值
     */
    private void set(String key, Integer value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value.toString());
        jedis.close();
    }

    /**
     * 获取指定key值的键值对的值
     *
     * @param key key值
     * @return 返回key值对应的值，没有则返回null
     */
    private String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    private boolean exists(String key){
        Jedis jedis = jedisPool.getResource();
        boolean result = jedis.exists(key);
        jedis.close();
        return result;
    }

    /**
     * 判断摸一个文章是否有对应的redis数据
     *
     * @param Aid 文章的ID
     * @return 返回一个布尔值
     */
    private boolean hasArticleHitsData(String Aid) {
        Jedis jedis = jedisPool.getResource();
        logger.info("检查是否有：" + jedis.exists("articleHits_" + Aid));
        boolean result = jedis.exists("articleHits_" + Aid);
        jedis.close();
        return result;
    }

    /**
     * 获取指定文章的点击数
     *
     * @param Aid 文章的ID
     * @return 返回点击数
     */
    public int getArticleHits(String Aid) {
        String hits = get("articleHits_" + Aid);
        if (hits == null) {
            Article article = articleMapper.getByID(Aid);
            int hit = article.getHits();
            set("articleHits_" + Aid, hit);
            return hit;
        } else {
            return Integer.parseInt(hits);
        }
    }

    /**
     * 更新指定文章的点击数据
     *
     * @param Aid 文章ID
     */
    public void updateArticleHits(String Aid) {
        if (!hasArticleHitsData(Aid)) {
            logger.info("通过数据库获取数据");
            articleMapper.updateHit(Aid);
            set("articleHits_" + Aid, articleMapper.getByID(Aid).getHits());
            insertArticleList(Aid);
        } else {
            logger.info("从内存中获取数据");
            int data = Integer.parseInt(get("articleHits_" + Aid));
            set("articleHits_" + Aid, data + 1);
        }
    }

    /**
     * 在redis储存中点击数改变的文章信息
     *
     * @param Aid 文章ID
     */
    private void insertArticleList(String Aid) {
        logger.info("在文章列表中插入了一个元素");
        Jedis jedis = jedisPool.getResource();
        jedis.rpush("articleList", "articleHits_" + Aid);
        jedis.close();
    }

    /**
     * 获得redis中存储的文章列表
     * 获取指定范围的记录
     * lrange 下标从0开始 -1表示最后一个元素
     *
     * @param start 开始获取的位数
     * @param end   结束获取的位数
     * @return 返回文章信息的列表
     */
    public List<String> getArticleList(int start, int end) {
        Jedis jedis = jedisPool.getResource();
        List<String> articles = jedis.lrange("articleList", start, end);
        jedis.close();
        return articles;
    }

    /**
     * 清空List数据
     * ltrim 让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     * start和end为0时，即清空list
     *
     * @param start 开始位置
     * @param end   结束位置
     * @return 返回结果
     */
    public String emptyArticleList(int start, int end) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.ltrim("articleList", start, end);
        jedis.close();
        return result;
    }

    /**
     * 删除数据库中的所有key
     */
    public void emptyDataBase() {
        Jedis jedis = jedisPool.getResource();
        jedis.flushAll();
        jedis.close();
    }

    /**
     * 同步文章点击数
     * @return
     * 返回更新结构
     */
    public String synchronousArticleHits() {
        List<String> articles = getArticleList(0, 0);
        for (String article : articles) {
            articleMapper.setHits(article.split("_")[1], Integer.parseInt(get(article)));
        }
        return "同步成功";
    }

    /**
     * 获得文章的总数
     * @return
     * 返回一个int值
     */
    public int getArticleNumber(){
        Jedis jedis = jedisPool.getResource();
        if(jedis.exists("ArticleNumber")){
            int result = Integer.parseInt(jedis.get("ArticleNumber"));
            jedis.close();
            return result;
        }else {
            Integer number = articleMapper.statistical();
            jedis.set("ArticleNumber" ,number.toString());
            jedis.close();
            return number;
        }
    }

    /**
     * 更新文章数
     * @return
     * 返回一个布尔值
     */
    public void updateArticleNumber(int number){
        Jedis jedis = jedisPool.getResource();
        if(jedis.exists("ArticleNumber")){
            set("ArticleNumber",Integer.parseInt(get("ArticleNumber"))+number);
        }else {
            getArticleNumber();
            updateArticleNumber(1);
        }
    }

    /**
     * 获得非管理员的用户总数
     * @return
     * 返回一个int值
     */
    public int getUserNumber(){
        Jedis jedis = jedisPool.getResource();
        if(jedis.exists("UserNumber")){
            int result = Integer.parseInt(jedis.get("UserNumber"));
            jedis.close();
            return result;
        }else {
            Integer number = messageMapper.statistical();
            jedis.set("UserNumber" ,number.toString());
            jedis.close();
            return number;
        }
    }

    /**
     * 更新用户数量
     * @return
     * 返回一个布尔值
     */
    public void updateUserNumber(int number){
        Jedis jedis = jedisPool.getResource();
        if(jedis.exists("UserNumber")){
            set("UserNumber",Integer.parseInt(get("UserNumber"))+number);
        }else {
            getUserNumber();
        }
    }

    /**
     * 获得网站主页的访问数
     * @return
     * 返回一个int值
     */
    public int getIndexVisit(){
        Jedis jedis = jedisPool.getResource();
        if(jedis.exists("indexVisit")){
            int result = Integer.parseInt(get("indexVisit"));
            jedis.close();
            return result;
        }else {
            set("indexVisit" ,1);
            return 1;
        }
    }

    /**
     * 更新主页访问数
     */
    public void updateIndexVisit(){
        if(exists("indexVisit")){
            set("indexVisit",Integer.parseInt(get("indexVisit"))+1);
        }else {
            set("indexVisit",1);
        }
    }
}
