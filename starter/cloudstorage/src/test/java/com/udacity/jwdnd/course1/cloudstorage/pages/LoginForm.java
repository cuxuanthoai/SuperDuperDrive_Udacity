package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginForm {

    @FindBy(id = "login-form-container")
    private WebElement formContainerElement;

    @FindBy(id = "inputUsername")
    private WebElement usernameInputElement;

    @FindBy(id = "inputPassword")
    private WebElement passwordInputElement;

    @FindBy(id = "login-submit-button")
    private WebElement submitButtonElement;


    public LoginForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void submitLoginForm() {
        submitButtonElement.click();
    }

    public void setUsernameValue(String username) {
        usernameInputElement.clear();
        usernameInputElement.sendKeys(username);
    }

    public void setPasswordValue(String password) {
        passwordInputElement.clear();
        passwordInputElement.sendKeys(password);
    }

    public boolean isLoginSuccessful() {
        return !hasErrorMessage();
    }

    private boolean hasErrorMessage() {
        boolean hasError = false;
        try {
            hasError = formContainerElement.findElements(By.id("invalid-credentials-message")).size() != 0;

        } catch (Exception ignored) {}

        return hasError;
    }


}