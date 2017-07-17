package test.webCrawlerM;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import redis.clients.jedis.Jedis;

public class Allin1Test {
	
	//public void getModelUrlFromServer(){
		// JSONObject obj = new JSONObject(new JSONTokener(new FileReader(new File("1.txt"))));  
		//Pattern pattern = Pattern.compile(xpath_next_url);
    	
		/*
		JSONObject jsonObject = new JSONObject("{\"author\":\"//span[@bosszone='jgname']/a/text()\","
				+ "\"channel_name\":\"产业动态\","
				//+ "\"channel_nexturl\":\"href=\"(.*htm)\">下一页\","
				+ "\"channel_url\":\"http://news.qq.com/l/health2012/cydt/list2011121295527.htm\","
				+ "\"content\":\"//div[@bosszone='content']//allText()\","
				+ "\"country\":\"中国\","
				+ "\"id\":1,"
				+ "\"language\":\"汉语\","
				+ "\"pubtime\":\"//span[@class='article-time']/text()\","
				+ "\"region\":\"境内\","
				+ "\"source\":\"//span[@bosszone='jgname']/a/@href\","
				+ "\"status\":0,"
				//+ "\"url\":\"//div[@class=\"leftList\"]/ul/li/a/@href\","
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
	}*/
	public static void main(String []args) throws ParseException{
        
		Jedis jedis=new Jedis("localhost");
		for(String key:jedis.smembers("url_model")){
			jedis.lpush("url_model_list_pool", key);
		}
		
		URL furl;
		String host="";
		try {
			furl = new URL("http://www.jiankang.com/news/");
			host=furl.getHost();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(host);
		
		
		/*
		String a=" asdf";
		a=a.replace(" ", "");
		System.out.println(a);
		Date date;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		//date=new Date("Wed Jun 28 11:54:56 CST 2017");
		date=sdf.parse("2013-01-17 09:49");
		System.out.println(date.getTime());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Pattern pattern2=Pattern.compile("\"(.*?)htm");//处理提取链接
    	//Matcher matcher2=pattern2.matcher("<a href=\"index3.html\">下一页</a>");
    	//matcher2.find();
    	//System.out.println(matcher2.group().substring(1));
		/*
		Jedis jedis=new Jedis();
		String a="3";
		String b="5";
		redisOperation rO=new redisOperation();
		//rO.getModelUrlFromServer();
		//System.out.println();
	//Date date=new Date();
	//System.out.println(date.getTime());
	//System.out.println("12345678".substring(4));
	crawler c=new crawler();
	Allin1Test ttt=new Allin1Test();
	ttt.getModelUrlFromServer();
	//Document doc=c.htmlDownload("http://nurs.dxy.cn/article/520365");
	//System.out.println(doc.text());
	//jedis.hset("longtext1", "text", doc.text());
	//System.out.println(jedis.hget("longtext1", "text"));
	Map<String,String> map = new HashMap<String,String>();
	long l=2;
	map.put("asd", "sad");
	String urlt="$http://med.sina.com/article_list_100_2_$_382.html";
	//System.out.println("");
//	System.out.println(urlt.startsWith("$"));
//	System.out.println(urlt.indexOf("$",1));
///	System.out.println(urlt.substring(1).replaceAll("\\$", String.valueOf(l)));
	MultiThreadCrawler mt=new MultiThreadCrawler();
	Thread thread1=new Thread(mt);
	//thread1.start();
	//Thread thread2=new Thread(new MultiThreadCrawler());
	///thread2.start();
	//Thread thread3=new Thread(new MultiThreadCrawler());
	//thread3.start();*/
		
	
	}
}
