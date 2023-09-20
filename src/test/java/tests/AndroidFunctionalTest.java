
package tests;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import java.net.MalformedURLException;

import static org.testng.Assert.assertTrue;

public class AndroidFunctionalTest extends BaseClass {

    @Test
    public void makePaymentTest() throws InterruptedException {
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/usernameTextField']")));
        getDriver().findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/usernameTextField']")).sendKeys("company");
        getDriver().findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/passwordTextField']")).sendKeys("company");
        getDriver().findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/loginButton']")).click();

        Thread.sleep(3000);
        
        Boolean isLogoutButtonPresent = getDriver().findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/logoutButton']")).isDisplayed();
        assertTrue(isLogoutButtonPresent);

        getDriver().findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/makePaymentButton']")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/cancelButton']")));
        getDriver().findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/cancelButton']")).click();
        getDriver().findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/logoutButton']")).click();

        Thread.sleep(2000);

        Boolean isLoginButtonPresent = getDriver().findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/loginButton']")).isDisplayed();
        assertTrue(isLoginButtonPresent);
    }


}
