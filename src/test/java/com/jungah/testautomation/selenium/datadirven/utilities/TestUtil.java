package com.jungah.testautomation.selenium.datadirven.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.jungah.testautomation.selenium.datadirven.base.TestBase;
import org.apache.commons.io.FileUtils;

public class TestUtil extends TestBase {
	
	public static String screenshotPath;
	public static String screenshotName;
	
	public static void captureScreenshot() throws IOException {
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		Date d = new Date();
		screenshotName =  d.toString().replace(":", "_").replace(" ","_" )+".jpg";
		
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+ screenshotName));
			
	}
	
	@DataProvider(name="dp")
	public Object [][] getData(Method m) {
		
		String sheetName = m.getName();
		System.out.println("sheetName: " + sheetName);
		
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
					
		System.out.println("rows: " + rows);
		System.out.println("cols: " + cols);
		
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


