package BaseClasses.AndroidDriverInitializationSetup;

import AppiumUtils.ReusableValues;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static AppiumUtils.CommonActions.getFreePortNumber;
import static AppiumUtils.CommonActions.readPropertyFile;

public class AndroidDriverInstanceManager {
    private String ipAddress;
    private String portNumber;
    private String deviceID;
    private static AndroidDriverInstanceManager context;
    private volatile AndroidDriver androidDriver;

    private AndroidDriverInstanceManager() {
    }

    public static AndroidDriverInstanceManager getInstance() {
        if (context == null) {
            context = new AndroidDriverInstanceManager();
        }
        return context;
    }

    public HashMap<String, String> basicDetailsForAppiumServerInitialization() {
        HashMap<String, String> basicDetails = new HashMap<>();
        if (ipAddress == null) {
            ipAddress = System.getProperty("ipAddress") != null ? System.getProperty("ipAddress") : readPropertyFile().getProperty("ipAddress");
        }
        if (portNumber == null) {
            portNumber = System.getProperty("portNumber") != null ? System.getProperty("portNumber") : String.valueOf(getFreePortNumber());
        }
        if (deviceID == null) {
            deviceID = System.getProperty("deviceID") != null ? System.getProperty("deviceID") : readPropertyFile().getProperty("deviceID");
        }
        basicDetails.put("ipAddress", ipAddress);
        basicDetails.put("portNumber", portNumber);
        basicDetails.put("deviceID", deviceID);
        return basicDetails;

    }

    public AndroidDriver getAndroidDriver() {
        try {
            if (androidDriver == null) {
                synchronized (AndroidDriverInstanceManager.class) {
                    if (androidDriver == null) {
                        UiAutomator2Options options = new UiAutomator2Options(setAndroidDriverCapabilities());
                        String appiumServerURL = "http://" + ipAddress + ":" + portNumber;
                        androidDriver = new AndroidDriver(new URL(appiumServerURL), options);
                        setAndroidDriverCapabilities().clear();
                    }
                }
            }
        } catch (JsonProcessingException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return androidDriver;
    }

    public Map<String, Object> setAndroidDriverCapabilities() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CapabilitiesModel capabilitiesModel = mapper.readValue(ReusableValues.ANDROID_DRIVER_CAPABILITY_FILE_PATH, CapabilitiesModel.class);
        Map<String, Object> data = new HashMap<>();
        data.put("automationName", capabilitiesModel.getAutomationName());
        data.put("platformName", capabilitiesModel.getPlatformName());
        data.put("deviceName", capabilitiesModel.getDeviceName());
        System.out.println("-------------------------------------------------------------------- Here is a potential error");
        data.put("systemPort", getFreePortNumber());
        return ImmutableMap.copyOf(data);
    }
}
