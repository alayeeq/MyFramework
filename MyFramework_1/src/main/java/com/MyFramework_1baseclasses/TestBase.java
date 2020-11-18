package com.MyFramework_1baseclasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.testng.ITestResult;
import org.openqa.selenium.support.ui.WebDriverWait;

/*import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;*/

import com.google.common.io.Files;

public class TestBase {
	
	public static WebDriver driver;
	public static TakesScreenshot TS;
	public static Properties prop;
	public static WebDriverWait wait;
	protected final static  String CurrentDirectory = System.getProperty("user.dir");
	public static Logger logger;
	public static WebDriverWait  webDriverWait;
	/*public static ExtentTest test;
	public static ExtentHtmlReporter Hreporter;
	public static ExtentReports report;*/
	
	
	
	
	
	
	
	
	/*try {
		prop = new Properties();
	FileInputStream ip = new FileInputStream("CurrentDirectory+/src/main/java/com/MyFramework_1/config/config.properties");
	prop.load(ip);
	}
	catch (FileNotFoundException e) {
	e.printStackTrace();
	}
	catch (IOException e) {
		e.printStackTrace();
	}*/
		
		public TestBase()  {
			
	    try {		
		FileInputStream ip = new FileInputStream(CurrentDirectory +"/Config/config.properties");
		prop = new Properties();
		prop.load(ip);
	    }
	    catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    }
			catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	
		@SuppressWarnings("deprecation")
		public static void initializaiton() {
			
			String Browsername = prop.getProperty("browser");
			String BaseURL = prop.getProperty("URL");
			
			System.out.println("Browser Selected is -->"+Browsername);
			
			if(Browsername.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", CurrentDirectory + "\\Driver2\\chromedriver.exe");
				System.out.println("Chrome Browser Initiated ");
				driver = new ChromeDriver();
			}
			else if(Browsername.equalsIgnoreCase("FireFox")) 
			{
				
				System.setProperty("webdriver.gecko.driver", CurrentDirectory + "\\Driver2\\GeckoDriver.exe");
				System.out.println("Firefox Browser Initiated ");
		        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		        capabilities.setCapability("marionette", true);
		        driver = new FirefoxDriver(capabilities);
			}
			else if(Browsername.equalsIgnoreCase("IE")) 
			{
				
				//System.setProperty("webdriver.ie.driver", "\\Driver2\\IEDriverServer.exe");
				System.out.println("IE Browser Initiated ");
				System.setProperty("webdriver.ie.driver","C:\\Users\\NagarajaK\\Desktop\\LP4\\SleniumIDE\\IE Driver_Win32\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
			
			
			driver.get(BaseURL);
			logger = Logger.getLogger("Login_till_ProdSelectionPage");
			PropertyConfigurator.configure("log4j.properties");
			
			webDriverWait = new WebDriverWait(driver, 30);
		}
		public static String screenshot(String Filename)   {
			String destination ="C:\\Users\\AsifZoya\\Documents\\Asif\\Java\\FrameWork_1\\Screenshots\\" +"\\1\\"  +Filename+ "\\.jpg";
			TS = (TakesScreenshot)driver;
			File file = TS.getScreenshotAs(OutputType.FILE);
		try {
			
			//File finaldestination = new File(destination);
			Files.copy(file, new File(destination));
			//Files.copy(file, new File(CurrentDirectory + "\\Test-Outputs\\"+"1" +Filename+ ".jpg"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return destination;
	
		
		}
		
		public static WebElement wait_base (String xpath) {
			
			
			WebElement returned_waitElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			
			return returned_waitElement;
			
		}
		

		public static WebElement wait_base_click (String xpath) {
			
			
			WebElement returned_waitElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			
			return returned_waitElement;
			
		}
		/*public static void ExtentReportsetup() {
			
			Hreporter = new ExtentHtmlReporter(CurrentDirectory+ "/test-output/my.html");
			Hreporter.config().setDocumentTitle("MyFramework_1 Report");
			Hreporter.config().setReportName("First Report");
				
			report = new ExtentReports();	
			report.attachReporter(Hreporter);
			report.setSystemInfo("Environemnt", "QA");
			report.setSystemInfo("Tester Name","Asif" );
			report.setSystemInfo("Browser","Chrome" );
			
		}*/
		
		
		/*public static void CallExtentReport(ITestResult tr) {
			if(tr.getStatus()==ITestResult.FAILURE) {
				
				
				test.log(Status.FAIL, "Test Case Failed is" + tr.getName());
				test.log(Status.FAIL, "Test case Failed is" + tr.getThrowable());
				String Screenpath = screenshot(tr.getName());
				test.addScreenCaptureFromPath(Screenpath);
				}
				
				else if (tr.getStatus()==ITestResult.SKIP) {
					test.log(Status.SKIP, "Test Case skipped is" + tr.getName());
				}
				
				else if (tr.getStatus()==ITestResult.SUCCESS) {
					test.log(Status.SKIP, "Test Case Passed is" + tr.getName());
				}
				
				report.flush();
		}*/
				
			
			
			
			
		}

			
					
			
		
		
	
	

	
	
