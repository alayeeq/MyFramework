package com.MyFramework_1.testcases;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyFramework_1.pages.*;
import com.MyFramework_1baseclasses.TestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
public class Login_till_ProdSelectionPage extends TestBase{

	public static ExtentTest test;
	public static ExtentHtmlReporter Hreporter;
	public static ExtentReports report;
	LoginPOM loginPOM;
	LaunchPOM launchPOM;
	RecommendedProductPOM recommendedProductPOM;
	public Logger logger; 
	public Actions act;
	
	@BeforeTest
	
	public void Setup() {
		/*Hreporter = new ExtentHtmlReporter(CurrentDirectory+ "/test-output/my2.html");
		Hreporter.config().setDocumentTitle("MyFramework_1 Report");
		Hreporter.config().setReportName("First Report");
			
		report = new ExtentReports();	
		report.attachReporter(Hreporter);
		report.setSystemInfo("Environemnt", "QA");
		report.setSystemInfo("Tester Name","Asif" );
		report.setSystemInfo("Browser","Chrome" );*/
		logger = Logger.getLogger("LandingPage_TC");
		PropertyConfigurator.configure("log4j.properties");
		//ExtentReportsetup();
		
	}
	
	
	@BeforeClass
	 
	public void setup() {
		initializaiton();
		loginPOM = new LoginPOM(driver);
		launchPOM = new LaunchPOM(driver);
		recommendedProductPOM = new RecommendedProductPOM(driver);
		act = new Actions(driver);
	}
	
	/*@AfterClass (alwaysRun = true)
	public void teardown(ITestResult tr) throws IOException {
		
		//CallExtentReport("ITestResult tr");
		
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
		
		driver.quit();
	}*/
	
	
	@Test (priority = 1)
	 public void LoginLinkTest() throws InterruptedException {
		loginPOM.UserID.sendKeys(prop.getProperty("UID"));
		logger.info("UserID is enterrerd");
		Thread.sleep(2000);
		loginPOM.Pwd.sendKeys(prop.getProperty("PWD"));
		logger.info("Pwd is enterrerd");
		loginPOM.LoginButton.click();
		logger.info("LoginButton is clicked");
		//screenshot("LoginLinkTest");
		
		//test = report.createTest("LoginLinkTest");
		
	}
	
	
	@Test (priority = 2)
	 public void LaunchBOUT() throws InterruptedException {
		
		Thread.sleep(50000);
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Sleep is complete");
		launchPOM.LaunchButton.click();
		logger.info("Launch button is clicked");

		//screenshot("LaunchBOUT");
		
		
	}
	
	@Test (priority = 3)
	 public void selectRecomProd() throws InterruptedException {
		
		//Thread.sleep(10000);
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		String HomeWindow = driver.getWindowHandle();
		Set <String> Windows = driver.getWindowHandles();
		
		Iterator<String> itr = Windows.iterator();
		
		while (itr.hasNext())
		{
			String wind =  itr.next();
			
			if(!HomeWindow.equals(wind))
				
			{
				driver.switchTo().window(wind);
			}
		}
		System.out.println("sleep2 is executed");
		
		//act.moveToElement(recommendedProductPOM.RECPROD).click();
		Thread.sleep(5000);
		recommendedProductPOM.RECPROD.click();
		logger.info("RECPROD button is clicked");
		//screenshot("selectRecomProd");
	}
	
	
	@Test (priority = 4)
	public void Product_addition() throws InterruptedException {
		
		ProductPagePOM p1=new ProductPagePOM(driver);
		String r1[][]=p1.xlread();

		System.out.println(r1.length);
		
		System.out.println(r1[0][0]);
		System.out.println(r1[1][0]);
/*		System.out.println(r1[2][0]);
		System.out.println(r1[3][0]);
		System.out.println(r1[4][0]);*/
		
		driver.manage().window().maximize();
		Thread.sleep(5000);
		for (int row=1;row<r1.length;row++)//Employee
		{
			for (int col=11;col<21;col++)//Column
				{
					System.out.println("EE "+row+" product "+col+" started for product -->"+(r1[row][col]));
					try {
					if((!(r1[row][col].isEmpty())))
					{
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Thread.sleep(5000);
						p1.addProdCart(driver, r1[row][col]);
					}
					else {
						System.out.println("blank break");
						break;
					}
					}catch(NullPointerException e)
					{
						System.out.println("blank break");	
						break;
					}
					System.out.println("EE "+row+" product "+col+" ended for product -->"+(r1[row][col]));
				}
			//checkout to be added.
		}
		
	}
	
}
