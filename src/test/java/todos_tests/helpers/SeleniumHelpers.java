package todos_tests.helpers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SeleniumHelpers {
    public static Path localDir = Paths.get(".").toAbsolutePath();
    public static final String screenshotsPath = "\\screenshots\\%s.png";

    public static void takeScreenshot(WebDriver webdriver, String name) throws Exception {

        TakesScreenshot screenshot = ((TakesScreenshot) webdriver);
        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(localDir.toAbsolutePath().toString() +
                String.format(screenshotsPath, "techno_" + name));
        FileUtils.copyFile(sourceFile, destinationFile);
    }
}
