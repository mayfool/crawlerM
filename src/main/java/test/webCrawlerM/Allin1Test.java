package test.webCrawlerM;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import redis.clients.jedis.Jedis;

public class Allin1Test {
	
	
	public static void main(String []args){
		Jedis jedis=new Jedis();
		String a="3";
		String b="5";
		//System.out.println();
	//Date date=new Date();
	//System.out.println(date.getTime());
	//System.out.println("12345678".substring(4));
	crawler c=new crawler();
	//Document doc=c.htmlDownload("http://nurs.dxy.cn/article/520365");
	//System.out.println(doc.text());
	//jedis.hset("longtext1", "text", doc.text());
	//System.out.println(jedis.hget("longtext1", "text"));
	Map<String,String> map = new HashMap<String,String>();
	long l=0;
	map.put("asd", "sad");
	MultiThreadCrawler mt=new MultiThreadCrawler();
	Thread thread1=new Thread(mt);
	thread1.start();
	//Thread thread2=new Thread(new MultiThreadCrawler());
	///thread2.start();
	//Thread thread3=new Thread(new MultiThreadCrawler());
	//thread3.start();
	
	}
}
