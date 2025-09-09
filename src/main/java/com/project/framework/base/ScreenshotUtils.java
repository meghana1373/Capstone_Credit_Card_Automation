package com.project.framework.base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.Scenario;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {
    public static void capture(String name) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver == null) return;
            File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            Path outDir = Path.of(System.getProperty("user.dir"), "output", "screenshots");
            Files.createDirectories(outDir);
            String fileName = name + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".png";
            Files.copy(src.toPath(), outDir.resolve(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void captureForScenario(Scenario scenario) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver == null) return;
            byte[] bytes = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            // attach to cucumber report
            scenario.attach(bytes, "image/png", scenario.getName());
            // also save to output/screenshots
            capture(scenario.getName().replaceAll("[^a-zA-Z0-9_-]", "_"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
