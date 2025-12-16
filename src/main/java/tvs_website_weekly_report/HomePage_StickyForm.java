package tvs_website_weekly_report;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage_StickyForm {

	public static void main(String[] args) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.tvsemerald.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// CLICK sticky opening button
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("_enquire_now"))).click();

        // Fill the form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Test User");
		//email field
		driver.findElement(By.name("email")).sendKeys("Testrock@gmail.com");
		//mob num filed
		driver.findElement(By.name("phone")).sendKeys("9876567822");
		//submit button
		driver.findElement(By.id("enquire_now_btn")).click();
		
		 // WAIT for URL to become thankyou page
       
		 wait.until(ExpectedConditions.urlContains("thankyou"));
        
		//thankyou page title 
		String title = driver.getTitle();
		if (title.contains("Thank you")) {
			System.out.println("Form Submitted Sucessfully");
			
		}
		else {
			System.out.println("Form Submission Failed");
		}
		driver.close();
	}

}
