package com.jungah.testautomation.selenium.datadirven.testcases;

import java.lang.reflect.Method;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jungah.testautomation.selenium.datadirven.utilities.TestUtil;
import com.jungah.testautomation.selenium.datadirven.base.TestBase;

import junit.framework.Assert;

public class OpenAccountTest extends TestBase{
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void openAccountTest(String customer, String currency) throws InterruptedException {
		
		click("openaccount_CSS");
		select("customer_CSS", customer );
		select("currency_CSS", currency );
		click("process_CSS");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		
	}
	
	@DataProvider
	public Object [][] getData() {
		
		String sheetName = "OpenAccountTest";
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
					
		Object[][] data = new Object[rows-1][cols];
		
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for(int colNum = 0; colNum < cols; colNum++) {
				//data[0][0]
				data[rowNum -2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;
	}
	

}
