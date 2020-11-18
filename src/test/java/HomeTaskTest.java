import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GloatCareersPage;

import java.util.List;

import static utils.TestRunner.report;

public class HomeTaskTest extends BaseTest {

    private final String PAGE_URL = "https://x.gloat.com/careers/all";
    private final String LOCATION  = "tel aviv";
    private final String JOB_TITLE  = "automation";

    @Test (priority = 0)
    public void checkLocationLink() {
        report.startLevel("Open Gloat careers page");
        openUrl(PAGE_URL);
        GloatCareersPage gloatCareersPage = new GloatCareersPage(driver);
        report.endLevel();

        report.startLevel("Select Tel aviv image");
        gloatCareersPage.selectLocation(LOCATION);
        report.endLevel();

        //Verify page URL
        Assert.assertEquals(driver.getCurrentUrl(), "https://x.gloat.com/careers/tlv");

    }

    @Test (priority = 1)
    public void sendMsgTest() {
        report.startLevel("Open Gloat careers page");
        openUrl(PAGE_URL);
        GloatCareersPage gloatCareersPage = new GloatCareersPage(driver);
        report.endLevel();

        report.startLevel("Open Chat and send a message");
        boolean isSent = gloatCareersPage.sendMsgInChat("Testing 123");
        gloatCareersPage.closeChat();
        report.endLevel();

        // Verify msg was sent
        Assert.assertTrue(isSent, " Failed to send message");
    }

    @Test (priority = 2)
    public void filterByLocationTest() {
        report.startLevel("Open Gloat careers page");
        openUrl(PAGE_URL);
        GloatCareersPage gloatCareersPage = new GloatCareersPage(driver);
        report.endLevel();

        report.startLevel("Filter by location (random selection)");
        gloatCareersPage.filterByLocation();
        report.endLevel();

        // Verify search results
        Assert.assertTrue(gloatCareersPage.getFilterResults().size() > 0);
    }

    @Test (priority = 3)
    public void filterByDepartmentTest() {
        report.startLevel("Open Gloat careers page");
        openUrl(PAGE_URL);
        GloatCareersPage gloatCareersPage = new GloatCareersPage(driver);
        report.endLevel();

        report.startLevel("Filter by Department (random selection)");
        gloatCareersPage.filterByDepartment();
        report.endLevel();

        // Verify search results
        Assert.assertTrue(gloatCareersPage.getFilterResults().size() > 0);
    }

    @Test (priority = 4)
    public void filterJobTest() {
        report.startLevel("Open Gloat careers page");
        openUrl(PAGE_URL);
        GloatCareersPage gloatCareersPage = new GloatCareersPage(driver);
        report.endLevel();

        report.startLevel("Enter a value to filter by (job title)");
        gloatCareersPage.searchRole(JOB_TITLE);
        report.endLevel();

        // Verify search results
        Assert.assertTrue(gloatCareersPage.getFilterResults().size() > 0);
    }


}
