package common;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

final class ProjectUtils {

    private static final Properties properties;

    private static final ChromeOptions chromeOptions;

    static {
        properties = new Properties();

        if (!isRunCI()) {
            InputStream inputStream = ProjectUtils.class.getClassLoader().getResourceAsStream(".properties");
            try {
                properties.load(inputStream);
            } catch (IOException ignore) {
            }
        }

        chromeOptions = new ChromeOptions();
        String options = getValue("browser.options.chrome");
        if (options != null) {
            for (String option : options.split(";")) {
                chromeOptions.addArguments(option);
            }
        }

        String chromeDriverPath = getValue("webdriver.chrome.driver");
        if (chromeDriverPath != null && !chromeDriverPath.isEmpty()) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
    }

    private static String convertPropToEnvName(String propName) {
        return propName.replace('.', '_').toUpperCase();
    }

    private static String getValue(String name) {
        return properties.getProperty(name, System.getenv(convertPropToEnvName(name)));
    }

    static boolean isRunCI() {
        return Boolean.TRUE.toString().equals(getValue("run.ci"));
    }

    static WebDriver createDriver() {
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        return driver;
    }

    static boolean closeIfError() {
        return Boolean.TRUE.toString().equals(getValue("browser.closeIfError"));
    }

    static void log(String str) {
        System.out.println(str);
    }

    static void logf(String str, Object... arg) {
        log(String.format(str, arg));
    }
}
