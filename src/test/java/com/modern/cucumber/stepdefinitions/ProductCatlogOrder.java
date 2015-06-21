package com.modern.cucumber.stepdefinitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
/*
 * @author RajeshAkula
 * This class describes changing the product sort order by price Low to High
 * Customer can able to see products from low price to high price on first 40 products   
 */
public class ProductCatlogOrder {

	WebDriver driver;
	WebDriverWait wait;
	ArrayList<Integer> gbp = new ArrayList<Integer>();
	DesiredCapabilities capabilities = new DesiredCapabilities();
	////////////////////////////////////////////////
	//////StepDefinitions//////////////////////////
	//////////////////////////////////////////////
	@Before("@ProductCatlogOrder")
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

	@Given("^customer navigate to modern  page$")
	public void navigateToPage() throws Throwable {
		
		driver.get("http://www.modern.co.uk/");
		Assert.assertTrue(driver.getTitle().equals("Modern - Modern.co.uk"));
	}

	@Then("^navigate to one of the productspage$")
	public void navigate_to_one_of_the_productspage() throws Throwable {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.partialLinkText("Handmade in UK")));
		driver.findElement(By.partialLinkText("Handmade in UK")).click();
		System.out.println("Clicked on HandMade in UK Option");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("showing")));
		Assert.assertTrue(driver.findElement(By.className("showing")).getText()
				.contains("Handmade in the UK"));
		System.out.println("HandMade in uk Page opened!!");
	}

	@Then("^select option price low to high$")
	public void select_option_price_low_to_high() throws Throwable {
		WebElement sortOrder = driver.findElement(By.className("sorts"));
		Select clickThis = new Select(sortOrder);
		clickThis.selectByIndex(0);
	}

	@Then("^Verfiy Sofas should sort out in an order price Low to High$")
	public void verfiy_Sofas_should_sort_out_in_an_order_price_Low_to_High()
			throws Throwable {

		WebElement productList = driver.findElement(By
				.cssSelector("div[class='items-list-container clearfix']"));
		// Number of Products on the Page
		List<WebElement> productImages = productList.findElements(By
				.className("products-list-item-container"));
		System.out.println("Products Displayed on the Page :"
				+ productImages.size());
		// iterate the values
		for (int i = 0; i < productImages.size(); i++) {
			WebElement priceDiv = productImages.get(i).findElement(
					By.tagName("table"));
			List<WebElement> tableRows = priceDiv
					.findElements(By.tagName("tr"));
			List<WebElement> tableData = tableRows.get(0).findElements(
					By.tagName("td"));
			String price = tableData.get(2).getText().replace("£", "");
			Integer price2 = Integer.parseInt(price);
			// System.out.println("originalPrice: " +price2);
			gbp.add(price2);
		}
		// Check the Condition Price Low to High
		for (int j = 1; j < gbp.size(); ++j) {
			Assert.assertTrue(gbp.get(j - 1) <= gbp.get(j));
		}
	}
	@After(" @ProductCatlogOrder")
	public void after(Scenario scenario) {
		final String scenarioStatus = scenario.getStatus();
		System.out.println("Execution result: " + scenarioStatus);
		System.out.println("---------------Finished scenario execution: " + scenario.getName());
		driver.quit();
	}
}
