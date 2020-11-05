package com.MyFramework_1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPOM {
	
	WebDriver driver;
	
	public LoginPOM(WebDriver d) {
		
	 driver = d;
	 PageFactory.initElements(driver, this);
		
		
	}
	
	@FindBy(xpath="//header//a[@class='sign-in']") public WebElement LoginLink;
	
	
	
	
	

}
