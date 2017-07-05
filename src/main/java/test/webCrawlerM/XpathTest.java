package test.webCrawlerM;

import java.io.IOException;
import java.util.List;

import org.htmlcleaner.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.jsoup.*;
import org.jsoup.nodes.Document;

import junit.framework.Assert;
import us.codecraft.xsoup.Xsoup;
public class XpathTest {
	public static void main(String [] args) throws IOException, XPatherException{
	@SuppressWarnings("restriction")
	String html =  Jsoup.connect("http://www.cnblogs.com/zyw-205520/").get().html();
	XPathFactory xPathFactory = XPathFactory.newInstance();
	XPath xPath = xPathFactory.newXPath();
	
	String url = "http://zhidao.baidu.com/daily";
	String contents = Jsoup.connect(url).post().html();

	HtmlCleaner hc = new HtmlCleaner();
	TagNode tn = hc.clean(contents);
	String xpath = "//h2/a/@href";
	Object[] objects = tn.evaluateXPath(xpath);
	System.out.println(objects.length);
	
	
	
	
	String html2 = "<html><div><a href='https://github.com'>github.com</a></div>" +
            "<table><tr><td>a</td><td>b</td></tr></table></html>";

    Document document = Jsoup.parse(html2);

    String result = Xsoup.compile("//b/@mf").evaluate(document).get();
    if(result==null){
    	System.out.println("null");
    }
    else if(result==""){
    	System.out.println("kong");
    }
//    Assert.assertEquals("https://github.com", result);

    List<String> list = Xsoup.compile("//tr/td/text()").evaluate(document).list();
    Assert.assertEquals("a", list.get(0));
    Assert.assertEquals("b", list.get(1));
    
    
    String a=XpathTest.XpathTestUrl("http://news.qq.com/l/health2012/cydt/list2011121295527_2.htm", "//div[@class='leftList']/ul/li/a/@href");
	System.out.println(a);
	String []xpaths=new String[2];
	xpaths[0]="//div[@class=”leftList”]/ul/li/a/@href";
	xpaths[1]="//div[@id='C-Main-Article-QQ']/div/h1/text()";
	String mm=XpathTest.AllTestIn1("http://news.qq.com/l/health2012/cydt/list2011121295527_2.htm", xpaths);
	System.out.println(mm);
	}
	
//使用方法 可以利用方法a来进行对单个xPath的判断，
//也可以通过方法c传入一个String数组的方法来对所有数组里的xPath
//进行判断 
	
	public static String XpathTestUrl(String mobanUrl,String xPath) throws IOException{//a
		Document doc=Jsoup.connect(mobanUrl).get();
		String result=Xsoup.compile("//div[@class='leftList']/ul/li/a/@href").evaluate(doc).get();
		System.out.println("结果是"+result);
		return result;
		/*if(result==null||result=="")
		 return false;
		
		return true;*/
		
	}
	
	
	//对单个传入的xPath进行测试
	public static String XpathTestOne(Document doc,String xPath){
		String result=Xsoup.compile(xPath).evaluate(doc).get();
		if(result==null||result=="")
		 return "0";
		
		return "1";
		
		
		
	}
    //对获取到的网页上的url进行测试
	public static String XpathTests(String  url,String []xpaths) throws IOException{
		Document doc=Jsoup.connect(url).get();
		int length=xpaths.length;
		String value="";
		for(int i=0;i<length;i++){
			value+=XpathTestOne(doc,xpaths[i]);
			
		}
		
		
		
		return value;
		
	}
	
	public static String AllTestIn1(String url,String[] xPaths) throws IOException{
		String result=XpathTestUrl(url,xPaths[0]);
		String value="1";
		if(result==null||result==""){
			return "无法抓取到当前页面的url";
		}
		Document doc=Jsoup.connect(result).get();
		for(int i=1;i<xPaths.length;i++){
		  value+=XpathTestOne(doc,xPaths[i]);
		}
		return value;
	}
	
	
}
