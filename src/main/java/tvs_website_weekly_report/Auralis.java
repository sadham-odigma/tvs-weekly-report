package tvs_website_weekly_report;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Auralis {
	@Parameters({ "url", "name", "email", "mobile" })
	@Test
	public void bannerForm(String url, String name, String email, String mobile) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		// name field
					driver.findElement(By.id("form_input_name")).sendKeys(name);
					// email field
					driver.findElement(By.id("form_input_email")).sendKeys(email);
					// mob num filed
					driver.findElement(By.id("form_input_phone")).sendKeys(mobile);
					driver.findElement(By.id("tvs_auralis_lp_bannerForm_submit_btn")).click();

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

}}
