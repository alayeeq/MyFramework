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
				System.out.println("wait implemented for xpath "+xpth);
				itemFound=true;
			}catch(Exception e) {
				System.out.println("Element NOT found");
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
				
				cartExpandChck = this.waitMod(driver,cartPth);
				WebElement cart=driver.findElement(By.xpath(cartPth));
				System.out.println("Items exist in Cart - need to delete");
				
				cart.click();
				System.out.println("Cart icon clicked");
				
				//check whether cart got Expanded 
				cartExpandChck=this.waitMod(driver, cartitemsPth);
				//If cart is NOT expanded click again
				if(!(cartExpandChck))
					{
					cart.click();
					System.out.println("Cart icon clicked again");
					}
				//check whether cart expanded again IF NOT exit with return flag as false.
				cartExpandChck=this.waitMod(driver, cartitemsPth);
				
				if(cartExpandChck)
				{
					boolean dlist=true;
					boolean cartRef=false;
					int refresh_Counter=1;
					
					while(dlist)
					{
						try {
							//Thread.sleep(3000);
							if(this.waitMod(driver, cartitemsPth));
								{
								WebElement dartList=driver.findElement(By.xpath(cartitemsPth));
								dartList.click();
								}
						}catch(org.openqa.selenium.NoSuchElementException nse) {
							System.out.println("Cart deletion completed");
							dlist=false;
							//break;
						}catch(ElementNotInteractableException nse){
							System.out.println("Page unResponsive - Refresh page");
							
							if(refresh_Counter<=3)
							{
								driver.navigate().refresh();
								Thread.sleep(20000);
								System.out.println("Refresh :"+refresh_Counter);
								refresh_Counter++;
							}
							else {
								System.out.println("Maxium refresh reached Batch to Terminate");
								dlist=false;
								//break;
							}
						}
					}
					
					if(refresh_Counter<=3)
					{
						System.out.println("Items Deleted rechecking Cart....");
						FL_flag=this.chckCart(driver);
					}
				}
				
			}catch(org.openqa.selenium.NoSuchElementException nse) {
				System.out.println("Cart Empty");
				FL_flag=true;
			}catch(org.openqa.selenium.StaleElementReferenceException e) {
				System.out.println("stale element");
				e.printStackTrace();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}/*catch(org.openqa.selenium.ElementNotInteractableException e) {
				System.out.println("Element Not Interactable");
				e.printStackTrace();
			}*/
			
			System.out.println("return cart flag : "+FL_flag);
			System.out.println("chck cart ends ");
			
			return FL_flag;
		}
		
		
/*		public boolean refresh()
		{
			if(refresh_Counter<=3)
			{
				driver.navigate().refresh();
				Thread.sleep(20000);
				System.out.println("Refresh :"+refresh_Counter);
				refresh_Counter++;
			}
			else {
				System.out.println("Maxium refresh reached Batch to Terminate");
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
			System.out.println("ROW COUNT "+rowCount);
			++rowCount;
			
			td_Set=new String[rowCount][27];
			
		
				for (int row=0;row<rowCount;row++)
				{
					for(int col=0;col<27;col++)
					{
						XSSFRow rw=sh.getRow(row);
						XSSFCell cl=rw.getCell(col);
						//System.out.println("Row # "+row+"| Col # "+col);
						
						//td_Set[row][col]=cl.getStringCellValue();
						
						try {
		                    CellType type = cl.getCellType();
							
		                    if ((type == CellType.STRING) ||(type == CellType.BLANK))
		                    {					
		                    	td_Set[row][col]=cl.getStringCellValue();
		                    	//System.out.println(type);
		                    }
		                    else if (type == CellType.NUMERIC)  
		                    {
		                    	td_Set[row][col]=NumberToTextConverter.toText(cl.getNumericCellValue());
		                    	//System.out.println(type);
		                    	
		                    }
		                    else if (type == CellType.NUMERIC)  
		                    {
		                    	td_Set[row][col]=NumberToTextConverter.toText(cl.getNumericCellValue());
		                    	//System.out.println(type);
		                    	
		                    }
		                    else  if (HSSFDateUtil.isCellDateFormatted(cl))
		                    {
		        				Date date=cl.getDateCellValue();
		        				System.out.println(date);
		        				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
		        				td_Set[row][col]=dateFormat.format(date);
		                    	//System.out.println(type);	
		                    }
		                    else 
		                    {
		                      	td_Set[row][col]=cl.getStringCellValue();
		                    	//System.out.println(type);
		                    }
							}catch(NullPointerException e) 
				        { 
				            //System.out.print("NullPointerException Caught"); 
				         	//td_Set[row][col]=cl.getStringCellValue();
				        } 
	                     
	                    System.out.println("Row # "+row+"| Col # "+col+"| Val -->"+td_Set[row][col]);
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
		
		public boolean addProdCart(WebDriver driver,String prdName ) throws InterruptedException 
		{
			Actions builder = new Actions(driver);
		
			String ibmEntNamXpth="//li[contains(text(),'1216218657 – IBM US Global Services_HSBC')]";
			String selectAddCartXpth1="//button[text()='Add to cart']";
			String selectGoBackXpth="//button[@title='Go back to the previous page']";
			
			Boolean flag=false;
			String tmp="(//div[@class='product-name' and text()='"+prdName+"'])[1]";
			
			
			boolean siteChck=false;
			
			//site check
			try {
				String prodPageXpth="//h3[@class='req-id title']";
				WebElement prodPageEle = wait_base(prodPageXpth);
			
				if(prodPageEle.isDisplayed()) 
					{
					System.out.println("Product Page is loaded");
					Thread.sleep(1000);
					siteChck=true;
					}
				}catch(Exception e) 
				{
					System.out.println("Product Page is NOT properly loaded");
				}
			
			
			if(siteChck)
			{
				//Step 1 - Click search box
				//Step 2 - Enter product name
				//Step 3 - Click Enter
				
				String srchXpth="//input[@class='fd-input']";
				String srchButtonXpth="//button[@class='sap-icon--search fd-button--shell']";
				
				WebElement srchEle=driver.findElement(By.xpath(srchXpth));
				WebElement srchButtonEle=driver.findElement(By.xpath(srchButtonXpth));
				
				srchEle.click();
				srchEle.clear();
				srchEle.sendKeys(prdName);
				srchButtonEle.click();
			
				Thread.sleep(5000);
				
				try {
					 WebElement prod = driver.findElement(By.xpath(tmp));
					 builder.moveToElement(prod).build().perform();
					 prod.click();
					 flag=true;
				}
				catch(org.openqa.selenium.NoSuchElementException nse){
					System.out.println("invalid Product selection : "+prdName);
					flag=false;
				}
				catch(org.openqa.selenium.ElementNotInteractableException ese) {
					System.out.println("Element not interacctable - "+tmp);
					flag=false;
				}
				
				if(flag) {
					Thread.sleep(5000);
					try{
						WebElement ibmEntName = driver.findElement(By.xpath(ibmEntNamXpth));
						 ibmEntName.click();
					    }
					    catch(org.openqa.selenium.NoSuchElementException nse){
					}
		
					 catch(org.openqa.selenium.TimeoutException nse){
					}
					
					 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
					 selectAddCart.click();
					 
					 Thread.sleep(5000);
					 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
					 selectGoBack.click();

					 try {
					 srchEle.clear();
					srchButtonEle.click();
					 }catch(Exception e) {
					 }
					
				}
			}
			return flag;
		}
		
}
