package tvs_website_weekly_report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * TestNG Listener that generates an ExtentReport HTML file.
 *
 * HOW IT WORKS:
 * 1. onStart()       → called once before any test runs
 *                       → creates the ExtentSparkReporter (HTML file)
 *                       → configures report title, theme, etc.
 *
 * 2. onTestStart()   → called before each @Test method
 *                       → creates an ExtentTest entry in the report
 *
 * 3. onTestSuccess()  → called when a test passes → logs PASS
 *    onTestFailure()  → called when a test fails  → logs FAIL + error
 *    onTestSkipped()  → called when a test skips   → logs SKIP
 *
 * 4. onFinish()       → called once after all tests complete
 *                       → flushes (writes) the report to the HTML file
 *
 * REGISTRATION:
 * Add this listener in seq.xml:
 *   <listeners>
 *       <listener class-name="tvs_website_weekly_report.ExtentReportListener"/>
 *   </listeners>
 */
public class ExtentReportListener implements ITestListener {

	// One report instance for the entire suite
	private ExtentReports extent;

	// ThreadLocal so each test thread gets its own ExtentTest
	// (safe even though tests run sequentially — good practice)
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {
		// --- STEP 1: Create the HTML report file with a timestamp ---
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String reportPath = "test-output/ExtentReport_" + timestamp + ".html";

		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

		// --- STEP 2: Configure report appearance ---
		sparkReporter.config().setDocumentTitle("TVS Emerald Test Report");
		sparkReporter.config().setReportName("Weekly Form Submission Report");
		sparkReporter.config().setTheme(Theme.STANDARD);

		// --- STEP 3: Attach reporter to ExtentReports ---
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		// --- STEP 4: Add system/environment info (shown in report dashboard) ---
		extent.setSystemInfo("Project", "TVS Emerald Website");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Environment", "Production");

		System.out.println("ExtentReport initialized: " + reportPath);
	}

	@Override
	public void onTestStart(ITestResult result) {
		// Create a new test entry in the report using class name + method name
		String testName = result.getTestClass().getRealClass().getSimpleName()
				+ " - " + result.getMethod().getMethodName();
		ExtentTest extentTest = extent.createTest(testName);
		test.set(extentTest);

		test.get().log(Status.INFO, "Test Started: " + testName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.get().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
		test.get().log(Status.FAIL, "Error: " + result.getThrowable().getMessage());

		// --- SCREENSHOT ON FAILURE ---
		// 1. Get the WebDriver instance from the test class using reflection
		// 2. Take screenshot and save to screenshots/ folder
		// 3. Attach screenshot to the ExtentReport
		try {
			Object testClass = result.getInstance();
			// Look for a "driver" field in the test class
			java.lang.reflect.Field driverField = null;
			for (java.lang.reflect.Field field : testClass.getClass().getDeclaredFields()) {
				if (WebDriver.class.isAssignableFrom(field.getType())) {
					driverField = field;
					break;
				}
			}

			if (driverField == null) {
				// Driver is a local variable — not accessible via reflection
				// We use a static ThreadLocal holder instead
				WebDriver driver = DriverHolder.getDriver();
				if (driver != null) {
					captureScreenshot(driver, result.getMethod().getMethodName());
				}
			} else {
				driverField.setAccessible(true);
				WebDriver driver = (WebDriver) driverField.get(testClass);
				if (driver != null) {
					captureScreenshot(driver, result.getMethod().getMethodName());
				}
			}
		} catch (Exception e) {
			test.get().log(Status.WARNING, "Could not capture screenshot: " + e.getMessage());
		}
	}

	/**
	 * Takes a screenshot, saves it to screenshots/ folder,
	 * and attaches it to the ExtentReport.
	 */
	private void captureScreenshot(WebDriver driver, String methodName) {
		try {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			String screenshotPath = "test-output/screenshots/" + methodName + "_" + timestamp + ".png";

			// Create screenshots directory if it doesn't exist
			new File("test-output/screenshots").mkdirs();

			// Take screenshot
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(screenshotPath);
			FileUtils.copyFile(srcFile, destFile);

			// Attach to report using Base64 (embedded in HTML — no broken image links)
			String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			test.get().fail("Screenshot at failure:",
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());

			System.out.println("Screenshot saved: " + screenshotPath);
		} catch (IOException e) {
			test.get().log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
	}

	@Override
	public void onFinish(ITestContext context) {
		// --- FINAL STEP: Write the report to the HTML file ---
		extent.flush();
		System.out.println("ExtentReport generated successfully!");
	}
}
