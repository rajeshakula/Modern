package com.modern.cucumber.stepdefinitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
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
 * This is the test class for Price Quantity,While changing the quantity of the product 
 * total amount should be reflected accordingly
 * Ex : Price for 1 = £500,
 * Ex : Price for 2 = £1000...etc 
 */
public class PriceQuantityTest {
	WebDriver driver;
	WebDriverWait wait;
	ArrayList<Integer> gbp = new ArrayList<Integer>();
	DesiredCapabilities capabilities = new DesiredCapabilities();
	private String sofaName;
	private String sofaPrice;
	private long sofaPriceInteger;

	// //////////////////////////////////////////////
	// ////StepDefinitions//////////////////////////
	// ////////////////////////////////////////////
	@Before("@ProductPriceQuantity")
	public void before(Scenario scenario) {
		System.out.println("-----------------Starting scenario execution: "
				+ scenario.getName());
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

	@Given("^customer navigate to Modern page$")
	public void customer_navigate_to_Modern_page() throws Throwable {
		driver.get("http://www.modern.co.uk/");
		Assert.assertTrue(driver.getTitle().equals("Modern - Modern.co.uk"));
	}
	
	@Then("^navigate to one of the tab$")
	public void clickOnTab() throws Throwable {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.partialLinkText("Handmade in UK")));
		driver.findElement(By.partialLinkText("Handmade in UK")).click();
		// Verify HandMade page Open
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("showing")));
		Assert.assertTrue(driver.findElement(By.className("showing")).getText()
				.contains("Handmade in the UK"));
	    
	}
	@Then("^click on one of the sofa$")
	public void clickOnSofa() throws Throwable {
		// items-list-container clearfix
		WebElement productList = driver.findElement(By
				.xpath("//*[@id=\"mod_block_products_list\"]/div[2]"));
		List<WebElement> productImages = productList.findElements(By
				.className("products-list-item-container"));
		System.out.println("Products Displayed on the Page :"
				+ productImages.size());
		for (int i = 0; i < 1; i++) {
			// Click on Product
			productImages.get(i).click();
		}
		// Get the Product Name
		WebElement productName = driver.findElement(By.className("part1"));
		this.sofaName = productName.getText();
		System.out.println("SofaName: " + this.sofaName);
		// Get the Product Price
		WebElement productPrice = driver.findElement(By.className("part2"));
		WebElement price = productPrice.findElement(By.tagName("b"));
		this.sofaPrice = price.getText().trim();
		System.out.println("SofaPrice for 1 Quantity :" + sofaPrice);
		this.sofaPriceInteger = Long.parseLong(this.sofaPrice.trim().replace(
				"£", "").replace(".00", ""));
	}

	@Then("^click on the Add to basket$")
	public void click_on_the_Add_to_basket() throws Throwable {
		driver.findElement(By.id("ws-btnaddcart")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("dropdown-basket")));
		// Click on Basket
		driver.findElement(By.partialLinkText("Basket")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.tagName("h1")));
	}

	@Then("^verfiy price vs quantity$")
	public void verfiy_price_vs_quantity() throws Throwable {
		for (int j = 2; j < 10; j++) {
			// quantity
			WebElement quantity = driver.findElement(By.name("quantity"));
			Select quantityDropdown = new Select(quantity);
			quantityDropdown.selectByIndex(j);
			System.out.println("Selected qunatity as --> " + j);
			if (isAlertPresent()) {
				Alert alert = driver.switchTo().alert();
				driver.switchTo().alert();
				alert.accept();
				// wait.until(ExpectedConditions.alertIsPresent());
			}
			Thread.sleep(200);
			// ListOfTables
			List<WebElement> tableList = driver.findElements(By
					.tagName("table"));
			List<WebElement> tableRow = tableList.get(3).findElements(
					By.tagName("tr"));
			List<WebElement> tableData = tableRow.get(2).findElements(
					By.tagName("td"));
			System.out.println("Price for Sofa quantity :" + j + "  ---> : "
					+ tableData.get(3).getText());
			String totalPrice = tableData.get(3).getText().trim()
					.replace("£", "").replace(".00", "").replace(",", "");
			int priceInteger = Integer.parseInt(totalPrice.trim());
			Assert.assertEquals(priceInteger, j * sofaPriceInteger);
		}
	}

	@After("@ProductPriceQuantity")
	public void after(Scenario scenario) {
		final String scenarioStatus = scenario.getStatus();
		System.out.println("Execution result: " + scenarioStatus);
		System.out.println("---------------Finished scenario execution: "
				+ scenario.getName());
		driver.quit();
	}
	/**
	 * check if alert present
	 */
	private boolean isAlertPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}
}
