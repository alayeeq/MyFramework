package com.MyFramework_1.testcases;

import org.testng.annotations.Test;

import com.MyFramework_1.pages.ProductPagePOM;
import com.MyFramework_1baseclasses.TestBase;

public class ProdSelectionAndCheckOut extends TestBase{
	
	@Test (priority = 4)
	ProductPagePOM p1=new ProductPagePOM(driver);
	public void sam() {
		
		
		String r1[][]=p1.xlread();
	}
	

}
