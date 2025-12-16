package tvs_website_weekly_report;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Udyana {

	@Parameters({ "url", "name", "email", "mobile" })
	@Test
	public void eqnowForm (String url, String name, String email, String mobile) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// scroll down

		Actions scroll = new Actions(driver);
		WebElement enqnowForm = driver.findElement(By.xpath("//a[@id='udyana_ph_3_lp_enquire_now_btn']"));
		scroll.scrollToElement(enqnowForm).perform();
		enqnowForm.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@name='name'])[1]")));
		// name field
		driver.findElement(By.xpath("(//input[@name='name'])[1]")).sendKeys("test");
		// email field
		driver.findElement(By.name("email")).sendKeys(email);
		// mob num filed
		driver.findElement(By.name("phone")).sendKeys(mobile);
		driver.findElement(By.id("udyana_lp_submit_enquiry")).click();

		// wait for thankyou url
		// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
