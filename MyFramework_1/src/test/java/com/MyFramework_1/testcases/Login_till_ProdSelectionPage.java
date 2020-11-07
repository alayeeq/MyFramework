package com.MyFramework_1.testcases;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		WebElement w =wait.until(ExpectedConditions.visibilityOf(loginPOM.UserID));
		w.sendKeys(prop.getProperty("UID"));
		logger.info("UserID is enterrerd");
		//Thread.sleep(2000);
		
		
		
		loginPOM.Pwd.sendKeys(prop.getProperty("PWD"));
		logger.info("Pwd is enterrerd");
		loginPOM.LoginButton.click();
		logger.info("LoginButton is clicked");
		//screenshot("LoginLinkTest");
		
		//test = report.createTest("LoginLinkTest");
		
	}
	
	
	@Test (priority = 2)
	 public void LaunchBOUT() throws InterruptedException {
		

		Thread.sleep(25000);
		driver.manage().window().maximize();
		driver.switchTo().defaultContent();
		//wait.until(ExpectedConditions.elementToBeSelected(launchPOM.LaunchButton));
			
		System.out.println("Loginbutton is Visible");

		launchPOM.LaunchButton.click();
		logger.info("Launch button is clicked");

		//screenshot("LaunchBOUT");
		
		
	}
	
	@Test (priority = 3)
	 public void selectRecomProd() throws InterruptedException {
		
		Thread.sleep(10000);
		//driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
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
		//WebElement w2=wait.until(ExpectedConditions.visibilityOf(recommendedProductPOM.RECPROD));
		//System.out.println("Sleep is complete");
		recommendedProductPOM.RECPROD.click();
		//recommendedProductPOM.RECPROD.click();
		logger.info("RECPROD button is clicked");
		//screenshot("selectRecomProd");
	}
	
	
	@Test (priority = 4)
	public void ipConsolidation() throws InterruptedException {
		
		inputConsolPOM bl=new inputConsolPOM(driver);
		
		
		bl.initPath();
		//bl.clearTD();
		String[] ipFiles=bl.getIPFiles();
		bl.WeedOut(ipFiles);
		bl.xlwrite();
		
	}
	
	@Test (priority = 5)
	public void Product_addition() throws InterruptedException {
		
		ProductPagePOM p1=new ProductPagePOM(driver);
		inputConsolPOM bl=new inputConsolPOM(driver);
		
		String r1[][]=p1.xlread();

		System.out.println(r1.length);
		
		driver.manage().window().maximize();
		Thread.sleep(5000);
		int user_counter=0;//user_counter
		int item_counter=0;//item_counter
		for (int row=1;row<r1.length;row++)//Employee	
		{
			++user_counter;
			for (int col=11;col<21;col++)//Column
				{
					System.out.println("EE "+row+" product "+col+" started for product -->"+(r1[row][col]));
					try {
					if((!(r1[row][col].isEmpty())))
					{
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Thread.sleep(5000);
						p1.addProdCart(driver, r1[row][col]);
						item_counter++;
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
			System.out.println(r1[row][25]);
			System.out.println(r1[row][26]);
			System.out.println(r1[row][25]);
			
			
			//Devesh_ checkout page
			System.out.println("total no of items: " + item_counter);
			driver.findElement(By.xpath("//*[@id=\"shoppingCart\"]/div/div/div[1]/button")).click();//click on cart button
			driver.findElement(By.xpath("//*[@id=\"shopping-cart-submit-button\"]")).click();//click on checkout button
			//Thread.sleep(15000);
			//driver.switchTo().defaultContent();
			System.out.println("--------------------------entered checkout page-------------------------------");
			
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"gbsection\"]/div[1]/gb-action-bar/div/div/div/div[2]/div[1]/div[2]/div/button[2]")));
			System.out.println("--------------------------wait completed -------------------------------");
			for(int i=1;i<=item_counter;i++)
				{
					System.out.println("--------------------------entered checkout page for loop for item " +i+ "-------------------------------");
					try
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='section' and @class='item line-item-container-\"+i+\"']")));
						//SHI supplier policy warning check
							if(driver.findElement(By.xpath("//div[@id='section' and @class='item line-item-container-"+i+"']//child::span[.='SHI Supplier Policy']")).isDisplayed())
								{
								System.out.println("--------------------------entered supplier check warning check-------------------------------");
								
								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::div[@class='field-dropdown dropdown']/button[@aria-label='  Required' and @class='dropdown-toggle invalid']")).click();
								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::a[normalize-space(.)=\"I confirm that I have shared phone number in 'Comments' section with the supplier\"]")).click();
								}
							else
								{}
								
							//item expansion check
								if(driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']/div[2]/div[1]/button/i[@class='icon-slim-arrow-down']")).isDisplayed())
								{
									System.out.println("--------------------------entered item expansion check (iF)-------------------------------");
								}
								else {
									//Expand the item 
									System.out.println("--------------------------entered item expansion check (else)-------------------------------");
									driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']/div[2]/div[1]/button/i")).click();	
								}
								
								//shipping address expansion check
								if(driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']/div[3]/div[3]/ng-toggle/button/span/i[@class='icon-slim-arrow-down']")).isDisplayed())
								{
									System.out.println("--------------------------entered shipping address expansion check (iF)-------------------------------");
								}
								else {
									//Expand the item 
									System.out.println("--------------------------entered shipping address expansion check (else)-------------------------------");
									driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']/div[3]/div[3]/ng-toggle/button/span/span[text()='Shipping']")).click();	
								}
								
							
							//address check 
								System.out.println("--------------------------entered address check-------------------------------");
								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::div[1]/button[@type='button' and @class='dropdown-chooser dropdown-toggle invalid']")).click();
								Boolean isaddress_Present;
								try
								{
									isaddress_Present = driver.findElements(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::a[normalize-space(.)='\"+r1[user_counter][4]+\"']")).size()>0;
									System.out.println("--------------------------value of address_present: " +isaddress_Present+ "-------------------------------");
								}
								catch(Throwable e)
								{}
								//if(driver.findElement(By.xpath("//*[@id=\"LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo\"]/div[1]/div[1]/ng-include/div[1]/ul/li[4]/div/a[text()='"+r1[user_counter][4]+"']")) !=null)
								if(isaddress_Present = false)
								{	
									
									System.out.println("--------------------------address present-------------------------------");
									driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::a[normalize-space(.)='"+r1[user_counter][4]+"']")).click();//Click existing address
								Thread.sleep(10000);
								System.out.println("Item" +i+ " is done");
								}
								else
								{
									
								//create new address
									System.out.println("--------------------------address not present, create new one-------------------------------");
									//driver.findElement(By.xpath("//*[@id=\"LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo\"]/div[1]/div[1]/ng-include/div[1]")).click();//click the dropped down
									//System.out.println("--------------------------clicked address dropdown-------------------------------");
									driver.findElement(By.xpath("//*[@id=\"LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo\"]/div[1]/div[1]/ng-include/div[1]/ul/li[6]/div/a[contains(text(),'Browse')]")).click();//Click browse all
									//Thread.sleep(5000);
								
								
								//System.out.println("new address sleep - 1 ");
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='chooser_modalLineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo']/div/div/div/div[1]/h3")));
								System.out.println("wait for new address list pop up ");
								driver.findElement(By.xpath("//*[@id='chooser_modalLineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo']/div/div/div/div[2]/div/div[1]/button")).click();//Ship to address---> Click new
								
								//Thread.sleep(3000);
								//System.out.println("new address sleep - 2 ");
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='full-name']")));
								System.out.println("wait for new address form to populate");
								
								driver.switchTo().defaultContent();
								driver.findElement(By.xpath("//*[@id='full-name']")).sendKeys(r1[user_counter][4]);//name
								driver.findElement(By.xpath("//*[@id='streetline1']")).sendKeys(r1[user_counter][5]);//streetline1
								driver.findElement(By.xpath("//*[@id='streetline2']")).sendKeys(r1[user_counter][6]);//streetline2
								//driver.findElement(By.xpath("//*[@id=\"streetline3\"]")).sendKeys(r1[user_counter][4]);//streetline3
								driver.findElement(By.xpath("//*[@id='city']")).sendKeys(r1[user_counter][7]);//city
								driver.findElement(By.xpath("//*[@id='region']")).sendKeys(r1[user_counter][8]);//region
								driver.findElement(By.xpath("//*[@id='postal-code']")).sendKeys(r1[user_counter][9]);//postal-code
								//driver.findElement(By.xpath("//select[@id=\"country\"]/option[235]"));//country
								Select select = new Select(driver.findElement(By.id("country")));
								select.selectByVisibleText("United States");//country
								
								driver.findElement(By.xpath("//*[@id=\"phone\"]")).sendKeys(r1[user_counter][10]);//phone
								driver.findElement(By.xpath("//*[@id=\"fax\"]")).sendKeys(r1[user_counter][11]); //FAX
								driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(r1[user_counter][12]);//email
								
								driver.findElement(By.xpath("//*[@id=\"addressForm\"]/div[12]/button[2]")).click();
								Thread.sleep(10000);
								System.out.println("Item " +i+ " is done");
								}
						}
					catch(NullPointerException e)
					{
					
					}
				
				}
			driver.findElement(By.xpath("//*[@id='header-comments-comment']/div/div[1]/textarea")).sendKeys("My Phone number is: +1 "+ r1[user_counter][10]);
			driver.findElement(By.xpath("//*[@id=\"checkbox-header-comments\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"header-comments-commentButton\"]")).click();
			System.out.println("Comment added for User"+ user_counter);
			Thread.sleep(5000);
			
			
			if(driver.findElement(By.xpath("//*[@id='gbsection']/div[1]/gb-action-bar/div/div/div/div[2]/div[1]/div[2]/div/button[text()='Submit']")).isEnabled())
			{
				//driver.findElement(By.xpath("//*[@id='gbsection']/div[1]/gb-action-bar/div/div/div/div[2]/div[1]/div[2]/div/button[text()='Submit']")).click();
			}
			
			else
			{
				for(int i=1;i<=item_counter;i++)
				{
				driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-1']/div[2]/div[5]/div/button/div[@class='vertical-dots']")).click();
				driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-1']//child::a[contains(text(),'Remove')]")).click();
				}
			}
			
			System.out.println("Request Submitted for User"+ user_counter);
		}
	
		//Archieving TD sheet with Timestamp
		//bl.archieveTD();
	}
	
	
	
	
}
