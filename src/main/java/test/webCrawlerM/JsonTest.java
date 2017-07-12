package test.webCrawlerM;
import org.json.*;
public class JsonTest {

	
	public static void main(String [] args){
		JSONObject jsonobj = new JSONObject("{\"author\":\"//span[@bosszone='jgname']/a/text()\"}");
		String a=jsonobj.getString("author");
		System.out.println(a);
		//{"author":"//span[@bosszone='jgname']/a/text()","channel_name":"产业动态","channel_nexturl":"href=\"(.*htm)\">下一页","channel_url":"http://news.qq.com/l/health2012/cydt/list2011121295527.htm","content":"//div[@bosszone='content']//allText()","country":"中国","id":1,"language":"汉语","pubtime":"//span[@class='article-time']/text()","region":"境内","source":"//span[@bosszone='jgname']/a/@href","status":0,"url":"//div[@class=\"leftList\"]/ul/li/a/@href","website_name":"腾讯"};
		
	}
}
