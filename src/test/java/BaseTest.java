import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import static utils.TestRunner.*;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public class BaseTest {

    public WebDriver driver = null;

    @BeforeMethod
    public void setup() {
        driver = getNewChromeDriver();
    }

    public void openUrl(String url) {
        if (driver != null) {
            driver.get(url);
            report.log("Opening url: " + url);
        }
    }

    /**
     * This method init the wedDriver
     * @return new Chrome webdriver
     */
    private WebDriver getNewChromeDriver() {
        report.log("Setting new ChromeWebDriver...");
        WebDriverManager.chromedriver().setup();
        WebDriver WebDriver = new ChromeDriver();
        return WebDriver;
    }

    @AfterMethod
    public void afterTest() {
        if (driver != null) {
            takeScreenShot(driver);
            driver.quit();
        }
    }


    @AfterSuite
    public void afterSuite() {
        if (driver != null) {
            openHtmlReportFile();
        }

    }
}


