package todos_tests;


import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.github.bonigarcia.wdm.WebDriverManager;
import todos_tests.helpers.SeleniumHelpers;

public class TodosTests {
    WebDriver driver;

    @BeforeAll
    public static void initialize() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void prepareDriver() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Backbone.js",
            "AngularJS",
            "Dojo",
            "React"
    })
    public void todosTestCase(String techno) throws Exception {
        driver.get("https://todomvc.com");
        selectLink(techno);
        addTodo("Hello there");
        addTodo("This is Amine Haj Ali");
        addTodo("Can I get a 20?");
        tickTodo(2);
        assertRemainingTodos(2);
        SeleniumHelpers.takeScreenshot(driver, techno);
    }

    private void selectLink(String techno) {
        WebElement element = driver.findElement(By.linkText(techno));
        element.click();
    }

    private void addTodo(String todo) throws InterruptedException {
        WebElement element = driver.findElement(By.className("new-todo"));
        element.sendKeys(todo);
        element.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
    }

    private void tickTodo(int number) {
        driver.findElement(By.cssSelector("li:nth-child(" + number + ") > div > input")).click();
    }

    private void assertRemainingTodos(int expectedRemaining) throws InterruptedException {
        WebElement element = driver.findElement(By.cssSelector("footer > span > strong"));
        validateInnerText(element, Integer.toString(expectedRemaining));
        Thread.sleep(2000);
    }

    private void validateInnerText(WebElement element, String expectedResult) {
        ExpectedCondition<Boolean> expectedConditions =
                ExpectedConditions.textToBePresentInElement(element, expectedResult);
    }

    @AfterEach
    public void quitDriver() {
        driver.quit();
    }
}
