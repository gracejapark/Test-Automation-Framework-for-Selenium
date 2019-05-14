package com.jungah.testautomation.selenium.datadirven.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.jungah.testautomation.selenium.datadirven.utilities.TestUtil;
import com.jungah.testautomation.selenium.datadirven.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener{

	@Override
	public void onTestStart(ITestResult arg0) {
		
		test = rep.startTest(arg0.getName().toUpperCase());
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		
		test.log(LogStatus.PASS, arg0.getName().toUpperCase() + " PASS");
		rep.endTest(test);
		rep.flush();
		
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
	
		System.setProperty("org.uncommons.reportng.escape-output","false");
		
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, arg0.getName().toUpperCase() + " Failed with exception : " + arg0.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\"href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\"href=\"+TestUtil.screenshotName+\"><img src=\"+TestUtil.screenshotName+\" width=\"80%\" height=\"80%\"></img></a>");
		rep.endTest(test);
		rep.flush();
		
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
