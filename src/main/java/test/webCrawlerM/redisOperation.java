package test.webCrawlerM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redisOperation {
	JedisPool pool=new JedisPool(new JedisPoolConfig(),"localhost");
	Jedis jedis ;
	//JedisPool jp=new JedisPool();
	public redisOperation(){
		jedis= pool.getResource();
	}
    public String  hget(String key,String field){
    	return jedis.hget(key, field);
    }
	public  void redisUrlSadd(String url,String status){
	
	//	this.jedis.sadd("url"+status, url);

	
	//String value = jedis.get("foo");
	//System.out.println(value);
		jedis.sadd("url:"+status, url);
	}
	public void redisUrlSadd(String url){//默认等待处理状态
		this.redisUrlSadd(url,"wait");
	}
	public String redisUrlSpop(String key){
		return jedis.spop(key);
	}
	
	
	public void mapAdd(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		// System.out.println(value);
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println(rsmap);
	}
	public void redisMapAdd(String url,Map<String,String> map){
		jedis.hmset(url, map);
		
		
		
		
		
		
	}
}
