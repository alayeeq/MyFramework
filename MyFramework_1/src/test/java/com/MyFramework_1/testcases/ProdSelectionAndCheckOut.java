package com.MyFramework_1.testcases;

import com.MyFramework_1.pages.ProductPagePOM;
import com.MyFramework_1.pages.inputConsolPOM;
import com.MyFramework_1baseclasses.TestBase;


public class ProdSelectionAndCheckOut extends TestBase{
	
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
	
	//@Test (priority = 5, enabled =false)
	public void Product_addition() throws InterruptedException {
		
		ProductPagePOM p1=new ProductPagePOM(driver);
		inputConsolPOM bl=new inputConsolPOM(driver);
		
/*		driver.get("https://s1.ariba.com/gb/landingPage?id=97ae59a8-91d9-4e38-b0f6-6da107a60fe6&realm=IBM-GP0");
		String genericProdXpth="//div[@class='product-name']";
		p1.waitMod(driver, genericProdXpth);*/
		
		bl.initPath();
		String r1[][]=p1.xlread();
		logger.info(r1.length);
		driver.manage().window().maximize();
		Thread.sleep(5000);
		String status="";
		
		Employee_loop:
		for (int row=1;row<r1.length;row++)//Employee	
		{
			logger.info("EE "+row+" Started");
			status=bl.statusChck(row);
			
			if(status==null)
			{
				logger.info("EE "+row+" Started");
				Thread.sleep(3000);
				boolean flag=true;
				
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
			}
			else {
				logger.info("EE "+row+" Skipped");
			}
		 }
		//Archieving TD sheet with Timestamp
		//bl.archieveTD();
	}
	
	
}