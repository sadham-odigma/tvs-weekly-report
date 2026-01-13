package tvs_website_weekly_report;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ContactUsPageTest {

	@Parameters({"url", "name", "email", "mobile"})
    @Test(enabled = true)
    public void testContactUs(String url, String name, String email, String mobile) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// name field
		driver.findElement(By.name("name")).sendKeys(name);
		// email field
		driver.findElement(By.name("email")).sendKeys(email);
		// mob num field
		driver.findElement(By.name("phone")).sendKeys(mobile);

		// Select dropdown
		WebElement projectDropdown = driver.findElement(By.name("project_name"));
		Select projectDD = new Select(projectDropdown);
		projectDD.selectByValue("Udyana at TVS Emerald Aaranya");

		// submit button
		WebElement submitButton = driver.findElement(By.id("enquire_now_btn"));
		JavascriptExecutor js = driver;
		js.executeScript("arguments[0].click();", submitButton);
		//wait for thankyou url
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        wait.until(ExpectedConditions.urlContains("thankyou"));
	        
			//thankyou page title 
			String title = driver.getTitle();
			if (title.contains("Thankyou")) {
				System.out.println("Form Submitted Sucessfully");
				
			}
			else {
				System.out.println("Form Submission Failed");
			}
			driver.close();
	}

}
