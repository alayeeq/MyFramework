package com.MyFramework_1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.MyFramework_1baseclasses.TestBase;

public class LoginPOM extends TestBase{
	
	//WebDriver driver;
	
	public LoginPOM(WebDriver d) {
		
	 driver = d;
	 PageFactory.initElements(driver, this);
		
		
	}
	
	
	public String UserID = "//input[@name='username']";
	
	//@FindBy(xpath="//input[@name='username']") public WebElement UserID;
	@FindBy(xpath="//input[@name='password']") public WebElement Pwd;
	@FindBy(xpath="//button[@id='btn_signin']") public WebElement LoginButton;
	
	
	
	

}
