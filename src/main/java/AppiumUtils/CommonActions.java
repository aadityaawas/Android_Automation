package AppiumUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class CommonActions {

    public static Properties readPropertyFile() {
        try {
            UniversalLogger.info("Reading properties file");
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\GeneralData.properties");
            properties.load(fileInputStream);
            UniversalLogger.info("Properties file read successfully");
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected AppiumDriverLocalService startAppiumServer(String ipAddress, int portNumber, File mainJSFilePath) {
        UniversalLogger.info("Starting Appium Server...");
        AppiumDriverLocalService service = new AppiumServiceBuilder().withAppiumJS(mainJSFilePath)
                .usingPort(portNumber)
                .withIPAddress(ipAddress)
                .build();
        service.start();
        return service;
    }

    public static int getRandomSelection(int maximumValue, int minimumValue) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(maximumValue - minimumValue + ReusableValues.getDefaultMinimumValue()) + minimumValue;
    }

    protected static void sleep(int sleepTime) throws InterruptedException {
        Thread.sleep(sleepTime);
    }

    protected String getLocalMachinePlatformName() {
        return System.getProperty("os.name");
    }


    protected File getAppiumMainJSFilePath(String platformName) {
        return switch (platformName.toLowerCase()) {
            case "windows" ->
                    new File("C:\\Users\\Dell\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js");
            case "mac os x", "macos" -> new File("need to put the path for MAC accordingly");
            case "linux" -> new File("need to put the path for Linux accordingly");
            default -> null;
        };
    }

    /**
     * This function retrieves a free port number.
     *
     * @return the free port number as an integer
     * @throws IOException if an I/O error occurs
     */
    public static int getFreePortNumber() {
        int portNumber;
        int defaultPortNumber = Integer.parseInt(readPropertyFile().getProperty("portNumber"));
        try (ServerSocket socket = new ServerSocket(0)) {
            portNumber = socket.getLocalPort();
            if (portNumber != defaultPortNumber) {
                return portNumber;
            } else {
                int getRandomWaitTimeToRemoveConcurrency = getRandomSelection(ReusableValues.getDefaultMaximumValue(), ReusableValues.getDefaultMinimumValue()) * ReusableValues.getDefaultConcurrencyWaitDurationMultiplier();
                sleep(getRandomWaitTimeToRemoveConcurrency);
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        return portNumber;
    }
}