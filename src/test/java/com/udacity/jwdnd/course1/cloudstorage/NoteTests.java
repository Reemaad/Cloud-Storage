package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests extends CloudStorageApplicationTests {

    private void addNote(String noteTitleText, String noteDescriptionText) {

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        getDriver().findElement(By.linkText("Notes")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNoteButton")));
        WebElement addNoteButton = getDriver().findElement(By.id("addNoteButton"));
        addNoteButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = getDriver().findElement(By.id("note-title"));
        noteTitle.click();
        noteTitle.sendKeys(noteTitleText);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = getDriver().findElement(By.id("note-description"));
        noteDescription.click();
        noteDescription.sendKeys(noteDescriptionText);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveNoteChanges")));
        WebElement noteSubmitButton = getDriver().findElement(By.id("saveNoteChanges"));
        noteSubmitButton.click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        getDriver().findElement(By.linkText("here")).click();

    }

    /* Test that creates a note, and verifies it is displayed */
    @Test
    public void testAddNote() {
        doMockSignUp("Add Note", "Test", "ANT", "123");
        doLogIn("ANT", "123");

        addNote("Test Note", "note description...");

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        getDriver().findElement(By.linkText("Notes")).click();

        Assertions.assertTrue(getDriver().getPageSource().contains("Test Note"));
    }

    /* Test that edits an existing note and verifies that the changes are displayed. */
    @Test
    public void testEditNote() {
        doMockSignUp("Edit Note", "Test", "ENT", "123");
        doLogIn("ENT", "123");

        addNote("Test Note", "note description...");

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        getDriver().findElement(By.linkText("Notes")).click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNoteButton")));
        WebElement editNoteButton = getDriver().findElement(By.id("editNoteButton"));
        editNoteButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = getDriver().findElement(By.id("note-title"));
        noteTitle.click();
        noteTitle.sendKeys(" Updated");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = getDriver().findElement(By.id("note-description"));
        noteDescription.click();
        noteDescription.sendKeys(" updated");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveNoteChanges")));
        WebElement noteSubmitButton = getDriver().findElement(By.id("saveNoteChanges"));
        noteSubmitButton.click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        getDriver().findElement(By.linkText("here")).click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        getDriver().findElement(By.linkText("Notes")).click();

        Assertions.assertTrue(getDriver().getPageSource().contains("Test Note Updated"));
    }

    /*  Test that deletes a note and verifies that the note is no longer displayed. */
    @Test
    public void testDeleteNote() {
        doMockSignUp("Delete Note", "Test", "DNT", "123");
        doLogIn("DNT", "123");

        addNote("Test Note", "note description...");

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        getDriver().findElement(By.linkText("Notes")).click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNoteButton")));
        WebElement editNoteButton = getDriver().findElement(By.id("deleteNoteButton"));
        editNoteButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        getDriver().findElement(By.linkText("here")).click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        getDriver().findElement(By.linkText("Notes")).click();

        Assertions.assertFalse(getDriver().getPageSource().contains("Test Note"));
    }
}