package com.MyFramework_1.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyFramework_1.pages.LandingPOM;
import com.MyFramework_1baseclasses.TestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class LandingPage_TC extends TestBase {

	public static ExtentTest test;
	public static ExtentHtmlReporter Hreporter;
	public static ExtentReports report;
	LandingPOM landingPOM;
	public Logger logger; 

	
	@BeforeTest
	
	public void Setup() {
		Hreporter = new ExtentHtmlReporter(CurrentDirectory+ "/test-output/my2.html");
		Hreporter.config().setDocumentTitle("MyFramework_1 Report");
		Hreporter.config().setReportName("First Report");
			
		report = new ExtentReports();	
		report.attachReporter(Hreporter);
		report.setSystemInfo("Environemnt", "QA");
		report.setSystemInfo("Tester Name","Asif" );
		report.setSystemInfo("Browser","Chrome" );
		logger = Logger.getLogger("LandingPage_TC");
		PropertyConfigurator.configure("log4j.properties");
		//ExtentReportsetup();
		
	}
	
	
	@BeforeMethod
	 
	public void setup() {
		initializaiton();
		landingPOM = new LandingPOM();
		
	
	}
	
	@AfterMethod (alwaysRun = true)
	public void teardown(ITestResult tr) throws IOException {
		
		//CallExtentReport("ITestResult tr");
		
		if(tr.getStatus()==ITestResult.FAILURE) {
			
		
		test.log(Status.FAIL, "Test Case Failed is" + tr.getName());
		test.log(Status.FAIL, "Test case Failed is" + tr.getThrowable());
		String Screenpath = screenshot(tr.getName());
		test.addScreenCaptureFromPath(Screenpath);
		}
		
		else if (tr.getStatus()==ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case skipped is" + tr.getName());
		}
		
		else if (tr.getStatus()==ITestResult.SUCCESS) {
			test.log(Status.SKIP, "Test Case Passed is" + tr.getName());
		}
		
		report.flush();
		
		driver.quit();
	}
	
	
	@Test
	 public void LoginLinkTest() {
		landingPOM.Loginlink.click();
		System.out.println("Login is clicked");
		screenshot("LoginLinkTest");
		logger.info("loggerworked");
		test = report.createTest("LoginLinkTest");
		
	}
	
	
		
	
	
}
