package com.MyFramework_1.pages;

import java.io.IOException;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.MyFramework_1baseclasses.TestBase;

public class LandingPOM extends TestBase {

	
	
	
	public LandingPOM() {
		
		
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//header//a[@class='sig-in']") public WebElement Loginlink;
	
	public void SelectLoginLink() {
		
		Loginlink.click();
		
		
	}
	
}
