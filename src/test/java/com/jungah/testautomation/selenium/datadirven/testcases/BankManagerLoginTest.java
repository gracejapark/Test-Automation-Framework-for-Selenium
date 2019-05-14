package com.jungah.testautomation.selenium.datadirven.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jungah.testautomation.selenium.datadirven.base.TestBase;
import com.relevantcodes.extentreports.ExtentTest;
public class BankManagerLoginTest extends TestBase {


	@Test
	public void loginAsBankManager() throws InterruptedException, IOException {
		
		verifyEqulas("abc", "xyz");
		Thread.sleep(3000);
		log.debug("Inside Login Test");
		click("bmlBtn_CSS");
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successful");
		
		log.debug("Login successfully executed");
		
		//나중에 꼭 다시 한번 보면서 이해하기!!!
		//Assert.fail("Login not successful");
		
		Reporter.log("Login successfully executed");
		Reporter.log("<a target=\"_blank\"href=\"file:///C:\\Project\\screenshot\\error.jpg\">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\"href=\"file:///C:\\Project\\screenshot\\error.jpg\"><img src=\"file:///C:\\Project\\screenshot\\error.jpg\" width=\"80%\" height=\"80%\"></img></a>");
		
	}
}
