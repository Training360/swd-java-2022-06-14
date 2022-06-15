package website;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SeleniumTest
@Slf4j
class ComponentsTest {

    @Test
    void testListTexts(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/components/index.html");
        var items = driver.findElements(By.tagName("li"));

//      var texts = new ArrayList<String>();
//        for (var item: items) {
//            texts.add(item.getText());
//        }

//        var texts = items.stream().map(WebElement::getText).toList();
//
//        log.debug("List items: " + texts);
//
//        assertEquals(List.of("One", "Two", "Three", "Four"),
//                texts);

        // import static org.assertj.core.api.Assertions.assertThat;

        assertThat(items)
                .extracting(WebElement::getText)
                .hasSize(4)
                .contains("One", "Three");
    }

    @Test
    void testEmployeesTable(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/components/index.html");
        var cells = driver.findElements(By.cssSelector("td:nth-child(2)"));

        assertThat(cells)
                .extracting(WebElement::getText)
                .contains("John Doe");
    }

    @Test
    void testConvertToObjects(WebDriver webDriver) {
        webDriver.get("http://127.0.0.1:5500/components/index.html");
        var employees = getEmployeesFromTable(webDriver);
        log.debug(employees.toString());

        assertThat(employees)
                .extracting(Employee::getName, Employee::getYearOfBirth)
                .contains(tuple("Jane Doe", 1980));
    }

    List<Employee> getEmployeesFromTable(WebDriver webDriver) {
        var rows = webDriver.findElements(By.tagName("tr"));
        var employees = new ArrayList<Employee>();
        for (int i = 1; i < rows.size(); i++) {
            var row = rows.get(i);
            var cells = row.findElements(By.tagName("td"));
            long id = Long.parseLong(cells.get(0).getText());
            String name = cells.get(1).getText();
            int yearofBirth = Integer.parseInt(cells.get(2).getText());
            var employee = new Employee(id, name, yearofBirth);
            employees.add(employee);
        }
        return employees;
    }
}
