package BaseClasses;

import AppiumUtils.CommonActions;
import AppiumUtils.ReusableValues;
import PageObjects.BusBookingPage;
import PageObjects.LoginPage;
import PageObjects.TempPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class AndroidBaseTest extends CommonActions {

    private AppiumDriverLocalService service;

    public AndroidDriver androidDriver;
    public LoginPage loginPage;
    public TempPage tempPage;
    public BusBookingPage busBookingPage;


    @BeforeClass(alwaysRun = true)
    public void androidBaseConfigurations() throws IOException {
        Configurator.initialize(null,System.getProperty("user.dir") + readPropertyFile().getProperty("log4j2Path"));
        readPropertyFile();
        String ipAddress = System.getProperty("ipAddress") != null ? System.getProperty("ipAddress") : readPropertyFile().getProperty("ipAddress");
        int portNumber = System.getProperty("portNumber") != null ? Integer.parseInt(System.getProperty("portNumber")) : getFreePortNumber();
        File appiumMainJSFilePath = getAppiumMainJSFilePath(getLocalMachinePlatformName());
        getAppiumServerStarted(ipAddress, portNumber, appiumMainJSFilePath);
        // options.setApp(System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\APKs\\RedBus.apk");
        androidDriver = new AndroidDriver(service.getUrl(), setUiAutomator2Options());
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ReusableValues.getDefaultWaitTime()));
        objectInitializationManager(androidDriver);
    }

    @AfterClass(alwaysRun = true)
    public void resetServices() {
        if (androidDriver != null) {
            androidDriver.quit();
        }
        if (service != null && service.isRunning()) {
            service.stop();
        }
        System.out.println("Test executing stopped. Performing cleanup");
    }

    private void getAppiumServerStarted(String ipAddress, int portNumber, File mainJSFilePath) {
        try {
            service = startAppiumServer(ipAddress, portNumber, mainJSFilePath);
        } catch (AppiumServerHasNotBeenStartedLocallyException e) {
            System.out.println("Failed to start Appium Server. Starting Appium Server again....");
            service = startAppiumServer(ipAddress, portNumber, mainJSFilePath);
        }
    }

    private UiAutomator2Options setUiAutomator2Options() throws IOException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability("platformName", readPropertyFile().getProperty("platformName"));
        options.setCapability("automationName", readPropertyFile().getProperty("androidAutomationName"));
        options.setCapability("deviceName", readPropertyFile().getProperty("deviceName"));
        return options;
    }

    private void objectInitializationManager(AndroidDriver androidDriver) {
        loginPage = new LoginPage(androidDriver);
        tempPage = new TempPage(androidDriver);
        busBookingPage = new BusBookingPage(androidDriver);
    }
}
