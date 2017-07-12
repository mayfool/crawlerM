package test.webCrawlerM;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.jsoup.*;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.xsoup.Xsoup;

public class crawler implements Runnable {
	redisOperation rO;
	String searchInfoUrl;
	static int a = 1;
	String look_up_model="look_up_model";
	String title="title";
	String author="author";
	String pubtime="pubtime";
	String content="content";
	String source="source";
	String next_url="channel_nexturl";
	Date date;

	public crawler() {
        date=new Date();
		rO = new redisOperation();
		//AddCoreUrl("http://www.dxy.cn/");
		a++;
		getA();

	}

	public crawler(String searchInfoUrl) {

		this.searchInfoUrl = searchInfoUrl;

	}

	public static int getA() {
		System.out.println(a);
		return a;
	}

	public Document htmlDownload(String url) {
		Document doc = null;
		// Document document = Jsoup.parse(new URL(url).openStream(), "GBK",
		// url);
		try {
			doc = Jsoup.connect(url).timeout(5000).get();
			//doc = Jsoup.parse(new URL(url).openStream(), "utf-8", url);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("fetching %s", url);
		System.out.println();
		return doc;

	}

	public static long getModifyDate(String frontURL) {
		URL u = null;
		HttpURLConnection http = null;
		try {
			u = new URL(frontURL);
			http = (HttpURLConnection) u.openConnection();
			http.setRequestMethod("HEAD");
		} catch (MalformedURLException e) {
			// DO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// DO Auto-generated catch block
			e.printStackTrace();
		}
		Date lastModify = new Date(http.getLastModified());
		// String a=lastModify.getTime()+"";
		// a=a.substring(4);
		// int m=Integer.parseInt(a);
		long m = lastModify.getTime();
		long spec=1465786208000L;
		
		return m==spec?m:-1;
		/*
		 * Date anotherdate=new Date();
		 * //System.out.println(date.toLocaleString()); int
		 * a=lastModify.compareTo(anotherdate);
		 * System.out.println(lastModify.toLocaleString());
		 * System.out.println(anotherdate.toLocaleString());
		 * System.out.println(a);
		 */
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		// String date = sdf.format(lastModify);
		/*
		 * if(a<0){ return true;//需要重新处理网页 } return false;
		 */
	}
/*
	public void AddCoreUrl(String url) {
		storeCoreUrl(url);
	}

	public void storeCoreUrl(String url) {
		rO.redisUrlRpush(url, "core");

	}
*/
	public void getAndStoreBaiduUrl(Document doc) {

		Elements links = doc.select("a[data-click]");
		for (Element link : links) {
			if (link.attr("target").equals("_blank")) {
				if (link.attr("href").contains("cache")) {
					System.out.println("百度快照");
				} else {
					System.out.println(link.attr("href"));
					rO.redisUrlRpush(link.attr("href"), "core");
					// System.out.println(link.attr("title"));
				}
			}
		}
	}
	
	
	public static String testXpath(String xPath,String url){
		String localUrl=url;
		Document doc = null;
		try {
			 doc = Jsoup.parse(new URL(url).openStream(), "utf-8", localUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements theOne=doc.select(xPath);
		return theOne.toString();
		//System.out.println(theOne.toString());
		
	}

	public void getAndStoreNormalUrl(Document doc, long lastModify) {
		String title = "";
		String text = "";
		long lastModify1 = lastModify;
		Map<String, String> map = null;

		Elements links = doc.select("a[data-click]");
		for (Element link : links) {
			if (link.attr("target").equals("_blank")) {
				title = doc.title();
				text = doc.text();

				map.put("title", title);
				map.put("text", text);
				System.out.println(link.attr("href"));
				rO.redisUrlRpush(link.attr("href"));
				// System.out.println(link.attr("title"));

			}
		}
	}

	@SuppressWarnings("restriction")
	public void weatherAddFromBaseUrl() {// 判断是否要抓取
		String urlPop = rO.redisUrlSpop("url:core");
		String title;
		String text;

		Document doc;
		Date nowTime;
		long lnow = 0;
		long lastModify = 0;
		String slastModify = "";

		Map<String, String> map = new HashMap<String, String>();
		while (urlPop != null) {
			nowTime = new Date();
			lnow = nowTime.getTime();//当前时间
			try {
				

				
				slastModify = rO.hget(urlPop, "lastModify");
				System.out.println(slastModify);
			} catch (Exception e) {
				System.out.println("没有查找到最后修改时间");
				e.printStackTrace();
			}
			if (slastModify != null) {
				lastModify = Long.parseLong(slastModify);
				if (lnow - lastModify < 200000) {
					// 上一次爬取时间-网页上一次更新时间
					urlPop = rO.redisUrlSpop("url:core");
					continue;
				}

			}
			long updateTime=getModifyDate(urlPop);
			if(updateTime!=-1&&updateTime<lastModify){//网页更新时间小于存储的上次爬取时间
				continue;
			}
			doc = htmlDownload(urlPop);
			// urlPop=rO.redisUrlSpop("url:core");

			// getAndStoreNormalUrl(now);

			Elements links = doc.select("a");
			@SuppressWarnings("unused")
			String slink;
			for (Element link : links) {
				if (link.attr("target").equals("_blank")) {
					// slink=link.toString();
					title = doc.title();
					text = doc.text();
					map.put("lastModify", String.valueOf(lnow));
					//上一次爬取的时间
					map.put("title", title);
					map.put("text", text);
//System.out.println(link.attr("href"));
					rO.redisUrlRpush(link.attr("href"));
//System.out.println(urlPop);
					rO.redisMapAdd(link.attr("href"), map);
// System.out.println(link.attr("title"));

					// Document
				}
			}

		}
	}
	
	public boolean weatherAddFromBaseUrl(String url){
		//Document doc;
		//Date nowTime;
		long lnow = 0;
		long lastCrawle = 0;
		String slastCrawle = "";
		//nowTime = new Date();
		lnow = date.getTime();//当前时间
		try {
			

			
			slastCrawle = rO.hget(url, "crawle_time");
			System.out.println(slastCrawle);
		} catch (Exception e) {
			System.out.println("没有查找到最后修改时间");
			e.printStackTrace();
			return true;
		}
		if (slastCrawle != null) {
			lastCrawle = Long.parseLong(slastCrawle);
			if (lnow - lastCrawle < 200000) {//3分钟内不对同一模板进行检测
				// 当前时间-网页上一次更新时间
				 
			    return false;
			}
			else{
				return true;
			}

		}
		long updateTime=getModifyDate(url);//从网页获取上次更新时间
		if(updateTime!=-1&&updateTime<lastCrawle){//网页更新时间小于存储的上次爬取时间
			return false;
		}
		
		return true;
	}
	
    public boolean urlAdd(String url,String url_model){//把模板url中需要处理的url添加到bufferPool队列中
    	String key="bufferPool";
    	long before=rO.redisGetSetNums(key);
    	rO.redisSadd(key,url);
        rO.hset(url, look_up_model, url_model);
    	long now=rO.redisGetSetNums(key);
    	if(now-before==1){//如果集合中没有，则添加到列表中
    		rO.redisUrlRpush("normal_url_pool",url);
    		
    		return true;
    	}
    	return false;
    }
	

    
    public void crawlerForModel(){
    	String url;
    	while(true){
    	
    	url=rO.getModelUrl();
    	if(!weatherAddFromBaseUrl(url)){
    		continue;
    	}
    	String xpath=rO.hget(url, "URL");
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
    public void crawlerForModelWithRegexNextUrl(String source_url,String xpath,String xpath_next_url,String model_url){
    	
    	
    	Document doc=this.htmlDownload(source_url);
    	//String xpath=rO.hget(url, "URL");
    	//String xpath="//div[@class='leftList']/ul/li/a/@href";
    	
    	//String xpath_next_url="href=\'(.*htm)\'>下一页";
    	String htmlString="";
    	
    	
    	
    	Pattern pattern = Pattern.compile(xpath_next_url);
    	Pattern pattern2=Pattern.compile("http(.*?)htm");
		//Document doc=Jsoup.connect("http://news.qq.com/l/health2012/mtjd/list2011121295102.htm").get();
		//Document doc=c.htmlDownload("http://news.qq.com/l/health2012/mtjd/list2011121295102.htm");
    	//List<String> normalUrls=c.getListInformationByXpath(doc, xpath);
    	try{
	    htmlString = doc.html();}
    	catch(Exception e){
    		e.printStackTrace();
    	}
		Matcher matcher = pattern.matcher(htmlString);
		matcher.find();
		Matcher matcher2=pattern2.matcher(matcher.group());
    	matcher2.find();
    	String page_next_url="";
    	try{
    
    	page_next_url=matcher2.group();}
    	catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	
    	
    	
    	
    	
    	

    	List<String> normalUrls=this.getListInformationByXpath(doc, xpath);

    	
    	for(String normalUrl:normalUrls){
    		this.urlAdd(normalUrl,model_url);
    	}
    	if(page_next_url!=""){
    	crawlerForModelWithRegexNextUrl(page_next_url,xpath,xpath_next_url,model_url);}
    	
    }
    
    
    public void crawlerForModelWithoutRegexNextUrl(String source_url,String xpath,String xpath_next_url,String model_url){
    	String page_next_url;
    	for(int i=1;i<100;i++){
    	page_next_url=xpath_next_url.substring(1).replaceAll("\\$", String.valueOf(i));
    	this.crawlerForModelWithoutRegexNextUrlBase(page_next_url, xpath,model_url);
    	}
    }
    
    
    public void crawlerForModelWithoutRegexNextUrlBase(String source_url,String xpath,String model_url){
    	//String url;
    	
    	Document doc=this.htmlDownload(source_url);
    	
    	List<String> normalUrls=this.getListInformationByXpath(doc, xpath);
    
    	for(String normalUrl:normalUrls){
    		this.urlAdd(normalUrl,model_url);
    	}
    	
    	
    	
    	
    	}
    
    
    public void crawlerForNormalUrl(){
    	while(true){
    	String nurl=rO.getNormalUrl();
    	if(nurl.startsWith("/")){
    		nurl=rO.hget(nurl,"look_up_model")+nurl;
    	}
    	Document doc=this.htmlDownload(nurl);
    	String modelUrl=rO.hget(nurl, look_up_model);
    	try{
    	String xpath_title=rO.hget(modelUrl, title);
    	String xpath_author=rO.hget(modelUrl, author);
    	String xpath_pubtime=rO.hget(modelUrl, pubtime);
    	String xpath_content=rO.hget(modelUrl, content);
    	String xpath_source=rO.hget(modelUrl, source);
    	
    	String page_title=this.getInformationByXpath(doc,xpath_title);
    	String page_author=this.getInformationByXpath(doc,xpath_author);
    	String page_pubtime=this.getInformationByXpath(doc,xpath_pubtime);
    	String page_content=this.getInformationByXpath(doc,xpath_content);
    	String page_source=this.getInformationByXpath(doc,xpath_source);
    	//String page_title=this.getInformationByXpath(doc,xpath_title);
    	page_title=(page_title==null)?"":page_title;
    	page_author=(page_author==null)?"":page_author;
    	page_pubtime=(page_pubtime==null)?"":page_pubtime;
    	page_content=(page_content==null)?"":page_content;
    	page_source=(page_source==null)?"":page_source;
    	
    	
    	
    	
    	
    	
    	rO.hset(nurl, title, page_title);
    	rO.hset(nurl, author, page_author);
    	rO.hset(nurl, pubtime, page_pubtime);
    	rO.hset(nurl, content, page_content);
    	rO.hset(nurl,source,page_source);}
    	
    	catch(Exception e){
    		System.out.println(e.getMessage());
    		System.out.println("插入出错");
    	}
    	}
    	
    }
	
	
	public void storeData(String url) {

	}
	public List<String> getListInformationByXpath(Document doc,String xpath){
		//返回一个处理的列表值
		List<String> results=Xsoup.compile(xpath).evaluate(doc).list();
		return results;
	}
    public String getInformationByXpath(Document doc,String xpath){
    //根据xpath获取需要的数据
    	String result=Xsoup.compile(xpath).evaluate(doc).get();
    	return result;
    }
	public void getInformation(String url) {
		Document doc = htmlDownload(url);
		Date nowTime = new Date();
		long lnow = nowTime.getTime();
		String title;
		String text;
		Map<String,String> map=new HashMap<String,String>();
		doc = htmlDownload(url);
		// urlPop=rO.redisUrlSpop("url:core");

		// getAndStoreNormalUrl(now);

		Elements links = doc.select("a");
		@SuppressWarnings("unused")
		String slink;
		for (Element link : links) {
			if (link.attr("target").equals("_blank")) {
				// slink=link.toString();
				title = doc.title();
				text = doc.text();
				map.put("lastModify", String.valueOf(lnow));
				//上一次爬取的时间
				map.put("title", title);
				map.put("text", text);
				System.out.println(link.attr("href"));
				rO.redisUrlRpush(link.attr("href"));
				System.out.println(url);
				rO.redisMapAdd(link.attr("href"), map);
			}}
	}

	public static void main(String[] args) throws IOException {
		//String url = "http://www.hc3i.cn/";
		// BaseUrl.addUrl(url);
		crawler c = new crawler();
		// Document doc = c.htmlDownload(url);
		//c.weatherAddFromBaseUrl();
		// c.getAndStoreBaiduUrl(doc);
		//System.out.println(getModifyDate("http://www.baidu.com"));
		//crawler c=new crawler();
		testXpath("a","http://www.sina.com");
		Pattern pattern = Pattern.compile("href=\"(.*htm)\">下一页");
		//Document doc=Jsoup.connect("http://news.qq.com/l/health2012/mtjd/list2011121295102.htm").get();
		//Document doc=c.htmlDownload("http://news.qq.com/l/health2012/mtjd/list2011121295102.htm");
    	//List<String> normalUrls=c.getListInformationByXpath(doc, xpath);
		//String htmlString = doc.html();
		/*Matcher matcher = pattern.matcher(htmlString);
		matcher.find();
		matcher.group();
		System.out.println();
    	//String page_next_url=c.getInformationByXpath(doc, "href=\\'(.*htm)\\'>下一页");
    	System.out.println();*/
		
//c.crawlerForModelWithRegexNextUrl("http://news.qq.com/l/health2012/yyqy/list2011121295156.htm","//div[@class='leftList']/ul/li/a/@href","href=\"(.*htm)\">下一页");
//c.crawlerForModelWithoutRegexNextUrl("http://med.sina.com/article_list_100_2_1_382.html", "//div[@class='indextitle-text']/a/@href", "$http://med.sina.com/article_list_100_2_$_382.html");       // c.crawlerForModel();
//c.crawlerForNormalUrl();
		//c.crawlerForNormalUrl();
		//c.getModelFromHttpServer();
		String a=null;
		String b=(a==null)?"qwe":"sdf";
		System.out.println(c.htmlDownload("http://health.people.com.cn/GB/408565/index.html"));

		System.out.println(b);
	}

	public void run() {
		// TODO Auto-generated method stub

	}
}
