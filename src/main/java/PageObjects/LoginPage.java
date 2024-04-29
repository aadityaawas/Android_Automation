package PageObjects;

import PlatformActions.AndroidActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends AndroidActions {

    private final AndroidDriver driver;

    public LoginPage(AndroidDriver driver){
        super(driver);
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "in.redbus.android:id/continueButton")
    private WebElement continueButton;
    @AndroidFindBy(accessibility = "Country auto selected toIndia")
    private WebElement countryListButton;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Country']")
    private WebElement countryHeader;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Language']")
    private WebElement languageHeader;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Country and Language']")
    private WebElement firstHeader;
    @AndroidFindBy(accessibility = " Login ")
    private WebElement loginButton;
    @AndroidFindBy(accessibility = "Join")
    private WebElement joinButton;
    @AndroidFindBy(accessibility = "Easy Booking, Booking your preferred bus ticket is just a few taps away")
    private WebElement loginPageHeader;
    @AndroidFindBy(accessibility = "Skip")
    private WebElement skipButton;
    @AndroidFindBy(accessibility = "Create Account or Sign in")
    private WebElement signUpPageHeader;
    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@resource-id, 'editPhoneNo')]")
    private WebElement editPhoneNumber;
    @AndroidFindBy(id = "com.google.android.gms:id/cancel")
    private WebElement mobileNumberListNoButton;
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id, 'loginButtonSms')]")
    private WebElement otpButton;
    @AndroidFindBy(id = "in.redbus.android.authmodule:id/textVerifyOtp")
    private WebElement verifyOTPButton;
    @AndroidFindBy(id = "in.redbus.android.authmodule:id/resendOTP")
    private WebElement resendOTPButton;
    @AndroidFindBy(id = "in.redbus.android.authmodule:id/editPhoneNumber")
    private WebElement reEditPhoneNumberButton;
    @AndroidFindBy(id = "in.redbus.android.authmodule:id/editOtp")
    private WebElement setOTPField;
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id, 'labelGoogleVerification')]")
    private WebElement googleAccountListButton;

    public void selectContinueButton(){
        selectButton(continueButton);
    }

    public void selectLoginButton(){
        selectButton(loginButton);
    }

    public void selectMobileListNoButton(){
        selectButton(mobileNumberListNoButton);
    }

    public void getOTPButton(){
        selectButton(otpButton);
    }

    public void selectVerifyOTPButton(){
        selectButton(verifyOTPButton);
    }

    public void selectResendOTPButton(){
        selectButton(resendOTPButton);
    }

    public void selectReEditPhoneNumberButton(){
        selectButton(reEditPhoneNumberButton);
    }

    public void selectMobileNumberField(){
        selectButton(driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id, 'editPhoneNo')]")));
    }

    public void selectGoogleAccountListButton(){
        selectButton(googleAccountListButton);
    }

    public void selectGoogleAccountForList(String googleEmail){
        List<WebElement> elementList = driver.findElements(By.cssSelector("android.widget.TextView"));
        //int listCount =  elementList.size();
        for (WebElement element : elementList) {
            if (element.getText().equals(googleEmail)) {
                element.click();
                break;
            }
        }
    }






    public String getCountryHeaderText(){
        return getElementText(countryHeader);
    }

    public String getContinueButtonText(){
        return getElementText(continueButton);
    }

    public String getFirstPageHeaderText(){
        return getElementText(firstHeader);
    }

    public void selectCountryListButton(){
        selectButton(countryListButton);
    }

    public boolean isFirstPageHeaderDisplayed(){
        return isElementDisplayed(firstHeader);
    }

    public boolean isLoginButtonDisplayed(){
        return isElementDisplayed(loginButton);
    }

    public boolean isSignUpPageHeaderDisplayed(){
        return isElementDisplayed(signUpPageHeader);
    }

    public boolean isOTPButtonDisplayed(){
        return isElementDisplayed(otpButton);
    }

    public boolean isVerifyOTPButtonDisplayed(){
        return isElementDisplayed(verifyOTPButton);
    }

    public boolean isResendOTPButtonDisplayed(){
        return isElementDisplayed(resendOTPButton);
    }








    public void setPhoneNumber(String phoneNumber){
        setData(editPhoneNumber, phoneNumber);
    }

    public void setOTP(String otp) throws IOException {
        driver.activateApp(readPropertyFile().getProperty("redbusPackage"));
        setData(setOTPField, otp);
    }


    public void selectPreference(String criteria, String prefer) throws InterruptedException {
        List<WebElement> elementList = driver.findElements(By.cssSelector("android.widget.TextView"));
        int listLength = elementList.size();
        for (int index = 0 ; index < listLength ; index++){
            if (elementList.get(index).getText().equals(prefer)){
                //printMessage(elementList.get(index).getText());
                //printMessage(String.valueOf(driver.findElements(By.cssSelector("android.widget.RadioButton")).size()));
                driver.findElements(By.cssSelector("android.widget.RadioButton")).get(index).click();
                break;
            }
        }
    }

    public boolean isSkipButtonDisplayed() throws InterruptedException {
        String direction = "right";
        int index=0;
//        List<WebElement> elementList = driver.findElements(By.cssSelector("android.widget.TextView"));
//        int listCount = elementList.size();
        while (index<2 && !isElementDisplayed(skipButton)){
            // swipeGesture(direction);
            index++;
        }
        if (isElementDisplayed(skipButton)){
            printMessage("Skip button is getting displayed. TC Passes");
            return true;
        }
        else {
            printMessage("Something went wrong");
            return false;
        }
    }

    public String readOTP() throws IOException {

        driver.activateApp(readPropertyFile().getProperty("androidMessagePackage"));
        return fetchOTP();
    }

    public String fetchOTP() throws IOException {
        //getLatestOTPSMS();
        List<WebElement> elementList = driver.findElements(By.cssSelector("android.widget.TextView"));
        int listCount = elementList.size();
        for (int index = 0 ; index < listCount ; index++){
            if (elementList.get(index).getText().contains("-REDBUS")){
                printMessage(elementList.get(index).getText());
                elementList.get(index).click();
                break;
            }
            else {
                printMessage("Nahi mila msg");
            }
        }
        return getLatestOTPText();
    }

    public void getLatestOTPSMS() throws IOException {
        List<WebElement> elementList = driver.findElements(By.cssSelector("android.widget.TextView"));
        int listCount = elementList.size();
        for (int index = 0 ; index < listCount ; index++){
            if (elementList.get(index).getText().contains(readPropertyFile().getProperty("smsSuffix"))){
                printMessage(elementList.get(index).getText());
                elementList.get(index).click();
                break;
            }
        }
    }

    public String getLatestOTPText(){
        String otpRegexPattern = "\\b\\d{6}\\b";
        String otp = "";
        WebElement element = driver.findElement(By.id("com.google.android.apps.messaging:id/suggestion_button_label"));
        String expectedOtp = element.getText();

        Pattern pattern = Pattern.compile(otpRegexPattern);
        Matcher matcher = pattern.matcher(expectedOtp);

        if (matcher.find()){
            otp = matcher.group();
            printMessage("OTP: "+otp);
        }
        else {
            printMessage("Can't find the OTP");
        }
        return otp;
    }



}
