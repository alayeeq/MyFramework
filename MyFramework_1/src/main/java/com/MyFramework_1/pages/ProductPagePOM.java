package com.MyFramework_1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.MyFramework_1baseclasses.TestBase;

public class ProductPagePOM extends TestBase{
		
		//WebDriver driver;
		
		public LoginPOM(WebDriver d) {
		 driver = d;
		 PageFactory.initElements(driver, this);
		}
		
		@FindBy(xpath="//input[@name='username']") public WebElement UserID;

}
