package tvs_website_weekly_report;

import org.openqa.selenium.WebDriver;

/**
 * Holds the WebDriver instance per thread so the ExtentReportListener
 * can access it for taking screenshots on failure.
 *
 * HOW TO USE:
 * In your test, right after creating the driver, call:
 *     DriverHolder.setDriver(driver);
 *
 * The listener will call DriverHolder.getDriver() to take screenshots.
 */
public class DriverHolder {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static void setDriver(WebDriver webDriver) {
		driver.set(webDriver);
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void removeDriver() {
		driver.remove();
	}
}
