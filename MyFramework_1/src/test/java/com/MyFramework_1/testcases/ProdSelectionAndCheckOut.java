package com.MyFramework_1.testcases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.MyFramework_1.pages.ProductPagePOM;
import com.MyFramework_1.pages.inputConsolPOM;
import com.MyFramework_1baseclasses.TestBase;


public class ProdSelectionAndCheckOut extends TestBase{
	
	public String timeStamp()
	{
		
		String dt=null;
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMdd HHmm");  
		   LocalDateTime now = LocalDateTime.now(); 
		   dt=dtf.format(now);
		   System.out.println(dt);  
		return dt;
		
	}
	
}