package tests;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class STAW {

    private String accessKey = "";
    private IOSDriver<IOSElement> driver = null;
    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        desiredCapabilities.setCapability("accessKey", accessKey);
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, "3cf62fb35d303b19b98d2b94e7ee06f582d2cf6a");
        desiredCapabilities.setCapability("dontGoHomeOnQuit", true);
        driver = new IOSDriver<>(new URL("https://uscloud.experitest.com/wd/hub"), desiredCapabilities);
    }

    @Test
    public void testing() {
        // Hello
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
