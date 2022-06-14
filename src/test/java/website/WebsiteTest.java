package website;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Teszt osztály
@Slf4j
class WebsiteTest {

//    private static final Logger log = LoggerFactory.getLogger(WebsiteTest.class);

    WebDriver driver;

    @BeforeAll
    static void initWebDriverManager() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)
        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        driver = new ChromeDriver(options);
        driver.get("https://www.python.org");
    }

    @AfterEach
    void quitDriver() {
        driver.quit();
    }

    // Teszteset
    @Test
    void testSearch() {
        // Given

        // When
        driver.findElement(By.id("id-search-field")).click();
        driver.findElement(By.id("id-search-field")).sendKeys("testing");
        driver.findElement(By.id("submit")).click();
        log.debug("Click on GO button");

        // Then
        String result = driver.findElement(By.cssSelector("h3:nth-child(2)")).getText();
        assertEquals("Results", result);
    }

    @Test
    void testPsf() {
        driver.findElement(By.linkText("PSF")).click();
        log.debug("Click on PDF menu item");
        assertEquals("Python Software Foundation", driver.getTitle());
    }

}
