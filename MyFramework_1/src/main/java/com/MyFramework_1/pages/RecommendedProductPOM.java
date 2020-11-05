package com.MyFramework_1.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.MyFramework_1baseclasses.TestBase;

public class RecommendedProductPOM extends TestBase{
	
	public RecommendedProductPOM(WebDriver d) {
		
		
		driver = d;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy (xpath = "//*[@id=\"popular\"]/div/div/div[2]/div/div[1]/img") public WebElement RECPROD;

}
