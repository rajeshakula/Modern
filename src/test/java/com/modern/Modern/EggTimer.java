package com.modern.Modern;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EggTimer {
	WebDriver driver;
	WebDriverWait wait;
	DesiredCapabilities capabilities = new DesiredCapabilities();

	/* Setup the Browser for Every Test */
	@Before
	public void setUp() {
		System.setProperty(
				"webdriver.chrome.driver",
				System.getProperty("user.dir")
						.concat("\\src\\main\\java\\chromeDriver\\32bit\\2.12\\chromedriver.exe"));
		capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches",
				Arrays.asList("--start-maximized"));
		driver = new ChromeDriver(capabilities);
		wait = new WebDriverWait(driver, 30);
		driver.get("http://e.ggtimer.com/");
		driver.manage().window().maximize();
		Assert.assertTrue(driver.getTitle().equals(
				"E.gg Timer - a simple countdown timer"));
	}

	/* TearDown BrowserSession */
	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void testTimer() throws NumberFormatException {
		try {
			WebElement timerInputFiled = driver.findElement(By
					.id("start_a_timer"));
			timerInputFiled.clear();
			timerInputFiled.sendKeys("10");
			driver.findElement(By.id("timergo")).click();
			// get the Timer
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.tagName("h2")));
			WebElement progressText = driver.findElement(By.tagName("h2"));
			String timerString = progressText.getText().replace("seconds", "");
			// String timerString=progressText.getText();
			System.out.println("progressText: " + timerString.trim());
			int j = Integer.parseInt(timerString.trim());

			for (int i = 0; i < j; i++) {
				WebElement progressText2 = driver.findElement(By.tagName("h2"));
				String timerString2 = progressText2.getText().replace(
						"seconds", "");
				int timerInt2 = Integer.parseInt(timerString2.trim());
				System.out.println("NewProgressText: " + timerInt2);
				Assert.assertEquals(j-1, j);
				j=j-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
