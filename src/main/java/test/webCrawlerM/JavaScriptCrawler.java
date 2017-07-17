package test.webCrawlerM;

import  org.openqa.selenium.By;
import  org.openqa.selenium.WebDriver;
import  org.openqa.selenium.WebElement;
import  org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import  java.util.List;
import  java.util.Random;
  

public  class  JavaScriptCrawler {
	public static Connection connectToMysql(String database,String user,String password){
		try{
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        }catch(ClassNotFoundException e1){
            System.out.println("找不到MySQL驱动!");
            e1.printStackTrace();
            //return false;
        }
        
        String url="jdbc:mysql://localhost:3306/"+database;    //JDBC的URL    
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn=null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            System.out.print("成功连接到数据库！");
            //创建一个Statement对象
        
        } catch (SQLException e){
            e.printStackTrace();
        }
		return conn;
	}
	
	
     public  static  void  main(String[] args)  throws  Exception{
    
    	 System.setProperty("webdriver.chrome.driver","C:\\Users\\Forcast1\\Downloads\\chromedriver_win32\\chromedriver.exe");
    	 WebDriver driver = new ChromeDriver();  
       //  driver.get("http://news.163.com/latest/");  
         int a=1;
         String sql="";
         String channel_url="";
         String next_url="";
         String url_xpath="";
        // Thread.sleep(5000);  
         Connection conn=connectToMysql("storm","root","hd951113");
         Statement stmt = conn.createStatement(); //创建Statement对象
         
     
         WebElement more;
         int i=0;
    while(true){
    	sql = "select * from storm where id="+a;
        a++;
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
        	channel_url=rs.getString(4);
        	next_url = rs.getString(5);
        	url_xpath = rs.getString(7);
        	
        	
        }
        driver.get(channel_url);
        // while(true){
        while(true){
            
            //stmt.close();
            //conn.close();
        	System.out.println(i++);
        	try{
        	more=driver.findElement(By.linkText("下一页"));//直接获取的classname
        	}
        	catch(Exception e){
        		continue;
        	}
        	
          //more = driver.findElement(By.xpath("//*[@id=\"instantPanel\"]/div[9]/a[8]"));//*[@id="instantPanel"]/div[9]/a[9]
         more.click();
         List<WebElement> lw=new ArrayList<WebElement>();
         lw=driver.findElements(By.xpath(url_xpath));//从数据库获取xpath
       //*[@id="artContainer"]/ul[8]/li[1]/a  /ul/li[1]/a //*[@id="artContainer"]/ul[1]/li[2]/a
         for(WebElement element:lw){
        	 System.out.println(element.getAttribute("href"));//获取到链接，添加数据库操作
         }}
        
     
    }
    
    }
}