package test.webCrawlerM;

import  org.openqa.selenium.By;
import  org.openqa.selenium.WebDriver;
import  org.openqa.selenium.WebElement;
import  org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import  java.util.List;
import  java.util.Random;
  

public  class  JavaScriptCrawler {
     public  static  void  main(String[] args)  throws  Exception{
    
    	 System.setProperty("webdriver.chrome.driver","C:\\Users\\Forcast1\\Downloads\\chromedriver_win32\\chromedriver.exe");
    	 WebDriver driver = new ChromeDriver();  
         driver.get("http://roll.sohu.com/sports/");  
         Thread.sleep(5000);  
         WebElement more = driver.findElement(By.cssSelector("#contentA > div.right > div.list14 > ul:nth-child(1) > li:nth-child(4) > a"));  
         System.out.println(more.getText());
         /*  more.click();  
         Thread.sleep(5000); // Let the user actually see something!  
         List<WebElement> names = driver.findElements(By.cssSelector("table tbody tr td h4 a"));  
         for(WebElement e : names) {  
             System.out.println(e.getText());  */
         
        // }  
        // Thread.sleep(5000); // Let the user actually see something!  
         //driver.quit();  
    	 /*  WebDriver driver = new ChromeDriver();  
         ChromeOptions options = new ChromeOptions();
         options.addArguments("--start-maximized");
         driver.get("http://www.sina.com");  
       //  Thread.sleep(5000);  
         WebElement more = driver.findElement(By.cssSelector("p.showmore a"));  
         more.click();  
         Thread.sleep(5000); // Let the user actually see something!  
         List<WebElement> names = driver.findElements(By.cssSelector("table tbody tr td h4 a"));  
         for(WebElement e : names) {  
             System.out.println(e.getText());  
         }  
         Thread.sleep(5000); // Let the user actually see something!  
         driver.quit();  
    	 /*
         //等待数据加载的时间
         //为了防止服务器封锁，这里的时间要模拟人的行为，随机且不能太短
         long  waitLoadBaseTime =  3000 ;
         int  waitLoadRandomTime =  3000 ;
         Random random =  new  Random(System.currentTimeMillis());
  
         //火狐浏览器
         WebDriver driver =  new  ChromeDriver();
         //要抓取的网页
         driver.get( "http://toutiao.com/" );
  
         //等待页面动态加载完毕
         Thread.sleep(waitLoadBaseTime+random.nextInt(waitLoadRandomTime));
  
         //要加载多少页数据
         int  pages= 5 ;
         for ( int  i= 0 ; i<pages; i++) {
             //滚动加载下一页
             driver.findElement(By.className( "loadmore" )).click();
             //等待页面动态加载完毕
             Thread.sleep(waitLoadBaseTime+random.nextInt(waitLoadRandomTime));
         }
  
         //输出内容
         //找到标题元素
         List<WebElement> elements = driver.findElements(By.className( "title" ));
         int  j= 1 ;
         for ( int  i= 0 ;i<elements.size();i++) {
             try  {
                 WebElement element = elements.get(i).findElement(By.tagName( "a" ));
                 //输出标题
                 System.out.println((j++) +  "、"  + element.getText() +  " "  + element.getAttribute( "href" ));
             } catch  (Exception e){
                 System.out.println( "ignore " +elements.get(i).getText()+ " because " +e.getMessage());
             }
         }
  
         //关闭浏览器
         driver.close();*/
     }
}