package test.webCrawlerM;

import java.io.IOException;
import java.util.Iterator;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

public class DistributedCrawler extends crawler {
	
	public void AddUrl(String url){
		long before=0;
        long now=0;
        String url_model="url_model";
       
         //a=line.split("\\s+");
        // System.out.println(a[0]);
         //if(a[0].equals("CHANNEL_URL")){
      	   ///url=a[1];
      	   before=rO.scard(url_model);
      	   rO.redisSadd(url_model, url);
      	   now=rO.scard(url_model);
      	   if(now-before==1){
      		   rO.redisUrlLpush("url_model_list_pool", url);
      	   }
         
	}
	
	
    public boolean getModelFromHttpServer() throws IOException{
    	Response res=Jsoup.connect("http://211.87.229.46:6080/task/0").ignoreContentType(true).execute();;
    	String body = res.body();
    	JSONObject jsonObject = new JSONObject(body);
    	Iterator iterator = jsonObject.keys();
		String json_key="";
		String value="";
		int status=jsonObject.getInt("status");
		if(status==1){
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		String channel_name="";
		String cunntry="";
		String author="";
		String language="";
		String source="";
		String content="";
		String url="";
		String channel_url=jsonObject.getString("channel_url");
		this.AddUrl(channel_url);
		String website_name="";
		String channel_nexturl="";
		String pubtime="";
		int  id;
		String region="";
		
	   
		
		
		
		
		
		
		
		System.out.printf("status%d",status);
		while(iterator.hasNext()){
			try{
			    json_key = (String) iterator.next();
		        value = (String) jsonObject.getString(json_key);
		        rO.hset(channel_url, json_key, value);
		        System.out.println(json_key);
		        System.out.println(value);
		        }
			catch(org.json.JSONException e){
				value=Integer.toString(jsonObject.getInt(json_key));
				rO.hset(channel_url, json_key, value);
				System.out.println(json_key);
		        System.out.println(value);
			}
		}
    	//System.out.println(jsonObject.keys());
   
    
    return true;
    }
    public void crawlerForModel(){
    	String url;
    	while(true){
    	
    	url=rO.getModelUrl();
    	/*if(!weatherAddFromBaseUrl(url)){
    		try {
				getModelFromHttpServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		continue;
    	}*/
    	String xpath=rO.hget(url, "url");
    	String xpath_next_url=rO.hget(url, next_url);
    	//String xpath_next_url=rO.hget(url, next_url);
    	if(xpath_next_url.startsWith("$")){
    		this.crawlerForModelWithoutRegexNextUrl(url, xpath, xpath_next_url,url);
    	}
    	else{
    		this.crawlerForModelWithRegexNextUrl(url, xpath, xpath_next_url,url);
    	}
    	rO.hset(url, "crawle_time", String.valueOf(date.getTime()));
    	}
    }
    
    public void pureCrawlerForModel(){
    	
    	
    	
    }
    public static void main(String[]args) throws IOException{
    	DistributedCrawler dc=new DistributedCrawler();
    	dc.crawlerForModel();
    }
    
    
    
}
