package tvs_website_weekly_report;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Udyana {

	@Parameters({ "url", "name", "email", "mobile" })
	@Test(enabled = false)
	public void eqnowForm(String url, String name, String email, String mobile) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// scroll down
		JavascriptExecutor js = driver;
		

		// Actions scroll = new Actions(driver);
//		 WebElement enquireBtn = wait.until(
//			        ExpectedConditions.presenceOfElementLocated(
//			            By.xpath("//button[normalize-space()='Enquire Now']")
//			        )
//			    );
		// scroll.scrollToElement(enqnowForm).perform();
//		js.executeScript("arguments[0].scrollIntoView({block:'center'});", enquireBtn);
//		js.executeScript("arguments[0].click();", enquireBtn);

//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@name='name'])[1]")));
		// name field
		driver.findElement(By.name("name")).sendKeys(name);
		// email field
		driver.findElement(By.name("email")).sendKeys(email);
		// mob num filed
		driver.findElement(By.name("phone")).sendKeys(mobile);
		driver.findElement(By.id("U_LP_Banner_Form_Submit_btn")).click();
//		WebElement submitBtn = driver.findElement(By.id("U_LP_Banner_Form_Submit_btn"));
//	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", submitBtn);
//	    wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
//	    js.executeScript("arguments[0].click();", submitBtn);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.urlContains("thankyou.php"));

		// thankyou page title
		String title = driver.getTitle();
		if (title.contains("Thank You")) {
			System.out.println("Form Submitted Sucessfully");

		} else {
			System.out.println("Form Submission Failed");
		}
		driver.close();

	}

}
