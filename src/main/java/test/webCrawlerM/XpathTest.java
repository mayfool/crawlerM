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
	}
	
//使用方法 可以利用方法a来进行对单个xPath的判断，
//也可以通过方法c传入一个String数组的方法来对所有数组里的xPath
//进行判断 
	
	public static String XpathTestOne(String url,String xPath) throws IOException{//a
		Document doc=Jsoup.connect(url).get();
		String result=Xsoup.compile(xPath).evaluate(doc).get();
		if(result==null||result=="")
		 return "0";
		
		return "1";
		
	}
	
	
	//对单个传入的xPath进行测试
	public static String XpathTestOne(Document doc,String xPath){
		String result=Xsoup.compile(xPath).evaluate(doc).get();
		if(result==null||result=="")
		 return "0";
		
		return "1";
		
		
		
	}

	public static String XpathTests(String  url,String []xpaths) throws IOException{
		Document doc=Jsoup.connect(url).get();
		int length=xpaths.length;
		String value="";
		for(int i=0;i<length;i++){
			value+=XpathTestOne(doc,xpaths[i]);
			
		}
		
		
		
		return value;
		
	}
	
	
}
