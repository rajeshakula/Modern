package com.modern.Modern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;
import net.sf.cglib.core.EmitUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * ProductCatlogOrder test from dropDown
 * @author Rajb
 *
 */
public class TestProductCatlogOrder extends BaseTest{
	
	ArrayList<Integer> gbp=new ArrayList<Integer>();

	@Test
	public void testProductCatlogOrder() throws InterruptedException {
		System.out.println("TestCase: Testing ProductCatlog Order");
		// click on Handmade in UK
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.partialLinkText("Handmade in UK")));
		driver.findElement(By.partialLinkText("Handmade in UK")).click();
		System.out.println("Clicked on HandMade in UK Option");
		// Verfiy Handmade page Opend
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("showing")));
		Assert.assertTrue(driver.findElement(By.className("showing")).getText()
				.contains("Handmade in the UK"));
		System.out.println("HandMade in uk Page opened!!");
		WebElement sortOrder = driver.findElement(By.className("sorts"));
		Select clickThis = new Select(sortOrder);
		clickThis.selectByIndex(0);
		// items-list-container clearfix
		WebElement productList = driver.findElement(By
				.cssSelector("div[class='items-list-container clearfix']"));
		//Number of Products on the Page
		List<WebElement> productImages = productList.findElements(By
				.className("products-list-item-container"));
		System.out.println("Products Displayed on the Page :"
				+ productImages.size());
		//iterate the values
		for (int i = 0; i < productImages.size(); i++) {
			WebElement priceDiv = productImages.get(i).findElement(
					By.tagName("table"));
			List<WebElement> tableRows=priceDiv.findElements(By.tagName("tr"));
			List<WebElement> tableData=tableRows.get(0).findElements(By.tagName("td"));
				String price=tableData.get(2).getText().replace("£", "");
				Integer price2=Integer.parseInt(price);
				//System.out.println("originalPrice: " +price2);
				gbp.add(price2);
		}
		//Check the Condition Price Low to High 
		for(int j=1;j<gbp.size();++j){
			Assert.assertTrue(gbp.get(j-1)<=gbp.get(j));
		}
		
	}
	
	
}
