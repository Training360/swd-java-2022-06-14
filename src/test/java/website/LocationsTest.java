package website;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SeleniumTest
@Slf4j
class LocationsTest {

    @Test
    @DisplayName("Create location, assert message, then find in table")
    void testCreate(WebDriver driver) {
        driver = new EventFiringDecorator(new ElementFoundEventListener()).decorate(driver);

        driver.get("http://localhost:8080");

        driver.findElement(By.linkText("Create location")).click();

        var name = "Test Location " + UUID.randomUUID();
        driver.findElement(By.id("location-name"))
                .sendKeys(name);
        driver.findElement(By.id("location-coords"))
                .sendKeys("1,1");
        driver.findElement(By.cssSelector("input.btn-primary[value='Create location']"))
                .click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var message = wait.until(
                ExpectedConditions.visibilityOf(driver.findElement(By.id("message-div"))));
        assertEquals("Location has been created", message.getText());

        wait.until(d ->
                d.findElements(By.cssSelector("tr > td:nth-child(2)"))
                        .stream().map(WebElement::getText)
                        .filter(t -> t.equals(name)).count() == 1
                );

    }
}
