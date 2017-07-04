package test.webCrawlerM;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class crawler implements Runnable {
	redisOperation rO;
	String searchInfoUrl;
	static int a = 1;

	public crawler() {

		rO = new redisOperation();
		AddCoreUrl("http://www.dxy.cn/");
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
			// doc = Jsoup.connect(url).get();
			doc = Jsoup.parse(new URL(url).openStream(), "utf-8", url);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("fetching %s", url);
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

	public void AddCoreUrl(String url) {
		storeCoreUrl(url);
	}

	public void storeCoreUrl(String url) {
		rO.redisUrlSadd(url, "core");

	}

	public void getAndStoreBaiduUrl(Document doc) {

		Elements links = doc.select("a[data-click]");
		for (Element link : links) {
			if (link.attr("target").equals("_blank")) {
				if (link.attr("href").contains("cache")) {
					System.out.println("百度快照");
				} else {
					System.out.println(link.attr("href"));
					rO.redisUrlSadd(link.attr("href"), "core");
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
				rO.redisUrlSadd(link.attr("href"));
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
					System.out.println(link.attr("href"));
					rO.redisUrlSadd(link.attr("href"));
					System.out.println(urlPop);
					rO.redisMapAdd(link.attr("href"), map);
					// System.out.println(link.attr("title"));

					// Document
				}
			}

		}
	}

	public void storeData(String url) {

	}

	public void getInformation(String url) {
		Document doc = htmlDownload(url);
		String title = doc.title();
		String word = doc.text();
	}

	public static void main(String[] args) {
		//String url = "http://www.hc3i.cn/";
		// BaseUrl.addUrl(url);
		//crawler c = new crawler();
		// Document doc = c.htmlDownload(url);
		//c.weatherAddFromBaseUrl();
		// c.getAndStoreBaiduUrl(doc);
		//System.out.println(getModifyDate("http://www.baidu.com"));
		//crawler c=new crawler();
		testXpath("a","http://www.sina.com");

	}

	public void run() {
		// TODO Auto-generated method stub

	}
}
