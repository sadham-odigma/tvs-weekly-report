package tvs_website_weekly_report;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestPractise {

	public static void main(String[] args) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.tvsemerald.com/lp/tvs-emerald-udyana-phase-3/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		

		JavascriptExecutor js = driver;
		Actions scroll = new Actions(driver);
		WebElement enqnowForm = driver.findElement(By.xpath("//a[@id='udyana_ph_3_lp_enquire_now_btn']"));
		scroll.scrollToElement(enqnowForm).perform();
		
		js.executeScript("arguments[0].click();", enqnowForm);
		//enqnowForm.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@name='name'])[1]")));
		// name field
		driver.findElement(By.xpath("(//input[@name='name'])[1]")).sendKeys("test");
		// email field
		driver.findElement(By.name("email")).sendKeys("sgsdsd@gmail.com");
		// mob num filed
		driver.findElement(By.name("phone")).sendKeys("9569569655");
		//driver.findElement(By.id("udyana_lp_submit_enquiry")).click();

	}

}
