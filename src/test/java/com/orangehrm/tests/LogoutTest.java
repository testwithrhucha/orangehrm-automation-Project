package com.orangehrm.tests;

import com.orangehrm.tests.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LogoutTest extends BaseTset {

    @Test
    public void verifyLogoutFunctionality() {
        // Step 1: Login with valid credentials
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        // Step 2: Wait for user dropdown menu
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement userMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-userdropdown-tab")));

        // Step 3: Click user dropdown
        userMenu.click();

        // Step 4: Click Logout link
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='Logout']")));
        logoutLink.click();

        // Step 5: Assert that we’re redirected to login page
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("username")));
        Assert.assertTrue(usernameField.isDisplayed(), "Login page not displayed after logout");
    }
}
