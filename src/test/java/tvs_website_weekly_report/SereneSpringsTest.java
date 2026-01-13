package tvs_website_weekly_report;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SereneSpringsTest {
	@Parameters({ "url", "name", "email", "mobile" })
	@Test(enabled = true)
	public void siteVisitForm(String url, String name, String email, String mobile) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// Scroll
		Actions scroll = new Actions(driver);
		WebElement footerForm = driver.findElement(By.xpath("//form[@id='footer_form']"));
		scroll.scrollToElement(footerForm).perform();
		// footerForm.click();

		// name field
		driver.findElement(By.xpath("(//input[@id='banner_input_name'])[2]")).sendKeys(name);
		// email field
		driver.findElement(By.xpath("(//input[@id='banner_input_email'])[2]")).sendKeys(email);
		// mob num filed
		driver.findElement(By.xpath("(//input[@id='banner_input_phone'])[2]")).sendKeys(mobile);
		driver.findElement(By.xpath("(//input[@id='banner_input_submit'])[2]")).click();

		// wait for thankyou url
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
