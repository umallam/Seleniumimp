
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import com.opera.operadriver.*;

public class WebConnector {
	
	Logger Application_Logs = Logger.getLogger("devpinoyLogger");
	
	WebDriver driver=null;
	WebDriver chrome =null;
	WebDriver firefox=null;
	WebDriver ie = null;
	WebDriver opera=null;
	WebDriver htmlunit =null;
	
	static WebConnector webcon;
		
	Properties config = null;
	Properties OR =null;
	
	public WebConnector(){
		
		if (config==null){
			try{
				config =new Properties();
				FileInputStream configfile = new FileInputStream (System.getProperty("user.dir")+"\\src\\main\\java\\com\\flight\\config\\config.properties");
				config.load(configfile);
				
				OR =new Properties();
				FileInputStream orfile =new FileInputStream (System.getProperty("user.dir")+"\\src\\main\\java\\com\\flight\\config\\OR.properties");
				OR.load(orfile);
			}
			catch (Exception e){
				System.out.println("The error is ");
				e.printStackTrace();
			}	
		}
	}
	
	public void log(String msg){
		Application_Logs.debug(msg);
	}
	public void openbrowser(){
		String browsername=config.getProperty("BrowserName");
		openbrowser(browsername);		
	}
	
	public void openbrowser(String browsername){
		
		if(browsername.equalsIgnoreCase("firefox") && firefox==null)
		  {
		   driver = new FirefoxDriver();
		   firefox=driver;
		  }
		  else if((browsername.toLowerCase()).matches("ie(.*)") && (ie==null))
		  {
		// Update the driver path with your location
		   System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//src//resources//java//IEDriverServer.exe");
		   driver = new InternetExplorerDriver();
		   ie=driver;
		  }

		  else if((browsername.toLowerCase()).matches("chrome") && chrome==null)
		  {
		// Update the driver path with your location
		   System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//resources//java//chromedriver.exe");
		   driver = new ChromeDriver();
		   chrome=driver;
		  /*}else if((browsername.toLowerCase()).matches("opera") && opera==null)
			  {
			// Update the driver path with your location
			  // System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//resources//java//chromedriver.exe");
			   driver = new OperaDriver();
			   opera=driver;
			   */
		  }else{
			  driver = new HtmlUnitDriver(true);
			  htmlunit=driver;
		  }
		
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	}
	
	public static WebConnector getInstance(){
		if(webcon==null)
			webcon= new WebConnector();
		
		return webcon;
	}
	
	public void openURL(String URL){
		log("Opening the URL "+URL);
		driver.get(URL);
	}
	
	public void click (String objectname){
		log("Clicking on Object: "+objectname);
		driver.findElement(By.cssSelector(OR.getProperty(objectname))).click();
	}
	
	public boolean isLoggedin(){
		
		if (isElementPresent(config.getProperty("loginconfirm"))){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void select (String text,String objectname){
		log("Selecting "+text+" in Object: "+objectname);
		List <WebElement> we =driver.findElements(By.cssSelector(OR.getProperty(objectname)));
		
		for (WebElement element:we){
			if (element.getText().toLowerCase().matches(text.toLowerCase())){
				element.click();
			}
		}
	}
	
	public void type (String text,String objectname){
		driver.findElement(By.cssSelector(OR.getProperty(objectname))).sendKeys(text);
	}
	public boolean isElementPresent(String objectname){
		log("Finding the Element : "+objectname);
		int count =driver.findElements(By.cssSelector(OR.getProperty(objectname))).size();
		if (count==0){
			return false;
		}
		else{
			return true;
		}	
	}
			

}
