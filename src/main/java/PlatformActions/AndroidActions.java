package PlatformActions;

import AppiumUtils.CommonActions;
import AppiumUtils.ReusableValues;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AndroidActions extends CommonActions {
       AndroidDriver driver;

    public AndroidActions(AndroidDriver driver){
        this.driver = driver;
    }

    public void implementExplicitWait(){
        AndroidDriver androidDriver = driver;
        new WebDriverWait(androidDriver, Duration.ofSeconds(ReusableValues.getDefaultWaitTime()));
    }

    protected void setData(WebElement element, String data) {
        AndroidDriver androidDriver = driver;
        String isElementClickable = element.getAttribute("clickable");
        if (isElementClickable.equals("true")){
            element.click();
            if (androidDriver.isKeyboardShown()){
                printMessage("keypad is shown. Closing it now");
                element.sendKeys(data);
            }
            else
                printMessage("keypad is not getting displayed");
        }
        else
            printMessage("Element is not clickable");
    }

    protected void selectButton(WebElement element){
        String isElementClickable = element.getAttribute("clickable");
        if (isElementClickable.equals("true")) {
            element.click();
        }
    }

    protected String getElementText(WebElement element){
        return element.getText();
    }

    protected boolean isElementDisplayed(WebElement element){
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

    public void jsSwipeGesture(String direction){
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "left", 100, "top", 100, "width", 100, "height", 100, "direction", direction,"percent", 1.0));
    }

    public String getToastMessage(){
        return driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("name");
    }

    public boolean isToastMessageDisplayed(){
        return isElementDisplayed(driver.findElement(By.cssSelector("android.widget.Toast")));
    }









    public enum ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }

    public void scroll(ScrollDirection direction, double scrollRatio) {
        if (scrollRatio < 0 || scrollRatio > 1) {
            throw new IllegalArgumentException("Scroll Distance must be between 0 and 1");
        }

        Duration scrollDuration = Duration.ofMillis(300); // Adjust as needed

        Dimension size = driver.manage().window().getSize();
        Point midpoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
        int bottom = midpoint.y + (int) (midpoint.y * scrollRatio);
        int top = midpoint.y - (int) (midpoint.y * scrollRatio);
        int left = midpoint.x - (int) (midpoint.x * scrollRatio);
        int right = midpoint.x + (int) (midpoint.x * scrollRatio);

        if (direction == ScrollDirection.UP) {
            swipe(new Point(midpoint.x, top), new Point(midpoint.x, bottom), scrollDuration);
        } else if (direction == ScrollDirection.DOWN) {
            swipe(new Point(midpoint.x, bottom), new Point(midpoint.x, top), scrollDuration);
        } else if (direction == ScrollDirection.LEFT) {
            swipe(new Point(left, midpoint.y), new Point(right, midpoint.y), scrollDuration);
        } else {
            swipe(new Point(right, midpoint.y), new Point(left, midpoint.y), scrollDuration);
        }
    }

    public void swipe(Point start, Point end, Duration duration) {
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "f1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(ImmutableList.of(swipe));
    }
}