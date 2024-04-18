package PlatformActions;

import AppiumUtils.CommonActions;
import io.appium.java_client.android.AndroidDriver;

public class AndroidActions extends CommonActions {
    AndroidDriver driver;

    public AndroidActions(AndroidDriver driver){
        this.driver=driver;
    }
}
