import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
public class Main {
    static MobileDriver driver;

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        String timeStamp = String.valueOf(new Date().getTime());//lastName
        String firstName = "Test";
        String email = "automation_fossilqa+"+timeStamp+"@gmail.com";
        String password = "misfit1";
        System.out.println(timeStamp);

        initDriver();
        Dimension size = driver.manage().window().getSize();
        int y_start=-(int)(size.height*0.1);
        int y_end=(int)(size.height*0.30);
        int x=size.width/2;
        TouchAction action = new TouchAction(driver);
        action.press(new PointOption().withCoordinates(x, y_start)).waitAction().moveTo(new PointOption().withCoordinates(x, y_end)).perform();
//        signUp(firstName, timeStamp, email, password);
//        switchMenuBar("SETTINGS");
//        logout();
//        login(email, password);
    }

    public static void signUp(String firstName, String lastName, String email, String password) throws InterruptedException {
        waitForElementVisible(By.id("bt_sign_up")).click();
        waitForElementVisible(By.id("bt_sign_up_email"), 10).click();

        inputSignUpData(firstName, lastName, email, password);

        waitForElementVisible(By.id("buttonSave"), 10).click();
        waitForElementVisible(By.id("bt_sign_up_create_account"), 10).click();

        //Confirm email opt in
        waitForElementVisible(By.id("btn_sure"), 10).click();

        //Choose device - Smartwatch
        waitForElementVisible(By.xpath(".//android.widget.TextView[@text='SMARTWATCH']"), 10).click();

        //Choose device type
        waitForElementVisible(By.id("iv_hybrid_smartwatch"), 10).click();

        //Location permission
        waitForElementVisible(By.id("permission_ok"), 10).click();
        waitForElementVisible(By.xpath(".//android.widget.Button[@text='ALLOW']"), 10).click();

        //Skip pairing
        waitForElementVisible(By.id("btn_skip"), 10).click();

        setPersonalInfo();
        setPersonalGoal();

        //Connect to other apps
        waitForElementVisible(By.xpath(".//android.widget.TextView[@text='CONNECT TO OTHER APPS']"), 10);
        waitForElementVisible(By.id("continue_btn"), 10).click();

        //Setup complete
        waitForElementVisible(By.xpath(".//android.widget.TextView[@text='SETUP COMPLETE']"), 10);
        waitForElementVisible(By.id("continue_btn"), 10).click();

    }

    public static void inputSignUpData(String firstName, String lastName, String email, String password) throws InterruptedException {
        waitForElementVisible(By.id("et_sign_up_firstname"), 10).sendKeys( firstName);
        waitForElementVisible(By.id("et_signup_lastname"), 10).sendKeys(lastName);
        waitForElementVisible(By.id("et_signup_email"), 10).sendKeys(email);
        waitForElementVisible(By.id("et_signup_password"), 10).sendKeys(password);
        waitForElementVisible(By.id("bt_sign_up_female"), 10).click();

        driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"CREATE ACCOUNT\"))"));
        waitForElementVisible(By.id("cb_policy"), 10).click();

        waitForElementVisible(By.id("et_signup_birthday"), 10).click();

        AndroidElement dialog = (AndroidElement) driver.findElement(By.xpath("//android.widget.LinearLayout"));
        Dimension dialogSize = dialog.getSize();
        int dialogWidth = (int) (dialog.getLocation().getX() + dialogSize.width * 0.75);
        int dialogHeight = (int) (dialog.getLocation().getY() + dialogSize.height * 0.25);
        tapOnPosition(new PointOption().withCoordinates(dialogWidth, dialogHeight), 20);//To make sure the age is larger than 14 years old

    }

    public static void setPersonalGoal() {
        //Set Step Goal

        waitForElementVisible(By.xpath(".//android.widget.TextView[@text='SET YOUR STEP GOAL']"), 10);
        waitForElementVisible(By.id("continue_btn"), 10).click();

        //Set Sleep Goal
        waitForElementVisible(By.xpath(".//android.widget.TextView[@text='SET YOUR SLEEP GOAL']"), 10);
        waitForElementVisible(By.id("continue_btn"), 10).click();

        // xenkute version
        // waitForElementPresent(By.id("continue_btn")).click();
        // waitForElementPresent(By.id("text_field")).sendKeys("Hello");
    }

    public static void setPersonalInfo() {
        //Set Units
        waitForElementVisible(By.xpath(".//android.widget.TextView[@text='SET YOUR UNITS']"));
        waitForElementVisible(By.id("continue_btn")).click();

        //Set Height
        waitForElementVisible(By.xpath(".//android.widget.TextView[@text='SET YOUR HEIGHT']"));
        waitForElementVisible(By.id("continue_btn")).click();

        //Set Weight
        waitForElementVisible(By.xpath(".//android.widget.TextView[@text='SET YOUR WEIGHT']"));
        waitForElementVisible(By.id("continue_btn")).click();
    }

    public static void switchMenuBar(String des) {
        waitForElementVisible(By.id("btn_menu")).click();
        waitForElementVisible(By.xpath(".//android.widget.TextView[@text='"+des+"']")).click();
    }

    public static void logout() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"LOGOUT\"))"));
        WebElement logout = waitForElementVisible(By.id("tv_log_out"), 5);
        tapOnPosition(new PointOption().withCoordinates(logout.getLocation().getX(), logout.getLocation().getY()), 1);
        //driver.findElement(By.id("tv_log_out")).click();
        /*In some devices, cannot scroll to the bottom of the screen, must workaround by TouchAction()>>tap.
        * Refer: https://github.com/appium/appium/issues/11037*/
    }

    public static void login(String email, String password) throws InterruptedException {
        waitForElementVisible(By.id("bt_log_in")).click();
        waitForElementVisible(By.id("email")).sendKeys(email);
//        Thread.sleep(1000);
//        driver.hideKeyboard();
        waitForElementVisible(By.id("password")).sendKeys(password);
//        Thread.sleep(1000);
//        driver.hideKeyboard();
        waitForElementVisible(By.id("login")).click();
        waitForElementVisible(By.id("btn_menu"));
    }

    public static WebElement waitForElementVisible(By by, int timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    public static WebElement waitForElementVisible(By by){
        return waitForElementVisible(by, 10);
    }

    public static void tapOnPosition(PointOption pointOption, int tapTime) throws InterruptedException {
        TouchAction action = new TouchAction(driver);
        for(int count = 0; count < tapTime; count++) {
            action.tap(pointOption)
                    .perform();
            Thread.sleep(200);
        }
    }

    public static void initDriver() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "/Users/126553/Documents/ExampleFolder/Appium/apps/MichaelKors-11810.apk");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
    }
}
