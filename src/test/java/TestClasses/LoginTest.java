package TestClasses;

import BaseClasses.AndroidBaseTest;
import PlatformActions.AndroidActions;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static PlatformActions.AndroidActions.printMessage;

public class LoginTest extends AndroidBaseTest {

    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);
    @BeforeMethod
    public void pageSetup(){
        //driver.manage().timeouts().getPageLoadTimeout();
    }

     @Test(priority = 1)
    public void selectionOfCountryLanguage_tc1() throws InterruptedException {
        Assert.assertTrue(loginPage.isFirstPageHeaderDisplayed());
        Assert.assertEquals(loginPage.getFirstPageHeaderText(), "Country and Language");
        loginPage.selectCountryListButton();
        loginPage.selectPreference("Language", "English");  // Currently we are putting English the preferred language.
        loginPage.selectPreference("Country", "India");     // Currently we are putting India the preferred country.
        loginPage.selectContinueButton();
    }

    @Test
    public void skipButtonGettingDisplayedAfterScrollingToLastPage_tc2() throws InterruptedException {
        Assert.assertTrue(loginPage.isLoginButtonDisplayed());
        for (int index = 0 ; index < 3 ; index++){
            loginPage.scroll(AndroidActions.ScrollDirection.RIGHT, 0.75);
            sleep(200);
        }
        Assert.assertTrue(loginPage.isSkipButtonDisplayed());
    }

    @Test(priority = 2)
    public void loginViaMobileNumber_tc3() throws InterruptedException, IOException {
        loginPage.selectContinueButton();


        Assert.assertTrue(loginPage.isLoginButtonDisplayed());
        loginPage.selectLoginButton();
        Assert.assertTrue(loginPage.isSignUpPageHeaderDisplayed());
        loginPage.selectMobileNumberField();
        loginPage.selectMobileListNoButton();
        loginPage.setPhoneNumber("9876543210"); // Please use the real phone number for this. I have placed sum dummy phone number here.
        Assert.assertTrue(loginPage.isOTPButtonDisplayed());
        loginPage.getOTPButton();
        sleep(1500);
        //Assert.assertTrue(loginPage.isToastMessageDisplayed());
        printMessage(loginPage.getToastMessage());
        Assert.assertTrue(loginPage.isVerifyOTPButtonDisplayed());
        androidDriver.openNotifications();
        androidDriver.findElement(AppiumBy.accessibilityId("Clear all notifications.")).click();
        String otp = loginPage.readOTP();
        loginPage.setOTP(otp);
    }

   // @Test(priority = 4)
    public void loginViaGmail_tc4(){
        loginPage.selectContinueButton();
        loginPage.selectLoginButton();
        loginPage.selectGoogleAccountListButton();
        loginPage.selectGoogleAccountForList("tester1@gmail.com"); // Please use the real email for this. I have placed sum dummy email here.
    }

    @Test
    public void test(){
        loginPage.selectContinueButton();
    }

   




}