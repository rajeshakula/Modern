package com.modern.Modern;

import java.util.List;

import junit.framework.Assert;

import org.apache.regexp.recompile;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
/**
 * Test for PriceCheck with Product Quantity
 * @author Rajb
 *
 */
public class TestPriceQuantity extends BaseTest {
	
	private String sofaName;
	private String sofaPrice;
	private String quantity=null;
	
	@Test
	public void testPriceQuantity() {
		try{
		//Check the Product Price and Quantity 
		System.out.println("TestCase2: Testing Product Price and Quantity ");
		// click on HandMade in UK
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.partialLinkText("Handmade in UK")));
		driver.findElement(By.partialLinkText("Handmade in UK")).click();
		// Verify HandMade page Open
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("showing")));
		Assert.assertTrue(driver.findElement(By.className("showing")).getText()
				.contains("Handmade in the UK"));
		// items-list-container clearfix
		WebElement productList = driver.findElement(By
				.xpath("//*[@id=\"mod_block_products_list\"]/div[2]"));
		List<WebElement> productImages = productList.findElements(By
				.className("products-list-item-container"));
		System.out.println("Products Displayed on the Page :"
				+ productImages.size());
		for (int i = 0; i < 1; i++) {
			//Click on Product 
			productImages.get(i).click();
		}
			//Get the Product Name 
			WebElement productName=driver.findElement(By.className("part1"));
			sofaName=productName.getText();
			System.out.println("SofaName: " +sofaName);
			//Get the Product Price
			WebElement productPrice=driver.findElement(By.className("part2"));
			WebElement price=productPrice.findElement(By.tagName("b"));
		//	sofaPrice=price.getText().trim().replace("£", "");
			sofaPrice=price.getText().trim();
			System.out.println("SofaPrice for 1 Quantity :" + sofaPrice);
			int sofaPriceInteger=Integer.parseInt(sofaPrice.trim().replace("£", ""));
			//Click addBasket
			driver.findElement(By.id("ws-btnaddcart")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown-basket")));
			//Click on Basket
			driver.findElement(By.partialLinkText("Basket")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
			
			for(int j=2;j<10;j++){
			//quantity
			WebElement quantity=driver.findElement(By.name("quantity"));
			Select quantityDropdown=new Select(quantity);
			quantityDropdown.selectByIndex(j);
			System.out.println("Selected qunatity as --> " +j);
			if(isAlertPresent()){
				 Alert alert = driver.switchTo().alert();
				driver.switchTo().alert();
				alert.accept();	
				//wait.until(ExpectedConditions.alertIsPresent());
			}
	        Thread.sleep(200);
	        //ListOfTables
	        List<WebElement> tableList=driver.findElements(By.tagName("table"));
	        List<WebElement>tableRow=tableList.get(3).findElements(By.tagName("tr"));
	        List<WebElement>tableData=tableRow.get(2).findElements(By.tagName("td"));
	        System.out.println("Price for Sofa quantity :"+ j + "  ---> : " + tableData.get(3).getText());
	        String totalPrice=tableData.get(3).getText().trim().replace("£", "");
	        int priceInteger=Integer.parseInt(totalPrice.trim());
	        Assert.assertEquals(priceInteger, j * sofaPriceInteger);
	        
	        
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}	
	/**
	 * check if alert present 
	 */
	private boolean isAlertPresent(){
		try{	
	        Alert alert = driver.switchTo().alert();        
			return true ;
		} catch(NoAlertPresentException e){
			return false;
		}
	}
	
	
}
