package AppiumUtils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public abstract class CommonActions {

    protected Properties readPropertyFile() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\GeneralData.properties");
        properties.load(fileInputStream);
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
}

