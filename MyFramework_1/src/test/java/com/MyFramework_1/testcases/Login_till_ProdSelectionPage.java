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
//import static com.MyFramework_1baseclasses.TestBase.*;

public class Login_till_ProdSelectionPage extends TestBase {
	

	public static ExtentTest test;
	public static ExtentHtmlReporter Hreporter;
	public static ExtentReports report;
	LoginPOM loginPOM;
	LaunchPOM launchPOM;
	RecommendedProductPOM recommendedProductPOM;
	//public Logger logger;
	boolean criticalFlag=true;
	public Actions act;
	
	@BeforeTest
	
	public void Setup() {

		

		
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
		
		
	}*/
	
	@Test (priority = 1)
	 public void LoginLinkTest() throws InterruptedException {
		
		System.out.println("Login is Visible");
		Thread.sleep(10000);
		//WebElement w =wait.until(ExpectedConditions.visibilityOf(loginPOM.UserID));
		//w.sendKeys(prop.getProperty("UID"));
		loginPOM.UserID.sendKeys(prop.getProperty("UID"));
		logger.info("UserID is enterrerd");
		//Thread.sleep(2000);
		
		
		
		loginPOM.Pwd.sendKeys(prop.getProperty("PWD"));
		logger.info("Pwd is enterrerd");
		loginPOM.LoginButton.click();
		logger.info("LoginButton is clicked");
		//screenshot("LoginLinkTest");
		
		//test = report.createTest("LoginLinkTest");
		
	}
	
	
	@Test (priority = 2,enabled = false)
	 public void LaunchBOUT() throws InterruptedException {
		ProductPagePOM p1=new ProductPagePOM(driver);

		Thread.sleep(50000);
		driver.manage().window().maximize();
		driver.switchTo().defaultContent();
		//wait.until(ExpectedConditions.elementToBeSelected(launchPOM.LaunchButton));
			
		System.out.println("Loginbutton is Visible");
		//p1.waitMod(driver, "//a[@id='lunchbutton']");
		
		launchPOM.LaunchButton.click();
		logger.info("Launch button is clicked");
		//screenshot("LaunchBOUT");
		 
	}
	
	@Test (priority = 3,enabled=false)
	 public void selectRecomProd() throws InterruptedException {
		ProductPagePOM p1=new ProductPagePOM(driver);
		
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
		p1.waitMod(driver, "//*[@id=\"popular\"]/div/div/div[2]/div/div[1]/img");
		recommendedProductPOM.RECPROD.click();
		//recommendedProductPOM.RECPROD.click();
		logger.info("RECPROD button is clicked");
		//screenshot("selectRecomProd");
	}
	
	
	@Test (priority = 4)
	public void ipConsolidation() throws InterruptedException {
		
		inputConsolPOM bl=new inputConsolPOM(driver);
		ProductPagePOM p1=new ProductPagePOM(driver);
		
		Thread.sleep(10000);
		bl.initPath();
		bl.clearTD();
		String[] ipFiles=bl.getIPFiles();
		bl.WeedOut(ipFiles);
		bl.xlwrite();
		
	}
	
	@Test (priority = 5, enabled=false)
	public void Product_addition() throws InterruptedException {
		
		ProductPagePOM p1=new ProductPagePOM(driver);
		inputConsolPOM bl=new inputConsolPOM(driver);
		
		driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
		String genericProdXpth="//div[@class='product-name']";
		p1.waitMod(driver, genericProdXpth);
		
		bl.initPath();
		String r1[][]=p1.xlread();
		logger.info(r1.length);
		driver.manage().window().maximize();
		Thread.sleep(5000);
		int user_counter=0;//user_counter 
		
		
		Employee_loop:
		for (int row=1;row<r1.length;row++)//Employee	
		{
			
			logger.info("EE "+row+" Started"); 
			Thread.sleep(3000);
			boolean flag=true;
			
			++user_counter;
			int i=0; //Item container counter
			
			int item_counter=0;//item_counter
			
			//chck cart and if any item exist empty it
			flag=p1.chckCart(driver);
			
			Product_Loop:
			for (int col=11;col<21;col++)//Column
				{
					logger.info("EE "+row+" product "+col+" started for product -->"+(r1[row][col]));
					try {
					if((!(r1[row][col].isEmpty())))
					{
						Thread.sleep(5000);
						flag=p1.addProdCart(driver, r1[row][col]);
						
						if(!(flag))
						{
							logger.info("invalid Product idetified during product selection :"+r1[row][col]);
							bl.statusUpdate(row, "Invalid Product exist");
							/*if(!p1.chckCart(driver));
							{
								System.out.println("Unalbe to empty cart");
							}*/
							
							continue Employee_loop;
						}
						item_counter++;
					}
					else {
						logger.info("blank break");
						break;
					}
					}catch(NullPointerException e)
					{
						logger.info("null pointer break");	
						break;
					}
					logger.info("EE "+row+" product "+col+" ended for product -->"+(r1[row][col]));
				}
			
			//checkout to be added.
			
/*			if(false)
			{
*/			//Devesh_ checkout page
			System.out.println("total no of items: " + item_counter);
			driver.findElement(By.xpath("//*[@id=\"shoppingCart\"]/div/div/div[1]/button")).click();//click on cart button
			driver.findElement(By.xpath("//*[@id=\"shopping-cart-submit-button\"]")).click();//click on checkout button
			//Thread.sleep(15000);
			//driver.switchTo().defaultContent();
			System.out.println("--------------------------entered checkout page-------------------------------");
			
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"gbsection\"]/div[1]/gb-action-bar/div/div/div/div[2]/div[1]/div[2]/div/button[2]")));
			System.out.println("--------------------------wait completed, checkout page element located -------------------------------");
			
		//Total amount > $150.00 check
			String total_amount_full = driver.findElement(By.xpath("//*[@id='gbsection']//child::span[@class='money-amount']")).getText();
			System.out.println("--------------------------Total Amount full : "+total_amount_full+"-------------------------------");
			String total_amount = total_amount_full.substring(0, total_amount_full.length()-4);
			System.out.println("--------------------------Total Amount : "+total_amount+"-------------------------------");
			String final_amount = total_amount.substring(1,total_amount.length());
			System.out.println("--------------------------Final Amount : "+final_amount+"-------------------------------");
			float tamt= Float.parseFloat(final_amount);
			System.out.println("--------------------------Final Amount (in float) : "+tamt+"-------------------------------");
				if(tamt>150.00)
				{
					//go for next employee
					bl.statusUpdate(row, "Declined -- Total amount greater than $150.00");
					System.out.println("--------------------------Declined -- Total amount greater than $150.00-------------------------------");
					if(row!=(r1.length-1))
					{
						System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(IF)-------------------------------");
					driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
					Thread.sleep(5000);
					driver.switchTo().defaultContent();
					continue Employee_loop;
					}
					else
					{ 
						System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(else)-------------------------------");
						break Employee_loop;
					}
				
				}
			
			//Item check loop
			for(i=1;i<=item_counter;i++)
				{
					System.out.println("--------------------------entered checkout page for loop for item " +i+ "-------------------------------");
					try
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']")));
						
						//Item warning check
								Boolean multi_warning_Present=false;
								try
								{
									multi_warning_Present = driver.findElements(By.xpath("//*//div[@id='section' and @class='item line-item-container-1']//child::span[@class='multiple-policies-text' and text()='This item has or requires multiple justifications']")).size()>0;
										System.out.println("--------------------------Multiple warnings present : " +multi_warning_Present+ "-------------------------------");
								}
								catch(Throwable e)
								{}
								Boolean shi_warning_Present=false;
								try
								{
									shi_warning_Present = driver.findElements(By.xpath("//div[@id='section' and @class='item line-item-container-"+i+"']//child::span[.='SHI Supplier Policy']")).size()>0;
										System.out.println("--------------------------SHI warning present : " +shi_warning_Present+ "-------------------------------");
								}
								catch(Throwable e)
								{}
								
								if(multi_warning_Present==true)
								{
									bl.statusUpdate(row, "Declined -- Wrong item selected");
									System.out.println("--------------------------Declined -- Wrong item selected-------------------------------");
									if(row!=(r1.length-1))
									{
										System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(IF)-------------------------------");
									driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
									Thread.sleep(5000);
									driver.switchTo().defaultContent();
									continue Employee_loop;
									}
									else
									{ 
										System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(else)-------------------------------");
										break Employee_loop;
									}
								}
								else if(shi_warning_Present == true)
								//if(driver.findElement(By.xpath("//div[@id='section' and @class='item line-item-container-"+i+"']//child::span[.='SHI Supplier Policy']")).isDisplayed())
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
								
					//Accounting drop-down expansion check
								System.out.println("--------------------------accounting expansion check-------------------------------");
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::span[text()='Accounting']")).click();
															
						   //new accounting cost center code
								System.out.println("--------------------------Search cost center code-------------------------------");
								driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']/div[1]/div/ng-include/div[1]/button")).click();//Click Cost center drop down
								Thread.sleep(3000);
								System.out.println("--------------------------Search cost center code--clicked cost center drop down-------------------------------");
								driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']//child::a[contains(text(),'Browse')]")).click();//Click browse all
								//Thread.sleep(2000);
								System.out.println("--------------------------Search cost center code--clicked browse all-------------------------------");
						
							System.out.println("--------------------------entered cost center code search form-------------------------------");
							
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']//child::h3[text()='Cost Center']")));
							System.out.println("click on search and enter cost center code 'US0001T8' ");
							driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']//child::input[@placeholder='Search']")).click();//click on search input text area								
							driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']//child::input[@placeholder='Search']")).sendKeys("US0001T8");//enter cost center value to search
							driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']//child::form/button[@class='nav-search-right' and @aria-label='Search']")).click();//click on search button
							driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']//child::button[@aria-label='choose Cost Center US0001T8']")).click();//click on choose button
							
							Thread.sleep(5000);
							//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='section' and @class='item line-item-container-1']//child::*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']//child::button")));
							System.out.println("--------------------------cost center code selected (end)-------------------------------");
							
					//address check 
								System.out.println("--------------------------entered address check-------------------------------");
								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::div[1]/button[@type='button' and @class='dropdown-chooser dropdown-toggle invalid']")).click();
								
								Boolean isaddress_Present=false;
								try
								{
									isaddress_Present = driver.findElements(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::a[normalize-space(.)='"+r1[user_counter][4]+"']")).size()>0;
										System.out.println("--------------------------value of address_present: " +isaddress_Present+ "-------------------------------");
										
								}
								catch(Throwable e)
								{}
								//if(driver.findElement(By.xpath("//*[@id=\"LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo\"]/div[1]/div[1]/ng-include/div[1]/ul/li[4]/div/a[text()='"+r1[user_counter][4]+"']")) !=null)
								if(isaddress_Present == true)
								{	
									
									System.out.println("--------------------------address present-------------------------------");
									driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"+i+"']//child::a[normalize-space(.)='"+r1[user_counter][4]+"']")).click();//Click existing address
								Thread.sleep(10000);
								//System.out.println("--------------------------Item " +i+ " is done-------------------------------");
								
								}
								else
								{
									
						//create new address
									System.out.println("--------------------------address not present, create new one-------------------------------");
									//driver.findElement(By.xpath("//*[@id=\"LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo\"]/div[1]/div[1]/ng-include/div[1]")).click();//click the dropped down
									//System.out.println("--------------------------clicked address dropdown-------------------------------");
									driver.findElement(By.xpath("//*[@id=\"LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo\"]//child::div/a[contains(text(),'Browse')]")).click();//Click browse all
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
								driver.findElement(By.xpath("//*[@id='city']")).sendKeys(r1[user_counter][24]);//city
								driver.findElement(By.xpath("//*[@id='region']")).sendKeys(r1[user_counter][25]);//region
								driver.findElement(By.xpath("//*[@id='postal-code']")).sendKeys(r1[user_counter][26]);//postal-code
								//driver.findElement(By.xpath("//select[@id=\"country\"]/option[235]"));//country
								Select select = new Select(driver.findElement(By.id("country")));
								select.selectByVisibleText("United States");//country
								
								driver.findElement(By.xpath("//*[@id=\"phone\"]")).sendKeys(r1[user_counter][8]);//phone
								//driver.findElement(By.xpath("//*[@id=\"fax\"]")).sendKeys(r1[user_counter][11]); //FAX
								driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(r1[user_counter][7]);//email
								
								driver.findElement(By.xpath("//*[@id=\"addressForm\"]/div[12]/button[2]")).click();
								
								Boolean isaddress_Submitted=false;
								try
								{
									isaddress_Submitted = driver.findElements(By.xpath("//*[@id='chooser_modalLineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo']//child::*[@id='fieldChoice-header-notification']//child::i[@class='icon-message-warning']")).size()>0;
										System.out.println("--------------------------value of address_present: " +isaddress_Submitted+ "-------------------------------");
										
								}
								catch(Throwable e)
								{}
								
								if(isaddress_Submitted == true)
								{
									bl.statusUpdate(row, "Wrong address provided");
									System.out.println("--------------------------Wrong address provided-------------------------------");
									if(row!=(r1.length-1))
									{
										System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(IF)-------------------------------");
									driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
									Thread.sleep(5000);
									driver.switchTo().defaultContent();
									continue Employee_loop;
									}
									else
									{ 
										System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(else)-------------------------------");
										break Employee_loop;
									}
								}	
								Thread.sleep(5000);
								}
					
					//Deliver to name:
								System.out.println("--------------------------Deliver to: "+r1[user_counter][4]+ "-------------------------------");
								driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHDeliverTo']//child::input[@name='DeliverTo']")).clear();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHDeliverTo']//child::span[@id='error-code' and text()='Deliver To Attention must be set.']")));
								driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHDeliverTo']//child::input[@name='DeliverTo']")).sendKeys(r1[user_counter][4]);
								
								
							System.out.println("--------------------------Item " +i+ " is done-------------------------------");
								
						}
					catch(Throwable e)
					{
						//go for next employee
						bl.statusUpdate(row, "Wrong product added for Submission");
						System.out.println("--------------------------Wrong product added for Submission-------------------------------");
						if(row!=(r1.length-1))
						{
							System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(IF)-------------------------------");
						driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
						Thread.sleep(5000);
						driver.switchTo().defaultContent();
						continue Employee_loop;
						}
						else
						{ 
							System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(else)-------------------------------");
							break Employee_loop;
						}
					}
				
				}
			
	//Comment section
			driver.findElement(By.xpath("//*[@id='header-comments-comment']/div/div[1]/textarea")).sendKeys("My Phone number is: +1 "+ r1[user_counter][8]);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='checkbox-header-comments']")).click();
			driver.findElement(By.xpath("//*[@id='header-comments-commentButton']")).click();
			System.out.println("Comment added for User"+user_counter+" : " +r1[user_counter][4]);
			Thread.sleep(5000);
			
			
			if(driver.findElement(By.xpath("//*[@id='gbsection']/div[1]/gb-action-bar/div/div/div/div[2]/div[1]/div[2]/div/button[text()='Submit']")).isEnabled())
			{
				driver.findElement(By.xpath("//*[@id='gbsection']/div[1]/gb-action-bar/div/div/div/div[2]/div[1]/div[2]/div/button[text()='Submit']")).click();
				
				//Thread.sleep(10000);
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("(//span[text()='Success'])[3]"))));
				//check if it is submitted
				Boolean is_submitted=false;
				try
				{
					is_submitted = driver.findElements(By.xpath("(//span[text()='Success'])[3]")).size()>0;
					System.out.println("--------------------------if submission done: " +is_submitted+ "-------------------------------");
				}
				catch(Throwable e)
				{}  
				if(is_submitted == true)
				{	
					
					//driver.findElement(By.xpath("")).isDisplayed();//xpath of the element from post submit page
					System.out.println("Request Submitted for User "+user_counter+" : " +r1[user_counter][4]);
					driver.findElement(By.xpath("(//button[@translate='actionButton2_button_View_Requisition'])[3]")).click();
					WebElement element = driver.findElement(By.xpath("//div[@class='fd-action-bar__description']"));
					String elementval = element.getText();
					bl.statusUpdate(row, "Submission completed with order number: "+elementval);//write status to data file
					if(row!=(r1.length-1))
					{
						System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(IF)-------------------------------");
					driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
					Thread.sleep(5000);
					driver.switchTo().defaultContent();
					continue Employee_loop;
					}
					else
					{ 
						System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(else)-------------------------------");
						break Employee_loop;
					}
				}
				else
				{
				/*for(i=1;i<=item_counter;i++)
				{
				driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-1']/div[2]/div[5]/div/button/div[@class='vertical-dots']")).click();
				driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-1']//child::a[contains(text(),'Remove')]")).click();
				}*/
				driver.findElement(By.xpath("//*[@id='gbsection']/div[4]/gb-comment/div/div[2]/div/div[2]/ul/li/div/div[4]/span[text()='Remove']")).click();
				//go for next employee
				bl.statusUpdate(row, "Submission failed");
				if(row!=(r1.length-1))
				{
					System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(IF)-------------------------------");
				driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
				Thread.sleep(5000);
				driver.switchTo().defaultContent();
				continue Employee_loop;
				}
				else
				{ 
					System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(else)-------------------------------");
					break Employee_loop;
				}
				}
			}
			
			else
			{
				/*for(i=1;i<=item_counter;i++)
				{
				driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-1']/div[2]/div[5]/div/button/div[@class='vertical-dots']")).click();
				driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-1']//child::a[contains(text(),'Remove')]")).click();
				}*/
				driver.findElement(By.xpath("//*[@id='gbsection']/div[4]/gb-comment/div/div[2]/div/div[2]/ul/li/div/div[4]/span[text()='Remove']")).click();
				
				bl.statusUpdate(row, "Submission failed");
				if(row!=(r1.length-1))
				{
					System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(IF)-------------------------------");
				driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
				Thread.sleep(5000);
				driver.switchTo().defaultContent();
				continue Employee_loop;
				}
				else
				{ 
					System.out.println("--------------------------Row count: "+row+" and Total no of users in datasheet: "+(r1.length-1)+"--(else)-------------------------------");
					break Employee_loop;
				}
			}
		 }
/*		}*/
		
		//Archieving TD sheet with Timestamp
		//bl.archieveTD();
	}
	
}
