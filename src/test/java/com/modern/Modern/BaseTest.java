package com.modern.Modern;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * BaseTest Class ,Extend to all Test Class's
 * @author Rajb
 *
 */
public class BaseTest {

	 public WebDriver driver;
	 public WebDriverWait wait;
	 DesiredCapabilities capabilities = new DesiredCapabilities();
	/* Setup the Browser for Every Test */
	@Before
	public  void setUp() {
		System.setProperty(
				"webdriver.chrome.driver",
				System.getProperty("user.dir")
						.concat("\\src\\main\\java\\chromeDriver\\32bit\\2.12\\chromedriver.exe"));
		capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches",
				Arrays.asList("--start-maximized"));
		driver = new ChromeDriver(capabilities);
		wait = new WebDriverWait(driver, 30);
		driver.get("http://www.modern.co.uk/");
		driver.manage().window().maximize();
		Assert.assertTrue(driver.getTitle().equals("Modern - Modern.co.uk"));
	}
	/* TearDown BrowserSession */
	@After
	public  void tearDown() {
			driver.quit();
	}

}
