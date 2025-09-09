package com.project.framework.utils;

import com.project.framework.base.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    /**
     * Captures a screenshot and saves it in /screenshots folder.
     * Always returns a valid file path (or null if driver is null).
     */
    public static String captureScreenshot(String testName) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) return null;

        // Clean testName for file system
        String safeName = testName.replaceAll("[^a-zA-Z0-9-_]", "_");

        // Directory: <project-root>/screenshots
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        File dir = new File(screenshotDir);
        if (!dir.exists()) dir.mkdirs();

        // File name with timestamp
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String screenshotPath = screenshotDir + safeName + "_" + timestamp + ".png";

        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destFile.getAbsolutePath(); // ✅ Always return path
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
