package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialTest extends CloudStorageApplicationTests {
    private void addCredential(String url, String username, String password) {

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        getDriver().findElement(By.linkText("Credentials")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredentialButton")));
        WebElement addCredentialButton = getDriver().findElement(By.id("addCredentialButton"));
        addCredentialButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credentialUrl = getDriver().findElement(By.id("credential-url"));
        credentialUrl.click();
        credentialUrl.sendKeys(url);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUsername = getDriver().findElement(By.id("credential-username"));
        credentialUsername.click();
        credentialUsername.sendKeys(username);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credentialPassword = getDriver().findElement(By.id("credential-password"));
        credentialPassword.click();
        credentialPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveCredentialChanges")));
        WebElement credentialSubmitButton = getDriver().findElement(By.id("saveCredentialChanges"));
        credentialSubmitButton.click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        getDriver().findElement(By.linkText("here")).click();
    }

    /* Test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted */
    @Test
    public void testAddCredential() {
        doMockSignUp("Add Credential", "Test", "ACT", "123");
        doLogIn("ACT", "123");

        addCredential("http://localhost:8080/login", "admin", "pass");

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        getDriver().findElement(By.linkText("Credentials")).click();

        //Check if the credential is displayed
        Assertions.assertTrue(getDriver().getPageSource().contains("admin"));

        //Check if the password is encrypted
        Assertions.assertNotEquals("pass", getDriver().findElement(By.id("password")).getText());
    }

    /* Test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed. */
    @Test
    public void testEditCredential() {
        doMockSignUp("Edit Credential", "Test", "ECT", "123");
        doLogIn("ECT", "123");

        addCredential("http://localhost:8080/", "admin", "pass");

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        getDriver().findElement(By.linkText("Credentials")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editCredentialButton")));
        WebElement editCredentialButton = getDriver().findElement(By.id("editCredentialButton"));
        editCredentialButton.click();

        //Check if the password is unencrypted
        Assertions.assertEquals("pass", getDriver().findElement(By.id("credential-password")).getAttribute("value"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credentialUrl = getDriver().findElement(By.id("credential-url"));
        credentialUrl.click();
        credentialUrl.sendKeys(" login");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUsername = getDriver().findElement(By.id("credential-username"));
        credentialUsername.click();
        credentialUsername.sendKeys(" user");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credentialPassword = getDriver().findElement(By.id("credential-password"));
        credentialPassword.click();
        credentialPassword.sendKeys("123");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveCredentialChanges")));
        WebElement noteSubmitButton = getDriver().findElement(By.id("saveCredentialChanges"));
        noteSubmitButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        getDriver().findElement(By.linkText("here")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        getDriver().findElement(By.linkText("Credentials")).click();

        //Check if the changes are displayed
        Assertions.assertTrue(getDriver().getPageSource().contains("admin user"));
    }

    /*  Test that deletes a credential and verifies that the credential is no longer displayed. */
    @Test
    public void testDeleteCredential() {
        doMockSignUp("Delete Credential", "Test", "DCT", "123");
        doLogIn("DCT", "123");

        addCredential("http://localhost:8080/", "admin", "pass");

        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        getDriver().findElement(By.linkText("Credentials")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCredentialButton")));
        WebElement editNoteButton = getDriver().findElement(By.id("deleteCredentialButton"));
        editNoteButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
        getDriver().findElement(By.linkText("here")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        getDriver().findElement(By.linkText("Credentials")).click();

        //Check if the credential is no longer displayed
        Assertions.assertFalse(getDriver().getPageSource().contains("admin"));
    }
}
