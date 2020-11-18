package utils;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import il.co.topq.difido.model.Enums;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.awt.*;
import java.io.File;

public class TestRunner {

    private static final String REPORTING_FILE_PATH = "test-output/difido/current/index.html";
    public static ReportDispatcher report = ReportManager.getInstance();

    /**
     * This method takes a screenshots
     * @param driver
     */
    public static void takeScreenShot(WebDriver driver) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            report.addImage(scrFile, "Screenshot: " + getCurrentTimeStampAsStrings());
        } catch (Exception e) {
            report.log(e.getMessage(), Enums.Status.error);
        }
    }

    /**
     * This method return the current timestamp
     * @return - the current timestamp
     */
    private static String getCurrentTimeStampAsStrings() {
        LocalDateTime ldt = new LocalDateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy, MMMM dd, HH:mm:ss");
        return fmt.print(ldt);
    }


    /**
     * This method opens the reporting file from local path
     */
    public static void openHtmlReportFile() {
        try {
            File htmlFile = new File(REPORTING_FILE_PATH);
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (Exception ex) {
            report.log("BaseTest.openReportHtmlFile: " + ex.getMessage(), Enums.Status.error);
        }
    }


}
