package test.webCrawlerM;
import redis.clients.jedis.*;
public class redistest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
Jedis jedis=new Jedis("localhost");
String kw="";
int i=0;
for(String url:jedis.smembers("bufferPool")){

 
  kw=jedis.hget(url,"KEYWORD");
 if(kw!=null&&kw.contains("医疗")){
	 i++;
	 String model_url=jedis.hget(url, "look_up_model");
	 String website_name=jedis.hget(model_url, "website_name");
	 System.out.println(website_name);
	 for(String one:kw.split(",")){
		 if(one.contains("医疗"))
			 System.out.println(one);
		 
	 }

 }
 
 
}
System.out.println(i);

	}

}
