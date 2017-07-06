package test.webCrawlerM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class AddUrl {
	redisOperation rO;
	
	public AddUrl(){
		rO=new redisOperation();
	}
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String,String>();
		Jedis jedis=new Jedis();

		   String pathname = "C:\\Users\\Forcast1\\Desktop\\模板信息.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
           File filename = new File(pathname); // 要读取以上路径的input。txt文件  
           InputStreamReader reader = new InputStreamReader(  
                   new FileInputStream(filename),"utf-8"); // 建立一个输入流对象reader  
           BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
           String line = "";  
           
           String url="";
           while (line != null) {  
               line = br.readLine(); // 一次读入一行数据  
              // System.out.println(line);
               String[]a;
               long before=0;
               long now=0;
               String url_model="url_model";
               try{
                a=line.split("\\s+");
                System.out.println(a[0]);
                if(a[0].equals("CHANNEL_URL")){
             	   url=a[1];
             	   before=jedis.scard(url_model);
             	   jedis.sadd(url_model, url);
             	   now=jedis.scard(url_model);
             	   if(now-before==1){
             		   jedis.rpush("url_model_list_pool", a[1]);
             	   }
                }
                
               jedis.hset(url, a[0], a[1]);
               //System.out.println(a[0]);
               // System.out.println(a[1]);
                }
               catch(Exception e){
            	   System.out.println("add url 出错了");
               }
            
	}
   		System.out.println(jedis.hget("http://news.qq.com/l/health2012/cydt/list2011121295527.htm", "CONTENT"));

}
}
