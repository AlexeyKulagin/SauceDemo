package common;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.time.Duration;

public abstract class BaseTest {

    private WebDriver driver;

    protected WebDriver getDriver() {
        return driver;
    }

    protected void getWait() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ProjectUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());

        ProjectUtils.log("Open the browser");
        driver = ProjectUtils.createDriver();

        ProjectUtils.log("Get the web page");
        getDriver().get("https://www.saucedemo.com");
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if ((ProjectUtils.isRunCI() || testResult.isSuccess() || ProjectUtils.closeIfError()) && getDriver() != null) {

            getDriver().quit();
        }

        ProjectUtils.logf("Execution time is %.3f sec", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
    }
}
