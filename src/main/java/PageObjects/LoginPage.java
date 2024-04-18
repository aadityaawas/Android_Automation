package PageObjects;

import PlatformActions.AndroidActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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
    private WebElement loginPageHeader;


    public void selectContinueButton(){
        selectButton(continueButton);
    }

    public String getCountryHeaderText(){
        return getElementText(countryHeader);
    }

    public String getContinueButtonText(){
        return getElementText(continueButton);
    }

    public String getLoginPageHeaderText(){
        return getElementText(loginPageHeader);
    }

    public void selectCountryListButton(){
        selectButton(countryListButton);
    }

    public boolean isLoginPageHeaderDisplayed(){
        return isElementDisplayed(loginPageHeader);
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


}
