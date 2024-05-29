package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupForm {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "signup-submit-button")
    private WebElement submitButton;

    @FindBy(id = "signup-form-container")
    private WebElement formContainer;

    public SignupForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void submitForm() {
        submitButton.click();
    }

    public void setFirstNameValue(String name) {
        firstNameField.clear();
        firstNameField.sendKeys(name);
    }

    public void setLastNameValue(String name) {
        lastNameField.clear();
        lastNameField.sendKeys(name);
    }

    public void setUsernameValue(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void setPasswordValue(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public boolean isRegistrationSuccessful() {
        return !hasErrorMessage();
    }

    private boolean hasErrorMessage() {
        boolean result = false;
            result = formContainer.findElements(By.id("signup-error-message")).size() != 0;

        return result;
    }

}