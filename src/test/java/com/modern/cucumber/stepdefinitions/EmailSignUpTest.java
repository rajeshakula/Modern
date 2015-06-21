package com.modern.cucumber.stepdefinitions;

import java.util.Arrays;
import java.util.Random;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
/**
 * EmailSignUp TestCasess with Valid and Invalid data
 * If a customer give a valid Email should see "Email address subscribed" on the webPage
 * If a customer give a Invalid Email should see "Email address exists" on the webPage
 * @author RajeshAkula
 */
public class EmailSignUpTest {

	WebDriver driver;
	WebDriverWait wait;
	DesiredCapabilities capabilities = new DesiredCapabilities();
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	////////////////////////////////////////////////
	//////StepDefinitions//////////////////////////
	//////////////////////////////////////////////
	@Before("@EmailSignUpTestCase")
	public void before(Scenario scenario) {
		System.out
				.println("-----------------Starting scenario execution: " + scenario.getName());
		System.setProperty(
				"webdriver.chrome.driver",
				System.getProperty("user.dir")
						.concat("\\src\\main\\java\\chromeDriver\\32bit\\2.12\\chromedriver.exe"));
		capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches",
				Arrays.asList("--start-maximized"));
		driver = new ChromeDriver(capabilities);
		wait = new WebDriverWait(driver, 30);	
		driver.manage().window().maximize();
	}
	@Given("^customer navigate to modern web page$")
	public void navigateToPage() throws Throwable {	
		
		driver.get("http://www.modern.co.uk/");
		Assert.assertTrue(driver.getTitle().equals("Modern - Modern.co.uk"));
	}
	@Then("^enter the emailaddress (.*)$")
	public void enterEmail(String email) throws Throwable {
		WebElement emailField = driver.findElement(By.id("newsletter-email"));
		emailField.clear();
		if (email.equalsIgnoreCase("valid")) {
			email = getEmail();
			emailField.sendKeys(email);
			System.out.println("Entered Email address: " + email);
		} else {
			email = "1234@gmail.com";
			emailField.sendKeys(email);
			System.out.println("Entered Email address: " + email);
		}
	}
	@Then("^click on sunscribe button$")
	public void clickSubscribe() throws Throwable {
		driver.findElement(By.xpath("//*[@id=\"newsletter-signup\"]/button")).click();
	}

	@Then("^Verfiy the (.*)$")
	public void verfiyMessage(String message) throws Throwable {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("newsletter-signup-errormsg")));
			WebElement textBox=driver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div[1]/div"));
			Assert.assertEquals(message, textBox.getText());
	}
	
	@After("@EmailSignUpTestCase")
	public void after(Scenario scenario) {
		final String scenarioStatus = scenario.getStatus();
		System.out.println("Execution result: " + scenarioStatus);
		System.out.println("---------------Finished scenario execution: " + scenario.getName());
		driver.quit();
	}

	/**
	 * Method to get the randomString
	 * 
	 * @param length
	 * @return
	 */
	private String getRandomString(int length) {
		Random rG = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(alphabet.charAt(rG.nextInt(alphabet.length())));
		}
		return sb.toString();
	}
	/**
	 * Method to get the RandonEmailAddress
	 * 
	 * @return
	 */
	private String getEmail() {
		StringBuilder sb = new StringBuilder();
		sb.append(getRandomString(8));
		sb.append("@");
		sb.append(getRandomString(10));
		sb.append(".");
		sb.append(getRandomString(3));
		return sb.toString();
	}

}
