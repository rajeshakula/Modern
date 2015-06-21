package com.modern.Modern;

import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * EmailSignUp test with valid and Invalid data
 * @author Rajb
 *
 */
public class emailTest extends BaseTest{
	
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	/**
	 * EmailSignup Test  with InvalidData
	 */
	@Test
	public void testEmailSignUpWithInvaliData(){
		try{
		final String email ="1234@gmail.com";
		System.out.println("TestCase 3: Testing EmailSignUp Functionality with invalid Data");
		WebElement emailField=driver.findElement(By.id("newsletter-email"));
		emailField.clear();
		//same Email
		emailField.sendKeys(email);
		System.out.println("Entered Email address: " +email);
		//click on Subscribe
		driver.findElement(By.xpath("//*[@id=\"newsletter-signup\"]/button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("newsletter-signup-errormsg")));
		WebElement message=driver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div[1]/div"));
		Assert.assertEquals("Email address exists", message.getText());
		System.out.println("Email address exists ,TestCasePass");
		} catch(Exception e){
			e.printStackTrace();		
		}
	}
	/**
	 * EmailSignup Test with ValidData
	 */
	@Test
	public void testEmailSignUpWithValidData(){
		try{
		final String email=getEmail();
		System.out.println("TestCase 3: Testing EmailSignUp Functionality with Valid Data");
		WebElement emailField=driver.findElement(By.id("newsletter-email"));
		emailField.clear();
		//Random Email address Every Time 
		emailField.sendKeys(email);
		System.out.println("Entered Email address: " +email);
		//click on Subscribe
		driver.findElement(By.xpath("//*[@id=\"newsletter-signup\"]/button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("newsletter-signup-errormsg")));
		WebElement message=driver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div[1]/div"));
		Assert.assertEquals("Email address subscribed", message.getText());
		System.out.println("Email address subscribed!! ,TestCasePass");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to get the randomString
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
