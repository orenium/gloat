package pages;

import il.co.topq.difido.model.Enums;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static utils.TestRunner.report;

public class GloatCareersPage extends BasePage {

    private final By pageTitle = By.cssSelector("div.box-box.sc-jDwBTQ.hMPipe.theme-default div h1");
    private final By locations = By.cssSelector("a.box-link.box-link-theme-default.decoration-none.box-link.sc-hMqMXs.iYbEOz.sc-bdVaJa.fXwRfb.weight-600.font-source-sans.clean.lg div h2");
    private final By sendMsgText = By.cssSelector("div.intercom-18biwo.esf9qb11 span");
    private final By openChatBtn = By.cssSelector("div.intercom-lightweight-app-launcher.intercom-launcher svg");
    private final By textArea = By.cssSelector("textarea");
    private final By sendMsgBtn = By.cssSelector("button.intercom-composer-send-button.intercom-15qiax6.eoxa25q8");
    private final By chatIframe = By.cssSelector("iframe[name='intercom-messenger-frame']");
    private final By closeChatBtn = By.cssSelector("div.intercom-1ed15uv.e2ujk8f3 svg");
    private final By closeChatIframe = By.cssSelector("iframe[name='intercom-launcher-frame']");
    private final By locationFilter = By.cssSelector("div[data-name='location-filter']");
    private final By departmentFilter = By.cssSelector("div[data-name='department-filter']");
    private final By searchFilter = By.cssSelector("div[data-name='search-filter'] input");
    private final By filterResultsJobTitle = By.cssSelector("div[data-name='job-title'] h1");
    private final By ddlFilterOptions = By.cssSelector("div.react-tiny-popover-container div p");


    public GloatCareersPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }

    /**
     * This method checks if the page has finish loading
     *
     * @return
     */
    public boolean waitForPageToLoad() {
        boolean isPageLoaded = false;
        try {
            wait.until(ExpectedConditions.textToBe(pageTitle, "Join the team"));
            isPageLoaded = true;
            report.log("Gloat Careers Page was loaded");
        } catch (Exception ex) {
            report.log("GloatCareersPage.waitFOrPageToLoad: " + ex.getLocalizedMessage(), Enums.Status.error);
        }
        return isPageLoaded;
    }

    /**
     * This method clicks on a given location name
     *
     * @param location - The requested location to click on
     */
    public void selectLocation(String location) {
        try {
            List<WebElement> locationsTextElements = driver.findElements(locations);
            for (WebElement locationElement : locationsTextElements) {
                if (locationElement.getText().equals(location.toUpperCase())) {
                    locationElement.click();
                    break;
                }
            }
        } catch (Exception ex) {
            report.log("GloatCareersPage.selectLocation: " + ex.getMessage(), Enums.Status.error);
        }
    }

    /**
     * This method opens the chat window
     *
     * @return true if opened, false if not
     */
    public boolean openChat() {
        boolean isOpened = false;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(openChatBtn)).click();
            report.log("Chat button was clicked");
            isOpened = true;
        } catch (Exception ex) {
            report.log("GloatCareersPage.openChat: " + ex.getMessage(), Enums.Status.error);
        }
        return isOpened;
    }

    /**
     * This method closes the chat window
     *
     * @return true if closed, false if not
     */
    public boolean closeChat() {
        boolean isClosed = false;
        try {
            switchToIframe(closeChatIframe);
            wait.until(ExpectedConditions.presenceOfElementLocated(closeChatBtn)).click();
            report.log("Close Chat button was clicked");
            isClosed = true;
        } catch (Exception ex) {
            report.log("GloatCareersPage.closeChat: " + ex.getMessage(), Enums.Status.error);
        }
        switchToDefaultContent();
        return isClosed;
    }

    /**
     * This method opens the chat window and sends a msg
     *
     * @param msg - The msg to send
     * @return - True if msg was sent, false if not
     */
    public boolean sendMsgInChat(String msg) {
        boolean msgSent = false;
        try {
            openChat();
            wait.until(ExpectedConditions.presenceOfElementLocated(chatIframe));
            switchToIframe(chatIframe);
            wait.until(ExpectedConditions.presenceOfElementLocated(textArea));
            WebElement textInput = driver.findElement(textArea);
            if (textInput.isDisplayed()) {
                textInput.sendKeys(msg);
                driver.findElement(sendMsgBtn).click();
                report.log("Msg sent: " + msg);
                msgSent = true;
            }
            report.log("Send a msg was clicked");

        } catch (Exception ex) {
            report.log("GloatCareersPage.SendMsgInChat: " + ex.getMessage(), Enums.Status.error);
        }
        switchToDefaultContent();
        return msgSent;
    }

    /**
     * this method search for a jot title
     *
     * @param searchTerm - The search term (job title_ to search
     */
    public void searchRole(String searchTerm) {
        try {
            moveToElement(searchFilter);
            wait.until(ExpectedConditions.presenceOfElementLocated(searchFilter));
            WebElement search = driver.findElement(searchFilter);
            if (search.isDisplayed()) {
                search.click();
                search.sendKeys(searchTerm);
                report.log("Searching: " + searchTerm);
            }
        } catch (Exception ex) {
            report.log("GloatCareersPage.searchRole: " + ex.getMessage(), Enums.Status.error);
        }
    }

    /**
     * This method gets all filtering results (jobs titles)
     *
     * @return - All the results as strings (jobs titles)
     */
    public List<String> getFilterResults() {
        List<String> jobTitles = new ArrayList<String>();
        List<WebElement> results = driver.findElements(filterResultsJobTitle);
        if (results.size() > 0) {
            moveToElement(results.get(0));
            report.log(results.size() + " results found: ");
            for (WebElement result : results) {
                jobTitles.add(result.getText());
                report.log(result.getText());
            }
        } else {
            report.log("No search results found");
        }
        return jobTitles;
    }

    /**
     * This method filters jobs by the location DDL
     * and selects a random value from the options
     */
    public void filterByLocation() {
        driver.findElement(locationFilter).click();
        report.log("Location filter was clicked");
        selectRandomOption();
    }

    /**
     * This method filters jobs by the department DDL
     * and selects a random value from the options
     */
    public void filterByDepartment() {
        driver.findElement(departmentFilter).click();
        report.log("Department filter was clicked");
        selectRandomOption();
    }

    /**
     * This method selects a random option from the current visible DDL
     */
    public void selectRandomOption() {
        Random random = new Random();
        List<WebElement> options;
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ddlFilterOptions));
            options = driver.findElements(ddlFilterOptions);
            if (options.size() > 0) {
                int index = random.nextInt(options.size());
                report.log(options.get(index).getText() + " was selected");
                options.get(index).click();
            } else {
                report.log("No options were found", Enums.Status.warning);
            }
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            options = driver.findElements(ddlFilterOptions);
            if (options.size() > 0) {
                int index = random.nextInt(options.size());
                report.log(options.get(index).getText() + " was selected");
                options.get(index).click();
            }
        } catch (Exception ex) {
            report.log("GloatCareersPage.selectRandomOption: " + ex.getMessage());
        }

    }


}
