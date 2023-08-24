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

        desiredCapabilities.setCapability("accessKey", System.getenv("ACCESS_KEY"));
        desiredCapabilities.setCapability("Jenkins_Build_Number", System.getenv("BUILD_NUMBER"));
        desiredCapabilities.setCapability("testName", method.getName() + " - " + platform);

        if (platform.equalsIgnoreCase("iOS")) {

            desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
//            desiredCapabilities.setCapability("deviceQuery", "@serialnumber='\"" + udid + "\"'");
            desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
            desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
            desiredCapabilities.setCapability("autoAcceptAlerts", true);

            desiredCapabilities.set(caps);
            driver.set(new IOSDriver(new URL(System.getenv("CLOUD_URL") + "/wd/hub"), desiredCapabilities));

        } else if (platform.equalsIgnoreCase("Android")) {

            desiredCapabilities.setCapability("deviceQuery", "@os='android' and @category='PHONE'");
//            desiredCapabilities.setCapability("deviceQuery", "@serialnumber='\"" + udid + "\"'");
            desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
            driver.set(new AndroidDriver(new URL(System.getenv("CLOUD_URL") + "/wd/hub"), desiredCapabilities));

        }

        wait.set(new WebDriverWait(getDriver(), 10));
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
