import common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testEmptyPassword() {
        final String standardUserLogin = "standard_user";
        final String expectedErrorMessage = "Epic sadface: Password is required";

        getDriver().findElement(By.id("user-name")).sendKeys(standardUserLogin);
        getDriver().findElement(By.id("password")).sendKeys("");
        getDriver().findElement(By.id("login-button")).click();

        String actualErrorMessage = getDriver().findElement(By.cssSelector("h3[data-test='error']")).getText();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void testLockedUser() {
        final String lockedUserLogin = "locked_out_user";
        final String password = "secret_sauce";
        final String expectedErrorMessage = "Epic sadface: Sorry, this user has been locked out.";

        getDriver().findElement(By.id("user-name")).sendKeys(lockedUserLogin);
        getDriver().findElement(By.id("password")).sendKeys(password);
        getDriver().findElement(By.id("login-button")).click();

        String actualErrorMessage = getDriver().findElement(By.cssSelector("h3[data-test='error']")).getText();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
