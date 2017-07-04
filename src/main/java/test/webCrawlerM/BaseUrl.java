package test.webCrawlerM;

import java.util.List;
import java.util.Set;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import redis.clients.*;
public class BaseUrl {
	
	public static Set<String> baseUrl;
	redisOperation rO;

	// public String
   
	public static String getNormalUrl() {

		return baseUrl.iterator().next();
	}

	public void SearchBaseUrl(String keyWord) {
		 crawler c=new crawler();
		 rO=new redisOperation();
		 String searchUrl="https://www.baidu.com/s?wd=";
		 searchUrl+=keyWord;
         Document d=c.htmlDownload(searchUrl);
         //System.out.println(d);
         c.getAndStoreBaiduUrl(d);
	}

	
	
	
	
	
	
	
	
	
	
	
	public static void main(String [] args){
		
		
		
		BaseUrl bs=new BaseUrl();
		bs.SearchBaseUrl("你好疯子");
		
		
		
	}
	
	
	
	
	
	
	

}
