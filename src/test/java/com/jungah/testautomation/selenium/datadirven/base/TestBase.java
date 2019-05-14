package com.jungah.testautomation.selenium.datadirven.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.jungah.testautomation.selenium.datadirven.utilities.ExcelReader;
import com.jungah.testautomation.selenium.datadirven.utilities.ExtentManager;
import com.jungah.testautomation.selenium.datadirven.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {

	/*
	 * WebDrive Properties Logs ExtentReports DB Excel mail
	 *
	 */
	String propertiesPath = "\\\\src\\\\test\\\\resources\\\\properties\\\\";
	String excutablesPath = "\\\\src\\\\test\\\\resources\\\\executables\\\\";
	static String excelPath =  "\\src\\test\\resources\\excel\\";
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis = null;
	public static Logger log = LoggerFactory.getLogger(TestBase.class);
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + excelPath + "testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	
	
	@BeforeSuite
	public void setUp() {
		
		if (driver == null) {
			System.out.println(System.getProperty("user.dir"));
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + propertiesPath + "config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(System.getProperty("user.dir") + propertiesPath + "or.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(config.getProperty("browser"));
		}

		if (config.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + excutablesPath + "geckodriver.exe");
			driver = new FirefoxDriver();
			log.debug("filefox lauched!!!");
		} else if (config.getProperty("browser").equals("chrome")) {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + excutablesPath + "chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("chrome lauched!!!");
		}

		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to: " + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
				TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
	}
	
	public void click(String locator) {
		
		System.out.println("=======locator  "+ locator );
		if(locator.endsWith("_CSS")) {
			
		    
		    System.out.println("========OR.getProperty  " + OR.getProperty(locator));
		    
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
			
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		
		test.log(LogStatus.INFO, "Clicking on : " + locator);
	}
	
	public void type(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		
		test.log(LogStatus.INFO, "Typing in : "+locator+" entered value as "+value);
	}

	static WebElement dropdown;
	
	
	public void select(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		}else if(locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		}else if(locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, "Selecting from dropdown : "+ locator + " value as "+ value);
		
	}
	public boolean isElementPresent(By by) {

		try {

			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {

			return false;

		}
	}
	
	public static void verifyEqulas(String expected, String actual) throws IOException {
		
		try {
			
			Assert.assertEquals(actual, expected);
		}catch (Throwable t) {
			
			TestUtil.captureScreenshot();
			//ReportNG
			Reporter.log("<br>"+"Verification failure : "+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\"href=\"+TestUtil.screenshotName+\"><img src=\"+TestUtil.screenshotName+\" width=\"80%\" height=\"80%\"></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			//Extent Reports;
			test.log(LogStatus.FAIL, " Verification Failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		}
		
	}

	@AfterSuite
	public void tearDownl() {

		if (driver != null) {
			driver.quit();
		}

		log.debug("Test execution completed !!!");
	}
}
