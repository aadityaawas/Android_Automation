package BaseClasses.TestInstances;

import BaseClasses.AndroidDriverInitializationSetup.AndroidDriverInstanceManager;
import PageObjects.BusBookingPage;
import PageObjects.LoginPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;

public class TestClassObjectManager {
    private AndroidDriver androidDriver;
    private LoginPage login;
    private BusBookingPage busBooking;
    public TestClassObjectManager() throws MalformedURLException, JsonProcessingException {
        if (androidDriver == null) {
            androidDriver = AndroidDriverInstanceManager.getInstance().getAndroidDriver();
        }
    }

    public void freeAllPageInstances() {
        freeLoginPageInstance();
        freeBusBookingPageInstance();
    }


    public LoginPage getLoginPageInstance() {
        if (login == null) {
            login = new LoginPage(androidDriver);
        }
        return login;
    }

    public BusBookingPage getBusBookingPageInstance() {
        if (busBooking == null) {
            busBooking = new BusBookingPage(androidDriver);
        }
        return busBooking;
    }

    private void freeLoginPageInstance() {
        login = null;
    }
    private void freeBusBookingPageInstance() {
        busBooking = null;
    }


}
