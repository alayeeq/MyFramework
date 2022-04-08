package testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicTestScript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    System.setProperty("webdriver.chrome.driver","C:\\Users\\NagarajaK\\Desktop\\LP4\\SleniumIDE\\chromedriver_win32\\chromedriver.exe");
	    WebDriver driver = new ChromeDriver();
	    driver.manage().window().maximize();
	    driver.get("https://w3.ibm.com/buyatibm/");
	    
	    WebElement mailId =driver.findElement(By.id("mobile"));
	    WebElement pwd=driver.findElement(By.name("password"));
	    
	    System.out.println("id "+mailId.getText());
	    System.out.println("pwd"+pwd.getText());
	    
		    
	    WebElement signIn =driver.findElement(By.xpath("//button[@id='btn_signin']"));
	    signIn.click();

	    //WebElement launch=driver.findElement(By.xpath("//button[@id='btn_signin']"));
	    //launch.click();
	}
}
