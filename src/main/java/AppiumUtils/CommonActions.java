package AppiumUtils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class CommonActions {

    public Properties readPropertyFile() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\GeneralData.properties");
        properties.load(fileInputStream);
        return properties;
    }

    public AppiumDriverLocalService startAppiumServer(String ipAddress, int portNumber){             //  C\Users\Dell\AppData\Roaming\npm\node_modules\appium\build\lib\main.js
        AppiumDriverLocalService service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\Dell\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .usingPort(portNumber)
                .withIPAddress(ipAddress)
                .build();
        service.start();
        return service;
    }

    public void sleep(int sleepTime) throws InterruptedException {
        Thread.sleep(sleepTime);
    }
}

