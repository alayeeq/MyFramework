package com.MyFramework_1.testcases;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
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
	ProductPagePOM p1 = new ProductPagePOM(driver);
	inputConsolPOM bl = new inputConsolPOM(driver);

	int global_Counter = 1;

	public static ExtentTest test;
	public static ExtentHtmlReporter Hreporter;
	public static ExtentReports report;
	public int success;
	LoginPOM loginPOM;
	LaunchPOM launchPOM;
	RecommendedProductPOM recommendedProductPOM;
	// public Logger logger;
	boolean criticalFlag = true;
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

	@AfterClass(alwaysRun = true)
	public void teardown() {
		logger.info("After class initiated");
		logger.info("Critical Flag -->"+criticalFlag);
		
		if (criticalFlag == false) {
			logger.info("Batch Restarts");
			driver.quit();
			TestRunner.main(null);
		} else {
			logger.info("Batch Completed");
			driver.quit();
			JOptionPane.showMessageDialog(null, "BatchComplete");
		}
	}

	@Test(priority = 1)
	public void LoginLinkTest() throws InterruptedException {

		if (p1.webPageChck(driver)) {
			// NK Single-Sign On method
			try {
				String signOnMethodXpth = "//span[@id='credentialSignin']";

				WebElement signOnMethodEle = wait_base(signOnMethodXpth);

				try {
					if (signOnMethodEle.isDisplayed()) {
						WebDriverWait wait1 = new WebDriverWait(driver, 30);
						signOnMethodEle = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(signOnMethodXpth)));

						signOnMethodEle.click();
						logger.info("W3 sign On Method is requested - w3id Credentials method is selected");
					}
				} catch (Exception e) {
					System.out.println("Failure in sign On method selection window setting Crtical Flag to False");
					criticalFlag = false;
				}
			} catch (Exception e) {
				logger.info("W3 sign On Method is NOT requested - Batch continues");
			}

			if (criticalFlag) {
				try{	
					System.out.println("Login is Visible");
					WebElement UID = wait_base(loginPOM.UserID);
					UID.sendKeys(prop.getProperty("UID"));
					logger.info("UserID is enterrerd");
	
					loginPOM.Pwd.sendKeys(prop.getProperty("PWD"));
					logger.info("Pwd is enterrerd");
					loginPOM.LoginButton.click();
					logger.info("LoginButton is clicked");
					// screenshot("LoginLinkTest");
					// test = report.createTest("LoginLinkTest");
				}catch(Exception e)
				{
					logger.info("Login page failure - CriticalFlag set to False to reboot");
					criticalFlag=false;
				}
				
				// NK MFA Code
				if (criticalFlag) {
					try {
						String mfaXpth = "//h3[text()='Authorize this device']";
						WebElement mfaEle = wait_base(mfaXpth);
	
						if (mfaEle.isDisplayed()) {
							logger.info("Multi Factor Autentication is requested - Wait time increased 20+ Secs");
							Thread.sleep(30000);
						}
					} catch (Exception e) {
						logger.info("Multi Factor Autentication is NOT requested - Batch continues");
					}
				}
			}
		}
	}

	@Test(priority = 2, dependsOnMethods = { "LoginLinkTest" }, enabled = false)
	public void LaunchBOUT() throws InterruptedException {

		// Optimized Wait logic
		boolean flag = true;
		int i = 1;
		while ((i < 2) && (flag)) {
			Thread.sleep(50000);
			try {
				String launchXpth = "//a[@id='lunchbutton']";
				WebElement launchEle = wait_base(launchXpth);

				if (launchEle.isDisplayed()) {
					System.out.println("BoND Site Landing Page displayed - Wait#" + i + "/6");
					flag = false;
				}
			} catch (Exception e) {
				System.out.println("Waiting for BoND Site Landing Page displayed - Wait#" + i + "/6");
				flag = true;
			}
		}

		// NK Site check
		try {
			String launchXpth = "//a[@id='lunchbutton']";
			WebElement launchEle = wait_base(launchXpth);

			if (launchEle.isDisplayed()) {
				System.out.println("BoND Site Landing Page displayed");
			}
		} catch (Exception e) {
			System.out.println("BoND Site Landing Page NOT displayed - Critical Flag Set to False Batch Skips");
			criticalFlag = false;
		}

		if (criticalFlag) {
			ProductPagePOM p1 = new ProductPagePOM(driver);

			driver.manage().window().maximize();
			driver.switchTo().defaultContent();
			// wait.until(ExpectedConditions.elementToBeSelected(launchPOM.LaunchButton));

			System.out.println("Loginbutton is Visible");
			// p1.waitMod(driver, "//a[@id='lunchbutton']");

			// WebElement LB = wait_base(launchPOM.LaunchButton);

			// WebElement LB = wait_base_click(launchPOM.LaunchButton);

			// LB.click();

			launchPOM.LaunchButton.click();
			logger.info("Launch button is clicked");
			// screenshot("LaunchBOUT");
		}
	}

	@Test(priority = 3, dependsOnMethods = { "LaunchBOUT" }, enabled = false)
	public void selectRecomProd() throws InterruptedException {

		if (criticalFlag) {
			ProductPagePOM p1 = new ProductPagePOM(driver);

			Thread.sleep(10000);
			// driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			String HomeWindow = driver.getWindowHandle();
			Set<String> Windows = driver.getWindowHandles();

			Iterator<String> itr = Windows.iterator();

			while (itr.hasNext()) {
				String wind = itr.next();

				if (!HomeWindow.equals(wind))

				{
					driver.switchTo().window(wind);
				}
			}
			System.out.println("sleep2 is executed");

			// act.moveToElement(recommendedProductPOM.RECPROD).click();
			Thread.sleep(5000);
			// WebElement
			// w2=wait.until(ExpectedConditions.visibilityOf(recommendedProductPOM.RECPROD));
			// System.out.println("Sleep is complete");
			// p1.waitMod(driver, "//*[@id=\"popular\"]/div/div/div[2]/div/div[1]/img");

			// WebElement RP = wait_base(recommendedProductPOM.RECPROD);
			// RP.click();
			recommendedProductPOM.RECPROD.click();
			// recommendedProductPOM.RECPROD.click();
			logger.info("RECPROD button is clicked");
			// screenshot("selectRecomProd");
		}
	}

	@Test(priority = 4, dependsOnMethods = { "LoginLinkTest" })
	public void ipConsolidation() throws InterruptedException {
		boolean landingPageChck = false;

		// Wait for Landing page to load max 60 Sec
		// driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		// Thread.sleep(40000);

		// NK Page Load check
		if (!(p1.webPageChck(driver))) {
			System.out.println("Error Landing Page load fails - Chck Method webPageChck - Set Critical Flag to False");
		}

		// NK Site check
		try {
			String launchXpth = "//a[@id='lunchbutton']";
			WebElement launchEle = wait_base(launchXpth);

			if (launchEle.isDisplayed()) {
				System.out.println("BoND Site Landing Page displayed");
			}
		} catch (Exception e) {
			System.out.println("BoND Site Landing Page NOT displayed - Critical Flag Set to False Batch Skips");
			criticalFlag = false;
		}

		if (criticalFlag) {
			System.out.println("MSG - IP Consolidation method initiated");

			// Navigate to Direct URL
			driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
			driver.manage().window().maximize();

			bl.initPath();
			// bl.clearTD();
			String[] ipFiles = bl.getIPFiles();
			bl.WeedOut(ipFiles);
			bl.xlwrite();
		}
	}

	@Test(priority = 5, dependsOnMethods = { "ipConsolidation" })
	public void Product_addition() throws InterruptedException {

		if (!(p1.webPageChck(driver))) {
			System.out.println("Product Page load fails - Chck Method webPageChck - Set Critical Flag to False");
			criticalFlag = false;
		}

		// NK Site check
		try {
			String recomProdXpth = "//h3[@title='Recommended Accessories']";
			WebElement recomProdEle = wait_base(recomProdXpth);

			if (recomProdEle.isDisplayed()) {
				System.out.println("Recomended Accessories Page displayed");
			}
		} catch (Exception e) {
			System.out.println("Recomended Accessories Page NOT displayed - Critical Flag Set to False Batch Skips");
			criticalFlag = false;
		}

		if (criticalFlag) {
			bl.initPath();
			String r1[][] = p1.xlread();
			int NumberofRows = r1.length - 1;
			logger.info(NumberofRows);

			// bl.nofRecords(0, "Total No records in input : " +NumberofRows);

			String status = "";
			boolean prod_flag = false;

			Employee_loop: for (int row = 1; row < r1.length; row++)// Employee
			{
				logger.info("EE " + row + " Started");
				status = bl.statusChck(row);

				if (status == null) {
					logger.info("EE " + row + " Started");
					Thread.sleep(3000);
					boolean flag = true;

					int i = 0; // Item container counter
					int item_counter = 0;// item_counter

					// chck cart and if any item exist empty it
					boolean cartChck = p1.waitMod(driver, "//button[@title='Shopping Cart']");
					if (!(cartChck)) {
						System.out.println("Page NOT loaded properly");
						criticalFlag = false;
						break;
					}
					prod_flag = p1.chckCart(driver);

				// in case of any failure with clearing product cart, quit
					if (!(prod_flag)) {
						System.out.println("Page un responsive break EE loop");
						criticalFlag = false;
						break;
					}

					Product_Loop: for (int col = 13; col < 23; col++)// Column
					{
						logger.info(
								"EE " + row + " product " + (col - 12) + " started for product -->" + (r1[row][col]));
						try {
							if ((!(r1[row][col].isEmpty()))) {
								//Thread.sleep(5000);
								boolean dummyFlag=p1.webPageChck(driver);
								flag = p1.addProdCart(driver, row,r1[row][col]);

								if (!(flag)) {
									logger.info("Product Addition Failure :" + r1[row][col]);
									//bl.statusUpdate(row, "Failed -Invalid Product exist :"+r1[row][col]);
									
									//clear existing cart products.
									if (!p1.chckCart(driver))
									{
										System.out.println("Unable to empty cart");
									}
									continue Employee_loop;
								}
								item_counter++;
							} else {
								logger.info("blank break");
								break;
							}
						} catch (NullPointerException e) {
							logger.info("null pointer break");
							break;
						}
						logger.info("EE " + row + " product " + (col - 12) + " ended for product -->" + (r1[row][col]));
					} // Product Loop ends here

					//bl.statusUpdate(row, "Product added to cart- Checkout pending");
					// checkout to be added.
					// if(false)
					//{

					boolean dummyFlag=p1.webPageChck(driver);
					
					// Devesh_ checkout page
					logger.info("total no of items: " + item_counter);
					Thread.sleep(2000);
					try {
						driver.findElement(By.xpath("//*[@id=\"shoppingCart\"]/div/div/div[1]/button")).click();// click
						driver.findElement(By.xpath("//*[@id=\"shopping-cart-submit-button\"]")).click();// click on
																											// checkout
						// Thread.sleep(15000);
						// driver.switchTo().defaultContent();
						
					} catch (ElementNotInteractableException e) {
						try {
							logger.info("Shopping cart Element Not intercatable - Batch to refresh and try again ");
							driver.navigate().refresh();
						
							dummyFlag=p1.waitMod(driver, "//*[@id=\"shoppingCart\"]/div/div/div[1]/button");
							driver.findElement(By.xpath("//*[@id=\"shoppingCart\"]/div/div/div[1]/button")).click();// click
							
							dummyFlag=p1.waitMod(driver, "//*[@id=\"shopping-cart-submit-button\"]");
							driver.findElement(By.xpath("//*[@id=\"shopping-cart-submit-button\"]")).click();//
						}catch(Exception f)
						{
							f.printStackTrace();
							logger.info(" Cart Icon Element Not intercatable - Critical Flag to set as False and Batch to Reboot ");
							criticalFlag=false;
							break;
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					logger.info("Check out Page - Critical flag check-->"+criticalFlag);
					if(criticalFlag)
					{
					logger.info("--------------------------entered checkout page-------------------------------");
					WebDriverWait wait = new WebDriverWait(driver, 60);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
							"//*[@id=\"gbsection\"]/div[1]/gb-action-bar/div/div/div/div[2]/div[1]/div[2]/div/button[2]")));
					logger.info(
							"--------------------------wait completed, checkout page element located -------------------------------");
				
					// Total amount > $150.00 check
					String total_amount_full = driver
							.findElement(By.xpath("//*[@id='gbsection']//child::span[@class='money-amount']"))
							.getText();
					logger.info("--------------------------Total Amount full : " + total_amount_full
							+ "-------------------------------");
					String total_amount = total_amount_full.substring(0, total_amount_full.length() - 4);
					logger.info("--------------------------Total Amount : " + total_amount
							+ "-------------------------------");
					String final_amount = total_amount.substring(1, total_amount.length());
					logger.info("--------------------------Final Amount : " + final_amount
							+ "-------------------------------");
					float tamt = Float.parseFloat(final_amount);
					logger.info("--------------------------Final Amount (in float) : " + tamt
							+ "-------------------------------");
					if (tamt > 150.00) {
						// go for next employee
						bl.statusUpdate(row, "Declined -- Total amount greater than $150.00");
						logger.info("--------------------------Declined -- Your total amount( " + tamt
								+ " ) is greater than $150.00-------------------------------");
						if (row != (r1.length - 1)) {
							logger.info("--------------------------Row count: " + row
									+ " and Total no of users in datasheet: " + (r1.length - 1)
									+ "--(IF)-------------------------------");
							driver.get(
									"https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
							Thread.sleep(5000);
							driver.switchTo().defaultContent();
							continue Employee_loop;
						} else {
							logger.info("--------------------------Row count: " + row
									+ " and Total no of users in datasheet: " + (r1.length - 1)
									+ "--(else)-------------------------------");
							break Employee_loop;
						}
					}
				
					//Total Item count check 
					String no_of_item = driver.findElement(By.xpath("//*[@id='gbsection']//div/span[@class='line-item-count']")).getText();
					logger.info("--------------------------Total no of items : " + no_of_item+ "-------------------------------");
					String no_of_item_substring = no_of_item.substring(1, no_of_item.length() - 1);
					logger.info("--------------------------Total no of items_string : " + no_of_item_substring+ "-------------------------------");
					//String no_of_item_length = no_of_item_string.substring(1, total_amount.length());
					//logger.info("--------------------------Final Amount : " + no_of_item_length+ "-------------------------------");
					int no_of_item_int = Integer.parseInt(no_of_item_substring);
					logger.info("--------------------------Final count of items (in int) : " + no_of_item_int+ "-------------------------------");
					logger.info("total items added in cart ="+no_of_item_int+" total actual items per user request ="+item_counter);
					if (no_of_item_int != item_counter) {
						
						logger.info("--------------------------Failed -- total item count does not match-------------------------------");
						if (row != (r1.length - 1)) {
							
							logger.info("--------------------------Row count: " + row
									+ " and Total no of users in datasheet: " + (r1.length - 1)
									+ "--(IF)-------------------------------");
							driver.get(
									"https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
							Thread.sleep(5000);
							driver.switchTo().defaultContent();
							continue Employee_loop;
							} 
						else {
							
							logger.info("--------------------------Row count: " + row
									+ " and Total no of users in datasheet: " + (r1.length - 1)
									+ "--(else)-------------------------------");
							break Employee_loop;
						}	
						
						
					}// total item count check ends
						
					
					
					try {
						// Item check loop
						for (i = 1; i <= item_counter; i++) {
							logger.info("--------------------------****entered checkout page for loop for item: "
									+ i + "****-------------------------------");

							// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='section'
							// and @class='item line-item-container-"+i+"']")));
							String item_name = driver
									.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
											+ "']/div[2]/div[3]/div[1]/p[2]"))
									.getText();
							logger.info("--------------------------****entered checkout page for loop for item: "
									+ item_name + "****-------------------------------");
							// Item warning check
							Boolean multi_warning_Present = false;
							try {
								multi_warning_Present = driver
										.findElements(By.xpath("//*[@id='section' and @class='item line-item-container-"
												+ i
												+ "']//child::span[@class='multiple-policies-text' and text()='This item has or requires multiple justifications']"))
										.size() > 0;
								logger.info("--------------------------Multiple warnings present : "
										+ multi_warning_Present + "-------------------------------");
							} catch (Throwable e) {
							}
							Boolean shi_warning_Present = false;
							try {
								shi_warning_Present = driver
										.findElements(By.xpath("//*[@id='section' and @class='item line-item-container-"
												+ i + "']//child::span[.='SHI Supplier Policy']"))
										.size() > 0;
								logger.info("--------------------------SHI warning present : "
										+ shi_warning_Present + "-------------------------------");
							} catch (Throwable e) {
							}
							logger.info("--------------------------SHI warning: *" + shi_warning_Present
									+ "* and multi warning: *" + multi_warning_Present
									+ "*-------------------------------");
							// multi warning check
							if (multi_warning_Present == true) {
								bl.statusUpdate(row, "Failed -- Wrong item selected");
								logger.info(
										"--------------------------Declined -- Wrong item selected-------------------------------");
								if (row != (r1.length - 1)) {
									logger.info("--------------------------Row count: " + row
											+ " and Total no of users in datasheet: " + (r1.length - 1)
											+ "--(IF)-------------------------------");
									driver.get(
											"https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
									Thread.sleep(5000);
									driver.switchTo().defaultContent();
									continue Employee_loop;
								} else {
									logger.info("--------------------------Row count: " + row
											+ " and Total no of users in datasheet: " + (r1.length - 1)
											+ "--(else)-------------------------------");
									break Employee_loop;
								}
							}
							// SHI warning check
							else if (shi_warning_Present == true)
							// if(driver.findElement(By.xpath("//div[@id='section' and @class='item
							// line-item-container-"+i+"']//child::span[.='SHI Supplier
							// Policy']")).isDisplayed())
							{
								logger.info(
										"--------------------------entered SHI supplier check warning check-------------------------------");
								/*
								 * Actions act = new Actions(driver); WebElement shi_check =
								 * driver.findElement(By.
								 * xpath("//*[@id='section' and @class='item line-item-container-"
								 * +i+"']//child::div/button[@aria-label='  Required' and @class='dropdown-toggle invalid']/i"
								 * )); act.moveToElement(shi_check); System.out.
								 * println("--------------------------moved to shi warning drop down-------------------------------"
								 * ); Thread.sleep(3000); act.moveToElement(driver.findElement(By.
								 * xpath("//*[@id='section' and @class='item line-item-container-"
								 * +i+"']//child::div/button[@aria-label='  Required' and @class='dropdown-toggle invalid']/i"
								 * ))).click().perform();
								 */
								wait.until(ExpectedConditions.elementToBeClickable(
										By.xpath("//*[@id='section' and @class='item line-item-container-" + i
												+ "']//div[@class='form-group error']//button")));
								logger.info(
										"--------------------------wait completed for SHI supplier check warning clickable check-------------------------------");
								// driver.findElement(By.xpath("//*[@id='section' and @class='item
								// line-item-container-"+i+"']//child::div/button[@aria-label=' Required' and
								// @class='dropdown-toggle invalid']/i")).click();

								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"
										+ i + "']//div[@class='form-group error']//button")).sendKeys(Keys.RETURN);
								logger.info(
										"--------------------------clicked SHI supplier check warning drop down-------------------------------");
								// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='section'
								// and @class='item line-item-container-"+i+"']//div[@class='form-group
								// error']//a[contains(text(),'I confirm')]")));
								Thread.sleep(7000);
								logger.info(
										"--------------------------clicked SHI supplier check warning drop down - wait completed-------------------------------");
								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"
										+ i + "']//div[@class='form-group error']//a[contains(text(),'I confirm')]"))
										.click();
								logger.info(
										"--------------------------clicked 'I confirm' SHI supplier check warning-------------------------------");
							}

							else {
								logger.info(
										"--------------------------No warning present-------------------------------");

							}

							// item expansion check
							Boolean item_expanded = false;
							try {
								item_expanded = driver
										.findElements(By.xpath("//*[@id='section' and @class='item line-item-container-"
												+ i
												+ "']//child::div[@class='content-group item-toggle-section']//child::i[@class='icon-slim-arrow-down']"))
										.size() > 0;
								logger.info("--------------------------item is not expanded : " + item_expanded
										+ "-------------------------------");
							} catch (Throwable e) {
							}
							if (item_expanded == true) {
								logger.info(
										"--------------------------Item already expanded-------------------------------");
							} else {
								// Expand the item
								logger.info(
										"--------------------------Item needs to be expanded-------------------------------");
								// driver.findElement(By.xpath("//*[@id='section' and @class='item
								// line-item-container-"+i+"']//child::div[@class='content-group
								// item-toggle-section']//child::i[@class='icon-slim-arrow-right']")).click();
								Thread.sleep(5000);

								Actions actions = new Actions(driver);
								WebElement item_expansion = driver.findElement(
										By.xpath("//*[@id='section' and @class='item line-item-container-" + i
												+ "']//div[@class='line-item-content']/div/button[@aria-label='Toggle item details ']"));
								actions.moveToElement(item_expansion);
								actions.build().perform();

								logger.info(
										"--------------------------moved to item expansion drop down-------------------------------");
								Thread.sleep(3000);

								// actions.moveToElement(driver.findElement(By.xpath("//*[@id='section' and
								// @class='item
								// line-item-container-"+i+"']//div[@class='line-item-content']/div/button[@aria-label='Toggle
								// item details ']"))).click().perform();
								wait.until(ExpectedConditions.elementToBeClickable(
										By.xpath("//*[@id='section' and @class='item line-item-container-" + i
												+ "']//div[@class='line-item-content']/div/button[@aria-label='Toggle item details ']")));
								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"
										+ i
										+ "']//div[@class='line-item-content']/div/button[@aria-label='Toggle item details ']"))
										.sendKeys(Keys.RETURN);
								// driver.findElement(By.xpath("//*[@id='section' and @class='item
								// line-item-container-"+i+"']//div[@class='line-item-content']/div/button[@aria-label='Toggle
								// item details ']")).click();

								Boolean item_expanded1 = false;
								try {
									item_expanded1 = driver.findElements(
											By.xpath("//*[@id='section' and @class='item line-item-container-" + i
													+ "']//child::div[@class='content-group item-toggle-section']//child::i[@class='icon-slim-arrow-down']"))
											.size() > 0;
									logger.info("--------------------------item is not expanded : "
											+ item_expanded1 + "-------------------------------");
								} catch (Throwable e) {
								}

								if (item_expanded1 == true) {
									logger.info(
											"--------------------------Item expanded (IF)-------------------------------");
								} else {
									driver.findElement(
											By.xpath("//*[@id='section' and @class='item line-item-container-" + i
													+ "']//div[@class='line-item-content']/div/button[@aria-label='Toggle item details ']"))
											.sendKeys(Keys.RETURN);
								}

								logger.info(
										"--------------------------Item expanded-------------------------------");
								Thread.sleep(3000);
							}

							// Accounting drop-down expansion check
							logger.info(
									"--------------------------accounting expansion check-------------------------------");
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//child::span[text()='Accounting']")).click();

							// new accounting cost center code
							logger.info(
									"--------------------------Search cost center code-------------------------------");
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//*[@id='section' and @class='item line-item-container-" + i
											+ "']//div[@class='accounting-section']//field[5]/div[@class='input-wrap']//button")));
							// driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']/div[1]/div/ng-include/div[1]/button")).click();//Click
							// Cost center drop down
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='accounting-section']//field[5]/div[@class='input-wrap']//button"))
									.click();// Click Cost center drop down
							Thread.sleep(3000);
							logger.info(
									"--------------------------Search cost center code--clicked cost center drop down-------------------------------");
							// driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']//child::a[contains(text(),'Browse')]")).click();//Click
							// browse all
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='accounting-section']//field[5]/div[@class='input-wrap']//a[contains(text(),'Browse')]"))
									.click();// Click browse all

							// Thread.sleep(2000);
							logger.info(
									"--------------------------Search cost center code--clicked browse all-------------------------------");

							logger.info(
									"--------------------------entered cost center code search form-------------------------------");

							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//*[@id='section' and @class='item line-item-container-" + i
											+ "']//div[@class='accounting-section']//field[5]/div[@class='input-wrap']//h3[text()='Cost Center']")));
							driver.switchTo().activeElement();
							logger.info("click on search and enter cost center code 'US0001T8' ");
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='accounting-section']//field[5]/div[@class='input-wrap']//input[@placeholder='Search']"))
									.click();// click on search input text area
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='accounting-section']//field[5]/div[@class='input-wrap']//input[@placeholder='Search']"))
									.sendKeys("US0001T8");// enter cost center value to search
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='accounting-section']//field[5]/div[@class='input-wrap']//form/button[@class='nav-search-right' and @aria-label='Search']"))
									.click();// click on search button
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='accounting-section']//field[5]/div[@class='input-wrap']//button[@aria-label='choose Cost Center US0001T8']"))
									.click();// click on choose button

							Thread.sleep(5000);
							// wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='section'
							// and @class='item
							// line-item-container-']//child::*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHAccountingsD0Tt3d-Fi3lD-pAtHSplitAccountingsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHCostCenter']//child::button")));
							logger.info(
									"--------------------------cost center code selected (end)-------------------------------");

							wait.until(ExpectedConditions.elementToBeClickable(
									By.xpath("//*[@id='section' and @class='item line-item-container-" + i
											+ "']//div[@class='accounting-section']//field[5]/div[@class='input-wrap']//button")));
							logger.info(
									"--------------------------cost center code selected- wait after submit completed-------------------------------");

							// shipping address expansion check
							Boolean address_expanded = false;
							try {
								address_expanded = driver
										.findElements(By.xpath("//*[@id='section' and @class='item line-item-container-"
												+ i
												+ "']/div[3]/div[3]/ng-toggle/button/span/i[@class='icon-slim-arrow-down']"))
										.size() > 0;
								logger.info("--------------------------If address is not expanded : "
										+ address_expanded + "-------------------------------");
							} catch (Throwable e) {
							}
							if (address_expanded == true)
							// if(driver.findElement(By.xpath("//*[@id='section' and @class='item
							// line-item-container-"+i+"']/div[3]/div[3]/ng-toggle/button/span/i[@class='icon-slim-arrow-down']")).isDisplayed())
							{
								logger.info(
										"--------------------------Shipping address already expanded-------------------------------");
							} else {
								// Expand the item
								logger.info(
										"--------------------------clicking to expand the address-------------------------------");
								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"
										+ i + "']/div[3]/div[3]/ng-toggle/button/span/span[text()='Shipping']"))
										.click();
							}

							// address check
							/*
							 * System.out.
							 * println("--------------------------entered address check-------------------------------"
							 * ); driver.findElement(By.
							 * xpath("//*[@id='section' and @class='item line-item-container-"
							 * +i+"']//child::div[1]/button[@type='button' and @class='dropdown-chooser dropdown-toggle invalid']"
							 * )).click();
							 * 
							 * Boolean isaddress_Present=false; try { isaddress_Present =
							 * driver.findElements(By.
							 * xpath("//*[@id='section' and @class='item line-item-container-"+i+
							 * "']//child::a[normalize-space(.)='"+r1[row][4]+"']")).size()>0;
							 * logger.info("--------------------------value of address_present: "
							 * +isaddress_Present+ "-------------------------------");
							 * 
							 * } catch(Throwable e) {}
							 * 
							 * if(isaddress_Present == true) {
							 * 
							 * System.out.
							 * println("--------------------------address present-------------------------------"
							 * ); driver.findElement(By.
							 * xpath("//*[@id='section' and @class='item line-item-container-"+i+
							 * "']//child::a[normalize-space(.)='"+r1[row][4]+"']")).click();//Click
							 * existing address Thread.sleep(10000);
							 * //logger.info("--------------------------Item " +i+
							 * " is done-------------------------------");
							 * 
							 * } else {
							 */
							// create new address
							logger.info(
									"--------------------------address not present, create new one-------------------------------");
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]//button/i")).click();
							logger.info(
									"--------------------------clicked address drop down-------------------------------");
							
							Thread.sleep(5000);
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//*[@id='section' and @class='item line-item-container-" + i
											+ "']//div[@class='ship-section']//field[1]//a[contains(text(),'Browse')]")));
							logger.info(
									"--------------------------address drop down - wait completed-------------------------------");
							// driver.findElement(By.xpath("//*[@id='LineItemsFi3lD-iNd3x-0p3n1Fi3lD-iNd3x-Cl0s3D0Tt3d-Fi3lD-pAtHShipTo']//child::div/a[contains(text(),'Browse')]")).click();//Click
							// browse all
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]//a[contains(text(),'Browse')]"))
									.click();// Click browse all

							logger.info(
									"--------------------------clicked Browse all from address drop down-------------------------------");
							// Thread.sleep(5000);

							// logger.info("new address sleep - 1 ");
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//*[@id='section' and @class='item line-item-container-" + i
											+ "']//div[@class='ship-section']//field[1]/div//h3[text()='Ship To Address']")));
							logger.info("wait for new address list pop up ");
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]/div//button[@class='btn-medium btn-inverse add-new-address']"))
									.click();// Ship to address---> Click new

							// Thread.sleep(3000);
							// logger.info("new address sleep - 2 ");
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//*[@id='section' and @class='item line-item-container-" + i
											+ "']//div[@class='ship-section']//field[1]/div//input[@id='full-name']")));
							logger.info("wait for new address form to populate");

							driver.switchTo().defaultContent();
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]/div//input[@id='full-name']"))
									.sendKeys(r1[row][4]);// name
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]/div//input[@id='streetline1']"))
									.sendKeys(r1[row][5]);// streetline1
							// driver.findElement(By.xpath("//*[@id='streetline2']")).sendKeys(r1[row][6]);//streetline2
							// driver.findElement(By.xpath("//*[@id='streetline3']")).sendKeys(r1[row][4]);//streetline3
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]/div//input[@id='city']"))
									.sendKeys(r1[row][6]);// city
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]/div//input[@id='region']"))
									.sendKeys(r1[row][7]);// region
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]/div//input[@id='postal-code']"))
									.sendKeys(r1[row][8]);// postal-code
							// driver.findElement(By.xpath("//select[@id='country']/option[235]"));//country
							Select select = new Select(driver
									.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
											+ "']//div[@class='ship-section']//field[1]/div//select[@id='country']")));
							select.selectByVisibleText("United States");// country

							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]/div//input[@id='phone']"))
									.sendKeys(r1[row][10]);// phone
							// driver.findElement(By.xpath("//*[@id='fax']")).sendKeys(r1[row][11]); //FAX
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]/div//input[@id='email']"))
									.sendKeys(r1[row][9]);// email

							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "']//div[@class='ship-section']//field[1]/div//div[12]/button[text()='Save']"))
									.click();

							Boolean isaddress_Submitted = false;
							try {
								isaddress_Submitted = driver
										.findElements(By.xpath("//*[@id='section' and @class='item line-item-container-"
												+ i
												+ "']//div[@class='ship-section']//*[@id='fieldChoice-header-notification']//i[@class='icon-message-warning']"))
										.size() > 0;
								logger.info("--------------------------value of address_present: "
										+ isaddress_Submitted + "-------------------------------");

							} catch (Throwable e) {
							}

							if (isaddress_Submitted == true) {
								bl.statusUpdate(row, "Failed - Wrong address provided");
								logger.info(
										"--------------------------Wrong address provided-------------------------------");
								if (row != (r1.length - 1)) {
									logger.info("--------------------------Row count: " + row
											+ " and Total no of users in datasheet: " + (r1.length - 1)
											+ "--(IF)-------------------------------");
									driver.get(
											"https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
									Thread.sleep(5000);
									driver.switchTo().defaultContent();
									continue Employee_loop;
								} else {
									logger.info("--------------------------Row count: " + row
											+ " and Total no of users in datasheet: " + (r1.length - 1)
											+ "--(else)-------------------------------");
									break Employee_loop;
								}
							}
							Thread.sleep(5000);
							// }

							// Deliver to name:
							logger.info("--------------------------Deliver to: " + r1[row][4]
									+ "-------------------------------");
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "'] //div[@class='ship-section']//field[2]//input[@name='DeliverTo']")).clear();

							Thread.sleep(5000);

							Boolean error_present = false;
							try {
								error_present = driver
										.findElements(By.xpath("//*[@id='section' and @class='item line-item-container-"
												+ i
												+ "'] //div[@class='ship-section']//field[2]//span[@id='error-code' and text()='Deliver To Attention must be set.']"))
										.size() > 0;
								logger.info("--------------------------Deliver to warning present check : "
										+ error_present + "-------------------------------");
							} catch (Throwable e) {
							}
							if (error_present == true) {
								logger.info(
										"--------------------------Deliver to warning present (IF)-------------------------------");
							} else {
								logger.info(
										"--------------------------Deliver to warning not present (else)-------------------------------");
								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"
										+ i
										+ "'] //div[@class='ship-section']//field[2]//i[@class='sap-ariba-new icon-info']"))
										.click();// click on info label to change focus
								Thread.sleep(2000);
								logger.info(
										"--------------------------Deliver to warning not present (else)-------------------------------");
								driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-"
										+ i + "'] //div[@class='ship-section']//field[2]//input[@name='DeliverTo']"))
										.click();
								Thread.sleep(2000);
							}

							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//*[@id='section' and @class='item line-item-container-" + i
											+ "'] //div[@class='ship-section']//field[2]//span[@id='error-code' and text()='Deliver To Attention must be set.']")));
							driver.findElement(By.xpath("//*[@id='section' and @class='item line-item-container-" + i
									+ "'] //div[@class='ship-section']//field[2]//input[@name='DeliverTo']"))
									.sendKeys(r1[row][4]);

							Thread.sleep(4000);
							logger.info(
									"--------------------------Item " + i + " is done-------------------------------");

						} // forloop ends here

						// Comment section
						driver.findElement(By.xpath("//*[@id='header-comments-comment']/div/div[1]/textarea"))
								.sendKeys("My Phone number is: " + r1[row][10]);
						Thread.sleep(2000);
						// wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='checkbox-header-comments']")));
						//driver.findElement(By.xpath("//*[@id='checkbox-header-comments']")).click();
						
						Boolean is_checked = false;
						try {
							is_checked = driver.findElement(By.xpath("//*[@id='checkbox-header-comments']"))
									.isSelected();
							logger.info("-------------------------- Checkbox is checked : " + is_checked
									+ "-------------------------------");
						} catch (Throwable e) {
						}
						if (is_checked == true) {
							logger.info(
									"--------------------------is_checked (IF)-------------------------------");
						} else {
							driver.findElement(By.xpath("//*[@id='checkbox-header-comments']")).click();
						}
						
						driver.findElement(By.xpath("//*[@id='header-comments-commentButton']")).click();//click add button 

						logger.info("Comment added for User " + row + " : " + r1[row][4]);
						logger.info(
								"--------------------------Comment added with phone number-------------------------------");
						Thread.sleep(5000);
					} catch (Throwable e) {
						// go for next employee
						// bl.statusUpdate(row, "Failed - Error occurred while Submission");
						logger.info(
								"--------------------------Error occurred while Submission-------------------------------");
						if (row != (r1.length - 1)) {
							logger.info("--------------------------Row count: " + row
									+ " and Total no of users in datasheet: " + (r1.length - 1)
									+ "--(IF)-------------------------------");
							driver.get(
									"https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
							Thread.sleep(5000);
							driver.switchTo().defaultContent();
							continue Employee_loop;
						} else {
							logger.info("--------------------------Row count: " + row
									+ " and Total no of users in datasheet: " + (r1.length - 1)
									+ "--(else)-------------------------------");
							break Employee_loop;
						}
					}

					// Submit
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
								"//*[@id='gbsection']//span[@data-jhitranslate-for-automation='COMMENT_REMOVE' and text()='Remove']")));

						if (driver.findElement(By.xpath("//*[@id='gbsection']//button[text()='Submit']")).isEnabled()) {
							driver.findElement(By.xpath("//*[@id='gbsection']//button[text()='Submit']")).click();// click
																													// submit
																													// button
							logger.info(
									"--------------------------Submit button clicked-------------------------------");
							Thread.sleep(7000);
							wait.until(ExpectedConditions
									.visibilityOfElementLocated((By.xpath("(//span[text()='Success'])[3]"))));
							Boolean is_submitted = false;
							try {
								is_submitted = driver.findElements(By.xpath("(//span[text()='Success'])[3]"))
										.size() > 0;
								logger.info("--------------------------if submission done: " + is_submitted
										+ "-------------------------------");
								success++;
							} catch (Throwable e) {
							}

							// check if it is submitted
							if (is_submitted == true) {

								logger.info("Request Submitted for User " + row + " : " + r1[row][4]);
								driver.findElement(
										By.xpath("(//button[@translate='actionButton2_button_View_Requisition'])[3]"))
										.click();
								WebElement element = driver
										.findElement(By.xpath("//div[@class='fd-action-bar__description']"));
								String elementval = element.getText();
								bl.statusUpdate(row, "Success - Submission completed with order number: " + elementval);// write
																														// status
																														// to
																														// data
																														// file
								if (row != (r1.length - 1)) {
									logger.info("--------------------------Row count: " + row
											+ " and Total no of users in datasheet: " + (r1.length - 1)
											+ "--(IF)-------------------------------");
									driver.get(
											"https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
									Thread.sleep(5000);
									driver.switchTo().defaultContent();
									continue Employee_loop;
								} else {
									logger.info("--------------------------Row count: " + row
											+ " and Total no of users in datasheet: " + (r1.length - 1)
											+ "--(else)-------------------------------");
									break Employee_loop;
								}
							} else {

								driver.findElement(By.xpath(
										"//*[@id='gbsection']//span[@data-jhitranslate-for-automation='COMMENT_REMOVE' and text()='Remove']"))
										.click();
								// go for next employee
								bl.statusUpdate(row, "Failed - Submission. Manual check is required");
								if (row != (r1.length - 1)) {
									logger.info("--------------------------Row count: " + row
											+ " and Total no of users in datasheet: " + (r1.length - 1)
											+ "--(IF)-------------------------------");
									driver.get(
											"https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
									Thread.sleep(5000);
									driver.switchTo().defaultContent();
									continue Employee_loop;
								} else {
									logger.info("--------------------------Row count: " + row
											+ " and Total no of users in datasheet: " + (r1.length - 1)
											+ "--(else)-------------------------------");
									break Employee_loop;
								}
							}
						}

						else {
							driver.findElement(By.xpath(
									"//*[@id='gbsection']//span[@data-jhitranslate-for-automation='COMMENT_REMOVE' and text()='Remove']"))
									.click();

							bl.statusUpdate(row, "Failed - Submission. Please check if an entry is submitted");
							if (row != (r1.length - 1)) {
								logger.info("--------------------------Row count: " + row
										+ " and Total no of users in datasheet: " + (r1.length - 1)
										+ "--(IF)-------------------------------");
								driver.get(
										"https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
								Thread.sleep(5000);
								driver.switchTo().defaultContent();
								continue Employee_loop;
							} else {
								logger.info("--------------------------Row count: " + row
										+ " and Total no of users in datasheet: " + (r1.length - 1)
										+ "--(else)-------------------------------");
								break Employee_loop;
							}
						}
					} catch (Throwable e) {
						logger.info(
								"--------------------------Row count: " + row + " and Total no of users in datasheet: "
										+ (r1.length - 1) + "--(else)-------------------------------");
						bl.statusUpdate(row, "Failed - Submission. Please check if an entry is submitted");
						if (row != (r1.length - 1)) {
							logger.info("--------------------------Row count: " + row
									+ " and Total no of users in datasheet: " + (r1.length - 1)
									+ "--(IF)-------------------------------");
							driver.get(
									"https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
							Thread.sleep(5000);
							driver.switchTo().defaultContent();
							continue Employee_loop;
						} else {
							logger.info("--------------------------Row count: " + row
									+ " and Total no of users in datasheet: " + (r1.length - 1)
									+ "--(else)-------------------------------");
							break Employee_loop;
						}
					}

					 }
					//}
				} else {
					logger.info("EE " + row + " Skipped");
				}

			} // Employee Loop ends here

			// bl.nofRecords(1, "Total number of Records processed : " +row);
			// bl.nofRecords(2, "Total number of Records submitted successfully: "
			// +success);

			// Archieving TD sheet with Timestamp
			if (!(prod_flag)) {
				// bl.archieveTD();
			}
		} else
			System.out.println("code is here");
	}
}
