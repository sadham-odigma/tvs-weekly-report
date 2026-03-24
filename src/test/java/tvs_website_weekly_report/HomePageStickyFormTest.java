package tvs_website_weekly_report;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HomePageStickyFormTest {

	@Parameters({ "url", "name", "email", "mobile" })
	@Test(enabled = true)
	public void stickyForm(String url, String name, String email, String mobile) {
		ChromeDriver driver = new ChromeDriver();
		DriverHolder.setDriver(driver);
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// open sticky form by adding 'active' class
		JavascriptExecutor js = driver;
		js.executeScript("document.querySelector('.stickyForm').classList.add('active');");

		// wait for form to be interactable
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#web_sticky_form input[name='name']")));

		// name field
		driver.findElement(By.cssSelector("#web_sticky_form input[name='name']")).sendKeys(name);
		// email field
		driver.findElement(By.cssSelector("#web_sticky_form input[name='email']")).sendKeys(email);
		// phone field
		driver.findElement(By.cssSelector("#web_sticky_form input[name='phone']")).sendKeys(mobile);

		// agree checkbox - set checked and dispatch change event
		js.executeScript(
				"var cb = document.getElementById('agree');" +
				"cb.checked = true;" +
				"cb.dispatchEvent(new Event('change', { bubbles: true }));" +
				"cb.dispatchEvent(new Event('click', { bubbles: true }));");

		// submit form
		WebElement submitButton = driver.findElement(By.id("enquire_now_btn"));
		js.executeScript("arguments[0].click();", submitButton);

		// wait for thankyou url
		wait.until(ExpectedConditions.urlContains("thankyou"));

		// thankyou page title
		String title = driver.getTitle();
		if (title.contains("Thank")) {
			System.out.println("Form Submitted Sucessfully");

		} else {
			System.out.println("Form Submission Failed");
		}
		driver.close();
	}
}
