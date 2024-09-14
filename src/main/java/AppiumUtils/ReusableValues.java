package AppiumUtils;

public class ReusableValues {

    private static final int DEFAULT_MINIMUM_VALUE = 1;
    private static final int DEFAULT_MAXIMUM_VALUE = 10;
    private static final int DEFAULT_WAIT_TIME = 10;
    private static final int DEFAULT_CONCURRENCY_WAIT_DURATION_MULTIPLIER = 100;


    public static int getDefaultMinimumValue(){
        return DEFAULT_MINIMUM_VALUE;
    }

    public static int getDefaultMaximumValue(){
        return DEFAULT_MAXIMUM_VALUE;
    }

    public static int getDefaultWaitTime() {
        return DEFAULT_WAIT_TIME;
    }

    public static int getDefaultSleepTime() {
        return DEFAULT_WAIT_TIME;
    }

    public static int getDefaultConcurrencyWaitDurationMultiplier() {
        return DEFAULT_CONCURRENCY_WAIT_DURATION_MULTIPLIER;
    }

    public static final String ANDROID_DRIVER_CAPABILITY_FILE_PATH = "\\src\\test\\java\\BaseClasses\\AndroidDriverInitializationSetup\\AndroidDriverCapabilities.json";




}
