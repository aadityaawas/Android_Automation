package AppiumUtils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public abstract class CommonActions {

    protected static Properties readPropertyFile() throws IOException {
        UniversalLogger.info("Reading properties file");
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\GeneralData.properties");
        properties.load(fileInputStream);
        UniversalLogger.info("Properties file read successfully");
        return properties;
    }

    protected AppiumDriverLocalService startAppiumServer(String ipAddress, int portNumber, File mainJSFilePath){
        AppiumDriverLocalService service = new AppiumServiceBuilder().withAppiumJS(mainJSFilePath)
                .usingPort(portNumber)
                .withIPAddress(ipAddress)
                .build();
        service.start();
        return service;
    }

    public int getRandomSelection(int maximumValue, int minimumValue){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(maximumValue - minimumValue + ReusableValues.getDefaultMinimumValue()) + minimumValue;
    }

    protected void sleep(int sleepTime) throws InterruptedException {
        Thread.sleep(sleepTime);
    }

    protected String getLocalMachinePlatformName(){
        return System.getProperty("os.name");
    }


    protected File getAppiumMainJSFilePath(String platformName){
        switch (platformName.toLowerCase()){
            case "windows":
                return new File("C:\\Users\\Dell\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js");

            case "mac os x":
            case "macos":
                return new File("need to put the path for MAC accordingly");

            case "linux":
                return new File("need to put the path for Linux accordingly");
        }
        return null;
    }

    /**
     * This function retrieves a free port number.
     *
     * @return the free port number as an integer
     * @throws IOException if an I/O error occurs
     */
    protected int getFreePortNumber() throws IOException {
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return portNumber;
    }
}

