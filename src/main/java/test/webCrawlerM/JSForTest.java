package test.webCrawlerM;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class JSForTest {
	 public  static  void  main(String[] args){
		 System.setProperty("webdriver.chrome.driver","C:\\Users\\Forcast1\\Downloads\\chromedriver_win32\\chromedriver.exe");
    	 WebDriver driver = new ChromeDriver();  
    	 driver.get("http://roll.ent.qq.com/");
    	 List<WebElement>  more2=new ArrayList<WebElement>();
    	 WebElement more;
    	 List<WebElement> lw=new ArrayList<WebElement>();
    	 int i=0;
    	 while(true){
             
             //stmt.close();
             //conn.close();
         //	System.out.println(i++);
        
         	
           //more = driver.findElement(By.xpath("//*[@id=\"instantPanel\"]/div[9]/a[8]"));//*[@id="instantPanel"]/div[9]/a[9]
          
          
          lw=driver.findElements(By.xpath("//div[@id=\"artContainer\"]/ul/li/a"));//从数据库获取xpath
        //*[@id="artContainer"]/ul[8]/li[1]/a  /ul/li[1]/a //*[@id="artContainer"]/ul[1]/li[2]/a
        
 	try{
 		  for(WebElement element:lw){
 	         	System.out.println(element.getText());//获取到链接，添加数据库操作
 	         }
         	more2=driver.findElements(By.className("f12"));//直接获取的classname
         	more=more2.get(1);
         	System.out.println(more.getText());
         	
         	}
         	catch(Exception e){
         		more=more2.get(0);
         		
         	}
          
          try {
        	  
        	  
        	  more.click();
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
    	 }
	 }
}
