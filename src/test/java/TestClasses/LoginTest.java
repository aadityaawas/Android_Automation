package TestClasses;

import BaseClasses.AndroidBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends AndroidBaseTest {

    @Test
    public void selectionOfCountryLanguage_tc1() throws InterruptedException {
        Assert.assertTrue(loginPage.isLoginPageHeaderDisplayed());
        Assert.assertEquals(loginPage.getLoginPageHeaderText(), "Country and Language");
        loginPage.selectCountryListButton();
        loginPage.selectPreference("Language", "English");  // Currently we are putting English the preferred language. We can change it by updating the String language variable;
        loginPage.selectPreference("Country", "India");
        loginPage.selectContinueButton();
    }
}
