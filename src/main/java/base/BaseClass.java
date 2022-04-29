package base;

import com.experitest.reporter.testng.Listener;
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

@Listeners(Listener.class)
public class BaseClass {

    private DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    protected ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected WebDriverWait wait;

    private String ACCESS_KEY = "eyJ4cC51Ijo3MzU0MjQsInhwLnAiOjIsInhwLm0iOiJNVFUzT0RZd016ZzFOek16TVEiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4OTM5NjM4NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.GP0hK0o0j2WEKt-J0aXsVbu1tmt-PhWUryqluokszJk";
    private String CLOUD_URL = "https://uscloud.experitest.com/wd/hub";

    public AppiumDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    @Parameters({"platform"})
    public void setUp(String platform, @Optional Method method) throws MalformedURLException {

        desiredCapabilities.setCapability("accessKey", ACCESS_KEY);
        desiredCapabilities.setCapability("Jenkins_Build_Number", System.getenv("BUILD_NUMBER"));
        desiredCapabilities.setCapability("testName", method.getName() + " - " + platform);

        if (platform.equalsIgnoreCase("iOS")) {

            desiredCapabilities.setCapability("deviceQuery", "@os='ios'");
//            desiredCapabilities.setCapability("deviceQuery", "@serialnumber='\"" + udid + "\"'");
            desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
            desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
            driver.set(new IOSDriver(new URL(CLOUD_URL), desiredCapabilities));

        } else if (platform.equalsIgnoreCase("Android")) {

            desiredCapabilities.setCapability("deviceQuery", "@os='android' and contains(@modelName, 'Galaxy')");
//            desiredCapabilities.setCapability("deviceQuery", "@serialnumber='\"" + udid + "\"'");
            desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
            driver.set(new AndroidDriver(new URL(CLOUD_URL), desiredCapabilities));

        }

        wait = new WebDriverWait(getDriver(), 10);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            getDriver().quit();
            driver.remove();
        }
    }

}
