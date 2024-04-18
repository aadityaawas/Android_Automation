package BaseClasses;

import AppiumUtils.CommonActions;
import PageObjects.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;
import java.util.Properties;

public class AndroidBaseTest extends CommonActions {

    private AppiumDriverLocalService service;


    public AndroidDriver driver;
    public LoginPage loginPage;


    @BeforeClass(alwaysRun = true)
    public void androidConfigurations() throws IOException {
        Properties properties = readPropertyFile();
        String ipAddress = System.getProperty("ipAddress") != null ? System.getProperty("ipAddress") : properties.getProperty("ipAddress");
        getAppiumServerStarted(ipAddress);
        UiAutomator2Options options = new UiAutomator2Options();

        options.setDeviceName(readPropertyFile().getProperty("deviceName"));
       // options.setApp(System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\APKs\\RedBus.apk");

        driver = new AndroidDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);

    }
    @AfterClass(alwaysRun = true)
    public void resetServices(){
        if (driver!=null){
            driver.quit();
        }
        if (service!=null && service.isRunning()){
            service.stop();
        }
        System.out.println("Test executing stopped. Performing cleanup");

    }


    public void getAppiumServerStarted(String ipAddress) {
        int portNumber;
        try {
            service = startAppiumServer(ipAddress, Integer.parseInt(readPropertyFile().getProperty("portNumber")));
        }
        catch (AppiumServerHasNotBeenStartedLocallyException exception)
        {
            try (ServerSocket socket = new ServerSocket(0))
            {
                portNumber = socket.getLocalPort();
            }
            catch (IOException e) {
                throw new RuntimeException("Unable to find any free port", e);
            }
            if (portNumber != 4723){
                service = startAppiumServer(ipAddress,portNumber);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
