package test.webCrawlerM;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import org.json.*;

public class redisOperation {
	JedisPool pool;
	Jedis jedis;
	Date date;
	

	// JedisPool jp=new JedisPool();
	public redisOperation() {
		pool=new JedisPool(new JedisPoolConfig(), "localhost");
		jedis = pool.getResource();
		date=new Date();
		
	}
	
	public String getModelUrl(){
		String url=jedis.lpop("url_model_list_pool");
		jedis.rpush("url_model_list_pool", url);
		
		return url;
	}
	public void getModelUrlFromServer(){
		// JSONObject obj = new JSONObject(new JSONTokener(new FileReader(new File("1.txt"))));  
		JSONObject jsonObject = new JSONObject("{\"author\":\"//span[@bosszone='jgname']/a/text()\","
				+ "\"channel_name\":\"产业动态\","
				+ "\"channel_nexturl\":\"href=\"(.*htm)\">下一页\","
				+ "\"channel_url\":\"http://news.qq.com/l/health2012/cydt/list2011121295527.htm\","
				+ "\"content\":\"//div[@bosszone='content']//allText()\","
				+ "\"country\":\"中国\","
				+ "\"id\":1,"
				+ "\"language\":\"汉语\","
				+ "\"pubtime\":\"//span[@class='article-time']/text()\","
				+ "\"region\":\"境内\","
				+ "\"source\":\"//span[@bosszone='jgname']/a/@href\","
				+ "\"status\":0,"
				+ "\"url\":\"//div[@class=\"leftList\"]/ul/li/a/@href\","
				+ "\"website_name\":\"腾讯\"}");
		Iterator iterator = jsonObject.keys();
		String key="";
		String value="";
		while(iterator.hasNext()){
			    key = (String) iterator.next();
		        value = jsonObject.getString(key);
		        System.out.println(key);
		        System.out.println(value);
		}
	}
	
	
	public void get(String key){
		jedis.get(key);
	}
	public String getNormalUrl(){
		String nurl=jedis.lpop("normal_url_pool");
		//jedis.rpush("normal_url_pool", nurl);
		return nurl;
		
		
	}
	
	public  long  redisGetSetNums(String key){
		return jedis.scard(key);
	}
	

	public String hget(String key, String field) {
		return jedis.hget(key, field);
	}

	public void redisUrlRpush(String key,String url) {

		// this.jedis.sadd("url"+status, url);

		// String value = jedis.get("foo");
		// System.out.println(value);
		jedis.rpush(key,url);
	}

	public void redisUrlRpush(String url) {// 默认等待处理状态,所有要处理的链接添加
		                                   //到urlwait列表中
		this.redisUrlRpush(url, "wait");
	}
	public void redisUrlLpush(String key,String url){
		jedis.lpush(key, url);
	}

	public void redisUrlLpop(String key){
		jedis.lpop(key);
	}
	public void hset(String key,String field,String value){
		jedis.hset(key, field, value);
	}
	
	public void redisSadd(String key,String url){
		jedis.sadd(key, url);
	}
	
	public String redisUrlSpop(String key) {
		return jedis.spop(key);
	}

	public void mapAdd() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		// System.out.println(value);
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println(rsmap);
	}

	public void redisMapAdd(String url, Map<String, String> map) {
		jedis.hmset(url, map);

	}

	public long scard(String key) {
		// TODO Auto-generated method stub
		return jedis.scard(key);
	}
}
