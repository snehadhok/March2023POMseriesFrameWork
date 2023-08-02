package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

/**
 * 
 * @author Admin
 *
 */

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This is used to initiliaze the driver
	 * 
	 * @param browserName
	 * @return it returns driver
	 */
	public WebDriver initDriver(Properties prop) {
		//String browserName = System.getProperty(highlight);
		String browserName = prop.getProperty("browser");

		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		System.out.println("browserName is " + browserName);
		switch (browserName.toLowerCase()) {
		case "chrome":
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			// driver = new SafariDriver();
			break;

		default:
			System.out.println("Plz pass the right browser...." + browserName);
			break;
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used to init the properties
	 * 
	 * @return
	 */

	public Properties initProp() {
		// maven clean install -Denv
		FileInputStream ip = null;
		prop = new Properties();
		String envName = System.getProperty("env");
		System.out.println("env name is:" + envName);
		
		try {
		if (envName==null) {
			System.out.println("no env is giving.... hence running it on QA env..");
			ip =  new FileInputStream("./src\\test\\resources\\config\\qa.config.properties");		
		}
		else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip =  new FileInputStream("./src\\test\\resources\\config\\qa.config.properties");
			break;
			case "dev":
				ip =  new FileInputStream("./src\\test\\resources\\config\\dev.config.properties");
			break;
			case "uat":
				ip =  new FileInputStream("./src\\test\\resources\\config\\uat.config.properties");
			break;
			case "stage":
				ip =  new FileInputStream("./src\\test\\resources\\config\\stage.config.properties");
			break;
			case "prod":
				ip =  new FileInputStream("./src\\test\\resources\\config\\config.properties");
			break;

			default:
				System.out.println("plz pass the right env name..:"+envName);
				break;
			}
		}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+methodName+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	} 
	
	
	

}
