package PageObjects;

import PlatformActions.AndroidActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BusBookingPage extends AndroidActions {
    private final AndroidDriver driver;

    public BusBookingPage(AndroidDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    @AndroidFindBy(id = "View_source")
    private WebElement sourceField;
    @AndroidFindBy(id = "View_destination")
    private WebElement destinationField;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text = 'Date of Journey']")
    private WebElement calenderButton;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text = 'Search buses']")
    private WebElement searchBusesButton;

    public boolean isSourceFieldDisplayed(){
        return isElementDisplayed(sourceField);
    }
    public boolean isDestinationFieldDisplayed(){
        return isElementDisplayed(destinationField);
    }
    public boolean isCalendarButtonDisplayed(){
        return isElementDisplayed(calenderButton);
    }
    public boolean isSearchBusesButtonDisplayed(){
        return isElementDisplayed(searchBusesButton);
    }


    public void setSource(String source){
        setData(sourceField, source);
    }
    public void setDestination(String destination){
        setData(sourceField, destination);
    }




    public void selectSearchBusesButton(){
        selectButton(searchBusesButton);
    }


}
