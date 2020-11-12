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

		public void waitMod(WebDriver driver, String xpth) {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpth)));
			System.out.println("wait implemented");
		}
		
		public boolean webElementWaitAndCheck(WebDriver driver, String xpth) {
			boolean isPresent=false;
			
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpth)));
			//System.out.println("wait implemented");
			
			isPresent=driver.findElements(By.xpath(xpth)).size()>0;
			
			
			return isPresent;
		}
		
		public boolean chckCart(WebDriver driver)
		{
			System.out.println("chck cart starts ");
			boolean flag=false;
			String cartPth="//span[@class='fd-counter fd-counter--notification']";
			String cartitemsPth="//div[@class='col-xs-2 delete-cart-item']";
			String closeCartPth="//button[@class='icon-decline close-cart-button']";
			String closeCrtButton="//button[@title='Close cart']";
			
			try {
				Thread.sleep(3000);
				WebElement cart=driver.findElement(By.xpath(cartPth));
				System.out.println("Items exist in Cart - need to delete");
				cart.click();
				
				boolean dlist=true;
				while(dlist)
				{
					try {
						Thread.sleep(3000);
						WebElement dartList=driver.findElement(By.xpath(cartitemsPth));
						dartList.click();
					}catch(org.openqa.selenium.NoSuchElementException nse) {
						dlist=false;
						break;
					}
				}
				
				
/*				Thread.sleep(3000);
				WebElement closeCart=driver.findElement(By.xpath(closeCartPth));
				closeCart.click();*/
				
				System.out.println("Items Deleted rechecking Cart....");
				this.chckCart(driver);
				
			}catch(org.openqa.selenium.NoSuchElementException nse) {
				System.out.println("Cart Empty");
				flag=true;
			}catch(org.openqa.selenium.StaleElementReferenceException e) {
				System.out.println("stale element");
				e.printStackTrace();
			}catch(org.openqa.selenium.ElementNotInteractableException e) {
				System.out.println("Element Not Interactable");
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("return cart flag"+flag);
			return flag;
		}
		
		
		
		
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
		                    	System.out.println(type);
		                    }
		                    else if (type == CellType.NUMERIC)  
		                    {
		                    	td_Set[row][col]=NumberToTextConverter.toText(cl.getNumericCellValue());
		                    	System.out.println(type);
		                    	
		                    }
		                    else if (type == CellType.NUMERIC)  
		                    {
		                    	td_Set[row][col]=NumberToTextConverter.toText(cl.getNumericCellValue());
		                    	System.out.println(type);
		                    	
		                    }
		                    else  if (HSSFDateUtil.isCellDateFormatted(cl))
		                    {
		        				Date date=cl.getDateCellValue();
		        				System.out.println(date);
		        				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
		        				td_Set[row][col]=dateFormat.format(date);
		                    	System.out.println(type);	
		                    }
		                    else 
		                    {
		                      	td_Set[row][col]=cl.getStringCellValue();
		                    	System.out.println(type);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return td_Set;
		}
		
		public boolean addProdCart(WebDriver driver,String prdName ) throws InterruptedException 
		{
			Actions builder = new Actions(driver);
		
			String ibmEntNamXpth="//li[contains(text(),'1216218657 – IBM US Global Services_HSBC')]";
			String selectAddCartXpth1="//button[text()='Add to cart']";
			String selectGoBackXpth="//button[@title='Go back to the previous page']";
			
			Boolean flag=false;
			String tmp="(//div[@class='product-name' and text()='"+prdName+"'])[1]";
			
			
			
			Thread.sleep(5000);
			try {
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 flag=true;
				 //System.out.println(flag);
			}
			catch(org.openqa.selenium.NoSuchElementException nse){
			    //return false;
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
				        //return false;
				}
	
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
			}
			return flag;
		}
}
