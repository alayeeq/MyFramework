package com.MyFramework_1.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.MyFramework_1baseclasses.TestBase;

public class ProductPagePOM extends TestBase{
	
	boolean dummyFlag =true;
	inputConsolPOM bl = new inputConsolPOM(driver);
	
		//WebDriver driver;	
		public ProductPagePOM(WebDriver d) {
		 driver = d;
		 PageFactory.initElements(driver, this);
		}
		
		@FindBy(xpath="//input[@name='username']") public WebElement UserID;

		public boolean waitMod(WebDriver driver, String xpth) {
			boolean itemFound=false;
			try {
				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpth)));
				logger.info("wait implemented for xpath "+xpth);
				itemFound=true;
			}catch(Exception e) {
				logger.info("Element NOT found");
			}
			return itemFound;
		}
		
		public boolean webPageChck(WebDriver driver) {
			boolean isLoaded=false;
			
			  JavascriptExecutor js = (JavascriptExecutor)driver;
			  
			  
			  //Initially bellow given if condition will check ready state of page.
			  if (js.executeScript("return document.readyState").toString().equals("complete")){ 
				  logger.info("Page Is loaded.");
			   isLoaded=true;
			   return isLoaded; 
			  } 
			  
			  //This loop will rotate for 20 times to check If page Is ready after every 3 second.
			  //You can replace your value with 20 If you wants to Increase or decrease wait time.
			  for (int i=0; i<20; i++)
			  	{ 
				try 
				   {
						logger.info("Batch wait +3 Sec");
					   Thread.sleep(3000);
				    }catch (InterruptedException e) {}
				//To check page ready state.
			   if (js.executeScript("return document.readyState").toString().equals("complete"))
			   {
				   logger.info("Page Loaded");
				   isLoaded=true;
				   break;
			   }
			  }
			   
			return isLoaded;
		}
		
		public boolean chckCart(WebDriver driver)
		{
			logger.info("chck cart starts ");
			boolean FL_flag=false;
			boolean cartExpandChck=false;
			
			String cartPth="//button[@aria-label='Shopping Cart']//span[@class='fd-counter fd-counter--notification']";
			String cartitemsPth="//div[@class='col-xs-2 delete-cart-item']";
			String closeCartPth="//button[@class='icon-decline close-cart-button']";
			String closeCrtButton="//button[@title='Close cart']";
			//123
			try {
				//Thread.sleep(3000);
				
				//cartExpandChck = this.waitMod(driver,cartPth);
				
				try {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cartPth)));
					logger.info("wait implemented for xpath "+cartExpandChck);
					cartExpandChck=true;
				}catch(Exception e) {
					logger.info("Element NOT found");
				}
				
				WebElement cart=driver.findElement(By.xpath(cartPth));
				logger.info("Items exist in Cart - need to delete");
				
				cart.click();
				logger.info("Cart icon clicked");
				
				//check whether cart got Expanded 
				cartExpandChck=this.waitMod(driver, cartitemsPth);
				
				//If cart is NOT expanded click again
				if(!(cartExpandChck))
					{
					cart.click();
					logger.info("Cart icon clicked again");
					}
				
				//check whether cart expanded again IF NOT exit with return flag as false.
				cartExpandChck=this.waitMod(driver, cartitemsPth);
				
				if(cartExpandChck)
				{
					boolean dlist=true;
					boolean cartRef=false;
					int refresh_Counter=1;
					int prodDelCounter=0;
					
					while(dlist)
					{
						try {
							Thread.sleep(3000);
							
/*							boolean tmp=this.waitMod(driver, cartitemsPth)
							logger.info(tmp);*/
/*							if(tmp);*/
							
								{
								WebElement dartList=driver.findElement(By.xpath(cartitemsPth));
								dartList.click();
								prodDelCounter++;
								logger.info("Product deleted count :"+prodDelCounter);
								}
						}catch(org.openqa.selenium.NoSuchElementException nse) {
							logger.info("Cart deletion completed");
							dlist=false;
							//break;
						}catch(ElementNotInteractableException nse){
							logger.info("Page unResponsive - Refresh page");
							
							if(refresh_Counter<=3)
							{
								driver.navigate().refresh();
								Thread.sleep(20000);
								logger.info("Refresh :"+refresh_Counter);
								refresh_Counter++;
							}
							else {
								logger.info("Maxium refresh reached Batch to Terminate");
								dlist=false;
								//break;
							}
						}
					}
					
					if(refresh_Counter<=3)
					{
						logger.info("Items Deleted rechecking Cart....");
						FL_flag=this.chckCart(driver);
					}
				}
				
			}catch(org.openqa.selenium.NoSuchElementException nse) {
				logger.info("Cart Empty");
				FL_flag=true;
			}catch(org.openqa.selenium.StaleElementReferenceException e) {
				logger.info("stale element");
				e.printStackTrace();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}/*catch(org.openqa.selenium.ElementNotInteractableException e) {
				logger.info("Element Not Interactable");
				e.printStackTrace();
			}*/
			
			logger.info("return cart flag : "+FL_flag);
			logger.info("chck cart ends ");
			
			return FL_flag;
		}
		
		
/*		public boolean refresh()
		{
			if(refresh_Counter<=3)
			{
				driver.navigate().refresh();
				Thread.sleep(20000);
				logger.info("Refresh :"+refresh_Counter);
				refresh_Counter++;
			}
			else {
				logger.info("Maxium refresh reached Batch to Terminate");
				dlist=false;
				//break;
			}
		}*/
		
		@SuppressWarnings("deprecation")
		public String[][] xlread()
		{
			String absPath=System.getProperty("user.dir");
			String tdPath=absPath+"\\TestData\\TD";
			String tdPathAbs=tdPath+"\\TD.xlsx";
			String[][] td_Set=null;

			try {
				
			File fil=new File(tdPathAbs);
			FileInputStream fis=new FileInputStream(fil);
			XSSFWorkbook wb;
			wb = new XSSFWorkbook(fis);

			XSSFSheet sh=wb.getSheet("Sheet1");
			int rowCount = sh.getLastRowNum();
			logger.info("ROW COUNT "+rowCount);
			++rowCount;
			
			td_Set=new String[rowCount][27];
			
		
				for (int row=0;row<rowCount;row++)
				{
					for(int col=0;col<27;col++)
					{
						XSSFRow rw=sh.getRow(row);
						XSSFCell cl=rw.getCell(col);
						//logger.info("Row # "+row+"| Col # "+col);
						
						//td_Set[row][col]=cl.getStringCellValue();
						
						try {
		                    CellType type = cl.getCellType();
							
		                    if ((type == CellType.STRING) ||(type == CellType.BLANK))
		                    {					
		                    	td_Set[row][col]=cl.getStringCellValue();
		                    	//logger.info(type);
		                    }
		                    else if (type == CellType.NUMERIC)  
		                    {
		                    	td_Set[row][col]=NumberToTextConverter.toText(cl.getNumericCellValue());
		                    	//logger.info(type);
		                    	
		                    }
		                    else if (type == CellType.NUMERIC)  
		                    {
		                    	td_Set[row][col]=NumberToTextConverter.toText(cl.getNumericCellValue());
		                    	//logger.info(type);
		                    	
		                    }
		                    else  if (HSSFDateUtil.isCellDateFormatted(cl))
		                    {
		        				Date date=cl.getDateCellValue();
		        				logger.info(date);
		        				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
		        				td_Set[row][col]=dateFormat.format(date);
		                    	//logger.info(type);	
		                    }
		                    else 
		                    {
		                      	td_Set[row][col]=cl.getStringCellValue();
		                    	//logger.info(type);
		                    }
							}catch(NullPointerException e) 
				        { 
				            //System.out.print("NullPointerException Caught"); 
				         	//td_Set[row][col]=cl.getStringCellValue();
				        } 
	                     
	                    //logger.info("Row # "+row+"| Col # "+col+"| Val -->"+td_Set[row][col]);
	                }
				}
				
		
					fis.close();
					wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return td_Set;
		}
		
		
		public int numberOfCols () {
			
			String [][] XcellArry = xlread();
			
			String [] RowOne = XcellArry [0];
			
			int NumCols = RowOne.length;
			
			return NumCols;
		}
		
		public boolean addProdCart(WebDriver driver,int row,String prdName ) throws InterruptedException 
		{
			Actions builder = new Actions(driver);
		
			Boolean flag=false;
			boolean siteChck=false;
			
			String ibmEntNamXpth="//li[contains(text(),'1216218657 – IBM US Global Services_HSBC')]";
			String selectAddCartXpth1="//button[text()='Add to cart']";
			String selectGoBackXpth="//button[@title='Go back to the previous page']";
			String tmp="(//div[@class='product-name' and text()='"+prdName+"'])[1]";
			String srchXpth="//input[@class='fd-input']";
			String srchButtonXpth="//button[@class='sap-icon--search fd-button--shell']";
			
			WebElement srchEle;
			WebElement srchButtonEle;
			WebElement ibmEntName;
			WebElement selectAddCart;
			WebElement selectGoBack;
			
			
			
			//site check
			try {
				String prodPageXpth="//h3[@class='req-id title']";
				WebElement prodPageEle = wait_base(prodPageXpth);
			
				if(prodPageEle.isDisplayed()) 
					{
					logger.info("Product Page is loaded");
					Thread.sleep(1000);
					siteChck=true;
					}
				}catch(Exception e) 
				{
					logger.info("Product Page is NOT properly loaded");
				}
			
			
			if(siteChck)
			{
				//Step 1 - Click search box
				//Step 2 - Enter product name
				//Step 3 - Click Enter
				
				try {
				srchEle=driver.findElement(By.xpath(srchXpth));
				srchEle.click();
				srchEle.clear();
				srchEle.sendKeys(prdName);
				flag=true;
				}catch(Exception e)
				{
					e.printStackTrace();
					logger.info("Failure with product search box entry");
					flag=false;
				}
				
				if(flag)
				{
					try {
					srchButtonEle=driver.findElement(By.xpath(srchButtonXpth));
					srchButtonEle.click();
					}catch(Exception e)
					{
						e.printStackTrace();
						logger.info("Product Search button click faliure");
						flag=false;
					}
				}
			
				dummyFlag=this.webPageChck(driver);
				dummyFlag=this.waitMod(driver, tmp);
				//Thread.sleep(5000);
				
				//Locating and clicking product name
				if(flag)
				{
					try {
						 WebElement prod = driver.findElement(By.xpath(tmp));	
						 
						 WebDriverWait wait1 = new WebDriverWait(driver, 60);
						 prod = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(tmp)));
						 
						 builder.moveToElement(prod).build().perform();
						 prod.click();
						 flag=true;
					}
					catch(org.openqa.selenium.NoSuchElementException nse){
						logger.info("invalid Product selection : "+prdName);
						bl.statusUpdate(row, "Failed -Invalid Product exist :"+prdName);
						flag=false;
					}
					catch(org.openqa.selenium.ElementNotInteractableException ese) {
						logger.info("Element not interacctable - "+tmp);
						flag=false;
					}
				}
				
				//Product page to be loaded
				dummyFlag=this.webPageChck(driver);
				//dummyFlag=this.waitMod(driver, selectAddCartXpth1);
				//Thread.sleep(1000);
				
				if(flag) {
					try{
						ibmEntName = driver.findElement(By.xpath(ibmEntNamXpth));
						ibmEntName.click();
					    }catch(org.openqa.selenium.NoSuchElementException nse){
				    	}catch(org.openqa.selenium.TimeoutException nse){
				    	}
					
					try {
						WebDriverWait wait1 = new WebDriverWait(driver, 60);
						selectAddCart = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(selectAddCartXpth1)));
						
						//selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
					 	selectAddCart.click();
					}catch(ElementClickInterceptedException e)
					{	
						try {
								driver.navigate().refresh();
								Thread.sleep(5000);
								WebDriverWait wait1 = new WebDriverWait(driver, 60);
								selectAddCart = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(selectAddCartXpth1)));
								selectAddCart.click();
							}catch(Exception f) 
							{
								f.printStackTrace();
								logger.info("Add Cart button failure");
								flag=false;
							}
					}catch(Exception e)
					{
						e.printStackTrace();
						logger.info("Add Cart button failure");
						flag=false;
					}
					 
					
					//page Load wait after Product addition to Cart
					 Thread.sleep(1000);
					 dummyFlag=this.webPageChck(driver);
					 dummyFlag=this.waitMod(driver, selectGoBackXpth);
					 
					 //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					 
					 try {
						 	WebDriverWait wait1 = new WebDriverWait(driver, 60);
						 	selectGoBack = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(selectGoBackXpth)));
							
							selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
							selectGoBack.click();
					 }catch(Exception e) {
						 e.printStackTrace();
						 logger.info("Back button failure");
						 flag=false;
					 }

					 try {
						 srchEle=driver.findElement(By.xpath(srchXpth));
						 srchEle.clear();

						 WebDriverWait wait1 = new WebDriverWait(driver, 60);	
						 srchButtonEle = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(srchButtonXpth)));
						 
						 //srchButtonEle=driver.findElement(By.xpath(srchButtonXpth));
						 srchButtonEle.click();
					 }catch(Exception e) {
						 e.printStackTrace();
						 logger.info("Warning - After Product addition - Srch Box is not cleared - Batch will continue");
					 }
					
				}
			}
			return flag;
		}
		
}
