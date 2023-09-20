package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseClass {

    protected ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    protected ThreadLocal<DesiredCapabilities> desiredCapabilities = new ThreadLocal<>();

    public AppiumDriver getDriver() {
        return driver.get();
    }

    public WebDriverWait getWait() {
        return wait.get();
    }

    @BeforeMethod
    @Parameters({"platform"})
    public void setUp(String platform, @Optional Method method) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("accessKey", System.getenv("ACCESS_KEY"));
        caps.setCapability("Jenkins_Build_Number", System.getenv("BUILD_NUMBER"));
        caps.setCapability("testName", method.getName());

        if (platform.equalsIgnoreCase("iOS")) {

            caps.setCapability("automationName", "XCUITest");
            caps.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
//            caps.setCapability("deviceQuery", "@serialnumber='\"" + udid + "\"'");
            caps.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
            caps.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
            caps.setCapability("autoAcceptAlerts", true);

            desiredCapabilities.set(caps);
            driver.set(new IOSDriver(new URL(System.getenv("CLOUD_URL") + "/wd/hub"), caps));

        } else if (platform.equalsIgnoreCase("Android")) {

            // caps.setCapability("orientation", "landscape");
            caps.setCapability("automationName", "UiAutomator2");
            caps.setCapability("deviceQuery", "@os='android' and @category='PHONE' and contains(@name, 'Galaxy')");
//            desiredCapabilities.setCapability("deviceQuery", "@serialnumber='\"" + udid + "\"'");
            caps.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
            caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");

            desiredCapabilities.set(caps);
            driver.set(new AndroidDriver(new URL(System.getenv("CLOUD_URL") + "/wd/hub"), caps));

        }

        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(10)));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            getDriver().quit();
            driver.remove();
            wait.remove();
        }
    }

}
