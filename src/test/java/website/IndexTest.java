package website;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SeleniumTest
class IndexTest {

    @Test
    void testSayHello(WebDriver webDriver) {
        webDriver.get("http://127.0.0.1:5500/index.html");
        webDriver.findElement(By.cssSelector("#name-input")).sendKeys("John");
        webDriver.findElement(By.cssSelector("#submit-button")).click();
        String message = webDriver.findElement(By.cssSelector("#message-div")).getText();

        assertEquals("Hello John!", message);
    }
}
