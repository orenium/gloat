package pages;

import il.co.topq.difido.model.Enums;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.TestRunner.report;

public abstract class BasePage {

    protected WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
        actions = new Actions(driver);
    }

    /**
     * This method scroll to element
     *
     * @param element - The BY element we wish to scroll to
     */
    public void moveToElement(By element) {
        try {
            if (driver != null) {
                actions.moveToElement(driver.findElement(element)).perform();
            }
        } catch (Exception ex) {
            report.log("BasePage.moveToElement: " + ex.getMessage(), Enums.Status.error);
        }
    }

    /**
     * This method scroll to element
     *
     * @param element - The Webelement element we wish to scroll to
     */
    public void moveToElement(WebElement element) {
        try {
            if (driver != null) {
                actions.moveToElement(element).perform();
            }
        } catch (Exception ex) {
            report.log("BasePage.moveToElement: " + ex.getMessage(), Enums.Status.error);
        }
    }


    /**
     * This method switch to iFrame in order to manipulate webElements in it
     *
     * @param iFrameSelector - The iFrame By selector
     */
    public void switchToIframe(By iFrameSelector) {
        WebElement iFrame = driver.findElement(iFrameSelector);
        driver.switchTo().frame(iFrame);
    }


    /**
     * This method switch back to main DOM
     */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

}

