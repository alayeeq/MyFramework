package com.MyFramework_1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.MyFramework_1baseclasses.TestBase;

public class LaunchPOM  extends TestBase{

	
	
	

	
	public LaunchPOM(WebDriver d) {
		
		 driver = d;
		 PageFactory.initElements(driver, this);
			
			
		}
	


	@FindBy(xpath="//a[@id='lunchbutton']") public WebElement LaunchButton;
	
	
}
