package website;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SeleniumTest
class IndexTest {

    @Test
    void testSetBorder(WebDriver webDriver) {
        webDriver.get("http://127.0.0.1:5500/index.html");
        WebElement input = webDriver.findElement(By.id("field-to-validate"));
        String value = input.getText();
        if (value.equals("")) {
            ((JavascriptExecutor) webDriver).executeScript(
                    "arguments[0].style['border'] = '3px solid red';", input);
        }
        System.out.println("End");
    }

    @Test
    void testSayHello(WebDriver webDriver) {
        webDriver.get("http://127.0.0.1:5500/index.html");
        webDriver.findElement(By.cssSelector("#name-input")).sendKeys("John");
        webDriver.findElement(By.cssSelector("#submit-button")).click();

        String message = webDriver.findElement(By.cssSelector("#message-div")).getText();

        findButtonByText(webDriver, "Welcome").click();

        findInputWithLabel(webDriver, "Születési év").sendKeys("Hello");


        assertEquals("Hello John!", message);
    }

    WebElement findButtonByText(WebDriver webDriver, String text) {
        List<WebElement> buttons = webDriver.findElements(By.cssSelector("button"));
        for (WebElement button: buttons) {
            if (button.getText().equals(text)) {
                return button;
            }
        }
        throw new IllegalArgumentException("No button found!");
    }

    WebElement findInputWithLabel(WebDriver webDriver, String text) {
        List<WebElement> labels = webDriver.findElements(By.cssSelector("label"));
        for (WebElement label : labels) {
            if (label.getText().equals(text)) {
                //
                WebElement parent = (WebElement) ((JavascriptExecutor) webDriver).executeScript(
                        "return arguments[0].parentNode;", label);

                return parent.findElement(By.cssSelector("div > input"));
            }
        }
        throw new IllegalArgumentException("Not found");
    }
}
