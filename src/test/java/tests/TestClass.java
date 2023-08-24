package tests;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class TestClass extends BaseClass {

    @Test
    public void makePaymentTest() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.name("usernameTextField")));
        getDriver().findElement(By.name("usernameTextField")).sendKeys("company");
        getDriver().findElement(By.name("passwordTextField")).sendKeys("company");
        getDriver().findElement(By.name("loginButton")).click();

        Thread.sleep(3000);
        
        Boolean isLogoutButtonPresent = getDriver().findElement(By.name("logoutButton")).isDisplayed();
        assertTrue(isLogoutButtonPresent);

        getDriver().findElement(By.name("makePaymentButton")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.name("cancelButton")));
        getDriver().findElement(By.name("cancelButton")).click();
        getDriver().findElement(By.name("logoutButton")).click();

        Thread.sleep(2000);

        Boolean isLoginButtonPresent = getDriver().findElement(By.name("loginButton")).isDisplayed();
        assertTrue(isLoginButtonPresent);
    }


}
