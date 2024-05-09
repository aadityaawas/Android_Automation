package PageObjects;

import PlatformActions.AndroidActions;
import io.appium.java_client.android.AndroidDriver;

public class TempPage extends AndroidActions {
    private AndroidDriver driver;

    public TempPage(AndroidDriver driver){
        super(driver);
        this.driver = driver;
    }

    public void fromTempPage(){
        System.out.println("from temp");
    }


}
