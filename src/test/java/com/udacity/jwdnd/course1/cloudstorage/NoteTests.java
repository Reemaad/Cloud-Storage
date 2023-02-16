package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthTests extends CloudStorageApplicationTests {

    private WebDriver driver;


    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void addNote() {
        doMockSignUp("test","user","testUser","123");
        doLogIn("testUser","123");


        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        driver.findElement(By.linkText("Notes")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));
        WebElement addNoteButton = driver.findElement(By.id("addNoteButton"));
        addNoteButton.click();

        String noteTitleText = "Note Test";
        String noteDescriptionText = "Note description ...";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.click();
        noteTitle.sendKeys(noteTitleText);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.click();
        noteDescription.sendKeys(noteDescriptionText);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveChanges")));
        WebElement noteSubmitButton = driver.findElement(By.id("saveChanges"));
        noteSubmitButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        driver.findElement(By.linkText("here")).click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        driver.findElement(By.linkText("Notes")).click();


        Assertions.assertTrue(driver.getPageSource().contains(noteTitleText));
    }
}