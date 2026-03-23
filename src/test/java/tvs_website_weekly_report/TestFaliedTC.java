package tvs_website_weekly_report;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestFaliedTC {

	public static void main(String[] args) {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.tvsemerald.com/lp/tvs-emerald-auralis/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		driver.findElement(By.id("form_input_name")).sendKeys("Test nimi");
		driver.findElement(By.id("form_input_email")).sendKeys("tedf@gmail.com");
		driver.findElement(By.id("form_input_phone")).sendKeys("9223200001");
		driver.findElement(By.className("form_submit_btn")).click();
	}

}
