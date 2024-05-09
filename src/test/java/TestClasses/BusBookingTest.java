package TestClasses;

import BaseClasses.AndroidBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BusBookingTest extends AndroidBaseTest {

    @Test(groups = {"Smoke"})
    public void isAllElementPresent_tc4(){
        Assert.assertTrue(busBookingPage.isSourceFieldDisplayed());
        Assert.assertTrue(busBookingPage.isDestinationFieldDisplayed());
        Assert.assertTrue(busBookingPage.isCalendarButtonDisplayed());
        Assert.assertTrue(busBookingPage.isSearchBusesButtonDisplayed());
    }
}
