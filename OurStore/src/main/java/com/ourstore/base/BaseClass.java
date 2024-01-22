package com.ourstore.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;

public class BaseClass {

    public static Properties prop;
    public static WebDriver driver;

    @BeforeTest
    public void initialize() {
        loadConfig();
        initializeWebDriver();
    }

    private void loadConfig() {
        try {
            prop = new Properties();
            // Use System.getProperty("user.dir") to get the current project directory
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/Configuration/config.properties");
            prop.load(ip);
            System.out.println("driver: " + driver);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeWebDriver() {
        String browser = prop.getProperty("browser");

        // Check the browser type specified in the config.properties file and initialize the corresponding WebDriver
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver");
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/drivers/msedgedriver");
            driver = new EdgeDriver();
        } else {
            System.out.println("Browser specified in config.properties is not supported.");
        }

        driver.get(prop.getProperty("url"));
        // More conditions for other browsers (Safari, Opera, etc.) when needed
    }
}
