package PlatformActions;

import AppiumUtils.CommonActions;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class AndroidActions extends CommonActions {
       AndroidDriver driver;

    public AndroidActions(AndroidDriver driver){
        this.driver =driver;
    }

    public void setData(WebElement element, String data)
    {
        element.click();
        if (driver.isKeyboardShown()){
            printMessage("keypad is shown. Closing it now");
            element.sendKeys(data);
        }
        else
            printMessage("keypad is not getting displayed");
    }

    public void selectButton(WebElement element){
        element.click();
    }

    public String getElementText(WebElement element){
        return element.getText();
    }

    public boolean isElementDisplayed(WebElement element){
        try {
            element.isDisplayed();
            return true;
        }
        catch (NoSuchElementException exception){
            exception.printStackTrace();
            return false;
        }
    }

    public static void printMessage(String message){
        System.out.println(message);
    }


}
