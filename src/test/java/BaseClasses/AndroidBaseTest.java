package BaseClasses;

import AppiumUtils.CommonActions;
import AppiumUtils.ReusableValues;
import AppiumUtils.UniversalLogger;
import BaseClasses.AndroidDriverInitializationSetup.AndroidDriverInstanceManager;
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
import java.util.HashMap;

public class AndroidBaseTest extends CommonActions {

    private AppiumDriverLocalService service;
    private AndroidDriverInstanceManager instanceManager;

    public AndroidDriver androidDriver;
    public LoginPage loginPage;
    public TempPage tempPage;
    public BusBookingPage busBookingPage;


    @BeforeClass(alwaysRun = true)
    public void androidBaseConfigurations() throws IOException {
        Configurator.initialize(null,System.getProperty("user.dir") + readPropertyFile().getProperty("log4j2Path"));
        instanceManager = AndroidDriverInstanceManager.getInstance();
        HashMap<String, String> basicDetails = instanceManager.basicDetailsForAppiumServerInitialization();
        String ipAddress = basicDetails.get("ipAddress");
        int portNumber = Integer.parseInt(basicDetails.get("portNumber"));
        File appiumMainJSFilePath = getAppiumMainJSFilePath(getLocalMachinePlatformName());
        getAppiumServerStarted(ipAddress, portNumber, appiumMainJSFilePath);
        setupAppiumAndroidConnection();
//        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ReusableValues.getDefaultWaitTime()));
    }

    /**
     * This method is executed after all the test methods in a test class.
     * It is responsible for cleaning up the resources used by the test.
     * It quits the AndroidDriver and stops the Appium service.
     */
    @AfterClass(alwaysRun = true)
    public void resetServices() {
        // Quit the AndroidDriver if it is not null
        if (androidDriver != null) {
            androidDriver.quit();
        }
        // Stop the Appium service if it is running
        if (service != null && service.isRunning()) {
            service.stop();
        }
        // Log that the test execution is stopped and cleanup is being performed
        UniversalLogger.info("Test executing stopped. Performing cleanup");
    }

    /**
     * This method attempts to start the Appium server with the given IP address, port number, and main JS file path.
     * It retries the process twice in case the server fails to start due to high memory utilization.
     *
     * @param ipAddress The IP address of the server
     * @param portNumber The port number to use for the server
     * @param mainJSFilePath The path to the main JS file for the server
     */
    private void getAppiumServerStarted(String ipAddress, int portNumber, File mainJSFilePath) {
        // Number of attempts to start the server
        int attempts = 2;
        // Loop to retry starting the server
        try {
            for (int retry = 0; retry < attempts; retry++) {
                // Warn the user that the server may fail to start due to high memory utilization
                UniversalLogger.warn("Appium server may fail to start as it depends upon the high memory utilization.");
                // Start the Appium server with the given parameters
                service = startAppiumServer(ipAddress, portNumber, mainJSFilePath);
                // If the server starts successfully, break the loop
            }
        } catch (AppiumServerHasNotBeenStartedLocallyException e) {
            // Log an error message if the server fails to start
            UniversalLogger.error("Failed to start Appium Server. Starting Appium Server again....");
            if (service == null) {
                UniversalLogger.error("Appium Server is null. Check Appium Server initialization......");
            }
        }
    }

    private void setupAppiumAndroidConnection() {
        int attempts = 2;
        for(int retry = 0; retry < attempts; retry++) {
            try {
                UniversalLogger.info("Setting up Appium Android Connection");
                androidDriver = instanceManager.getAndroidDriver();
                System.out.println("Made connection to Android driver");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                UniversalLogger.error(e.getMessage());
                UniversalLogger.warn("Failed to connect android driver with Appium server. Retrying....");
                if (androidDriver == null) {
                    UniversalLogger.error("Android Driver is null. Check android driver initialization......");
                }
            }
        }
    }
}
