package com.modern.cucumber.testRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * TestRunner
 * @author RajeshAkula
 *
 */
@CucumberOptions(
		plugin = {"pretty", 
				"html:target/cucumber", 
				"json:target/cucumber.json"},
		dryRun=false,
		strict=true,
		 monochrome = true,
		tags="@ProductPriceQuantity,@EmailSignUpTestCase,@ProductCatlogOrder",
		 //tags="@ProductPriceQuantity",
				//tags="@ProductCatlogOrder",
		 features="src\\test\\java\\modern.feature",
		 glue="com.modern.cucumber.stepdefinitions")

@RunWith(Cucumber.class)
public class ModernTestRunner {

}
