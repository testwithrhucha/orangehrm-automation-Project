package com.orangehrm.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

import com.aventstack.extentreports.*;
import com.orangehrm.tests.BaseTset;
import com.orangehrm.tests.ExtentManager;

public class LoginTest extends BaseTset {

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void startReport() {
        extent = ExtentManager.getReportInstance();
        test = extent.createTest("Login Test");
    }

    @AfterClass
    public void endReport() {
        extent.flush();
    }

    @Test(priority = 1)
    public void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        boolean passed = driver.getCurrentUrl().contains("dashboard");
        test.info("Logging in with valid credentials");

        try {
            Assert.assertTrue(passed);
            test.pass("Login successful, dashboard page loaded");
        } catch (AssertionError e) {
            test.fail("Login failed or dashboard not found");
            throw e;
        }
    }

    @Test(priority = 2)
    public void loginWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalidUser", "invalidPass");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By errorLocator = By.xpath("//p[contains(@class,'oxd-alert-content-text')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));
        String actualMsg = driver.findElement(errorLocator).getText();

        test.info("Logging in with invalid credentials");

        try {
            Assert.assertTrue(actualMsg.contains("Invalid credentials"));
            test.pass("Proper error message shown: " + actualMsg);
        } catch (AssertionError e) {
            test.fail("Expected error message not found");
            throw e;
        }
    }
}
