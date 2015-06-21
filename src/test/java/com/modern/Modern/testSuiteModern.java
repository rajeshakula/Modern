package com.modern.Modern;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * JunitTestSuite it allows to execute all My testCases
 * @author Rajb
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	emailTest.class,
	TestPriceQuantity.class,
	TestProductCatlogOrder.class
	
})
public class testSuiteModern {

}
