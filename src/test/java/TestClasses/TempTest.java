package TestClasses;

import AppiumUtils.UniversalLogger;
import BaseClasses.AndroidBaseTest;
import org.testng.annotations.Test;

public class TempTest extends AndroidBaseTest {
    @Test(groups = {"Smoke"})
    public void tc1(){
//        tempPage.fromTempPage();
//        loginPage.selectContinueButton();

        UniversalLogger.debug("Hello");
        System.out.println("Hello");
    }

}
