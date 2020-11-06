package com.MyFramework_1.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.MyFramework_1baseclasses.TestBase;

public class ProductPagePOM extends TestBase{
		
		//WebDriver driver;	
		public ProductPagePOM(WebDriver d) {
		 driver = d;
		 PageFactory.initElements(driver, this);
		}
		
		
		
		@FindBy(xpath="//input[@name='username']") public WebElement UserID;

		
		
		
		
		
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
			
			td_Set=new String[rowCount][24];
			
		
				for (int row=0;row<rowCount;row++)
				{
					for(int col=0;col<24;col++)
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
		
		public void addProdCart(WebDriver driver,String prdName ) throws InterruptedException 
		{
			Actions builder = new Actions(driver);
		
			String prodXpth="(//div[@ng-repeat='resource in containerResourceArr track by $index'])";
			String addCartXpth="//span[@ng-if='!continueShopping']";
			String selectXpth="//button[@class='btn-small btn-primary";
			String ibmEntNamXpth="//li[contains(text(),'1216218657 – IBM US Global Services_HSBC')]";
			String selectAddCartXpth="//button[@name='addToCart']";
			String selectAddCartXpth1="//button[text()='Add to cart']";
			String selectGoBackXpth="//button[@title='Go back to the previous page']";
			
			String directProdAddCart="(//div[@ng-repeat='resource in containerResourceArr track by $index'])[i]//span[@ng-if='!continueShopping']";
			String directSel="(//div[@ng-repeat='resource in containerResourceArr track by $index'])[i]//button[@class='btn-small btn-primary";
		
			//String prdName=null;
			//prdName="ViewSonic VX2757-MHD - LED monitor - Full HD (1080p) - 27";
			
			switch(prdName){
			
			case "ViewSonic VX2757-MHD - LED monitor - Full HD (1080p) - 27":
				//option 1
			{
				String tmp=prodXpth+"[1]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
				 
			}
				break;
		
			case "T24i-20(A20238FT0)23.8 inch Monitor-HDMI":
				//option 2
			{
				String tmp=prodXpth+"[2]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
				 Thread.sleep(5000);
				 WebElement ibmEntName = driver.findElement(By.xpath(ibmEntNamXpth));
				 ibmEntName.click();
				 
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
			}
				break;
				
			case "Lenovo Ultraslim Plus Wireless Keyboard & Mouse":
				//option 3
			{
				String tmp=prodXpth+"[3]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
				 Thread.sleep(5000);
				 WebElement ibmEntName = driver.findElement(By.xpath(ibmEntNamXpth));
				 ibmEntName.click();
				 
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
			}
				break;
				
			case "Apple Magic Keyboard - keyboard - US":
				//option 4
			{
				String tmp=prodXpth+"[4]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
			
			case "Logitech Triathlon M720 - mouse - Bluetooth, 2.4 GHz":
				//option 5
			{
				String tmp=prodXpth+"[5]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
				
			case "Apple Magic Mouse 2 - mouse - Bluetooth":
				//option 6
			{
				String tmp=prodXpth+"[6]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "Apple Magic Trackpad 2 - trackpad - Bluetooth 4.0 - silver":
				//option 7	
			{
				String tmp=prodXpth+"[7]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
			
			case "PWR ADP_BO Type-C 65W Adapter":
				//option 8
			{
				String tmp=prodXpth+"[8]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
				 Thread.sleep(5000);
				 WebElement ibmEntName = driver.findElement(By.xpath(ibmEntNamXpth));
				 ibmEntName.click();
				 
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "PWR ADP_BO TP 170W AC Adapter-slim tip":
				//option 9
			{
				String tmp=prodXpth+"[9]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
				 Thread.sleep(5000);
				 WebElement ibmEntName = driver.findElement(By.xpath(ibmEntNamXpth));
				 ibmEntName.click();
				 
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "Apple USB-C Charge Cable - USB-C cable - 2 m":
				//option 10	
			{
				String tmp=prodXpth+"[10]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "96W USB-C Power Adapter MacBook Pro + USB-C Charge Cable (2M) Bundle":
				//option 11
			{
				String tmp=prodXpth+"[11]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "EVOLVE 40 UC USB-A CORDED MONAURAL HEADSET":
				//option 12
			{
				String tmp=prodXpth+"[12]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "EVOLVE 40 UC USB-A CORDED BINAURAL HEADSET":
				//option 13
			{
				String tmp=prodXpth+"[13]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "VOYAGER 5200 UC WIRELESS BLUETOOTH HEADSET UC":
				//option 14
			{
				String tmp=prodXpth+"[14]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "EVOLVE 65 WIRELESS BT DUAL EAR HEADSET W/STAND":
				//option 15
			{
				String tmp=prodXpth+"[15]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "EVOLVE 65 WIRELESS BLUETOOTH SINGLE EAR HEADSET":
				//option 16
			{
				String tmp=prodXpth+"[16]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "StarTech.com USB 3.0 to Gigabit Ethernet Adapter - 10/100/1000 NIC Network Adapt":
				//option 17
			{
				String tmp=prodXpth+"[17]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
			case "StarTech.com USB C to Gigabit Ethernet Adapter - Black - USB 3.1 to RJ45 LAN Net":
				//option 18
			{
				String tmp=prodXpth+"[18]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
				
			case "StarTech.com Portable USB C Multiport Video Adapter - 4k HDMI or VGA, USB 3.0":
				//option 19
			{
				String tmp=prodXpth+"[19]";
				
				 Thread.sleep(5000);
				 WebElement prod = driver.findElement(By.xpath(tmp));
				 builder.moveToElement(prod).build().perform();
				 prod.click();
				 
  				 Thread.sleep(5000);
				 WebElement selectAddCart=driver.findElement(By.xpath(selectAddCartXpth1));
				 selectAddCart.click();
				 
				 Thread.sleep(5000);
				 WebElement selectGoBack=driver.findElement(By.xpath(selectGoBackXpth));
				 selectGoBack.click();
					 
			}
				break;
				
				
			default:
			{
				System.out.println("Default switch - invalid product");
			}
				
			}
		}
}
