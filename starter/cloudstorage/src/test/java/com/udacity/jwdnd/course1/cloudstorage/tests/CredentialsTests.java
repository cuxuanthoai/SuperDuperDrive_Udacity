package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginForm;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialsTests extends BaseWeb {

	WebDriverWait wait ;


	@BeforeClass
	public void initialize() {
		var login = new LoginForm(driver);

		driver.get("http://localhost:" + this.port + "/login");
		login.setUsernameValue("123");
		login.setPasswordValue("123");
		login.isLoginSuccessful();

		Assert.assertTrue(login.isLoginSuccessful());

		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-credentials-tab")).click();
	}


	@Test
	public void createCredential() {

		 wait = new WebDriverWait(driver, 5);

		// Creating credential
		var addCredentialButton  = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential-button")));
		addCredentialButton .click();

		var credentialUrlInput  = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		credentialUrlInput .clear();
		credentialUrlInput .sendKeys("https://gmail.com");

		var credentialUsernameInput  = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
		credentialUsernameInput .clear();
		credentialUsernameInput .sendKeys("default");

		var credentialPasswordField  = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
		credentialPasswordField .clear();
		credentialPasswordField .sendKeys("abc123456");

		var saveCredentialButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-credential")));
		saveCredentialButton.click();

		var responseDialog = wait.until(ExpectedConditions.elementToBeClickable(By.id("alertModalBody")));
		Assertions.assertEquals(responseDialog.getText(), "Your changes were successfully saved.");

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();


		var found = wait.until(ExpectedConditions.textToBe(
			By.xpath("//*[@id='credentialTable']/tbody/tr/th"),
			"https://gmail.com")
		);

		Assert.assertTrue(found);


		var passwordColumn = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[3]"));
		wait.until(ExpectedConditions.visibilityOf(passwordColumn));

		Assert.assertNotSame("abc123456", passwordColumn.getText());
	}

	@Test
	public void editCredential() {

		 wait = new WebDriverWait(driver, 10);


		var notesTable = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody"));

		var initialCredentialPassword  = wait.until(
			ExpectedConditions.elementToBeClickable(
				notesTable.findElement(By.tagName("th"))
						  .findElement(By.tagName("span"))
			)
		).getText();

		var editCredentialButton = wait.until(
			ExpectedConditions.elementToBeClickable(
				notesTable.findElement(By.tagName("td"))
						  .findElement(By.tagName("button")))
		);

		editCredentialButton.click();

		var credentialUrlField = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		credentialUrlField.clear();
		credentialUrlField.sendKeys(".com/c/26d470fc-4a3c-42da-88e5-0c7723264c");

		var credentialUsernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
		credentialUsernameField.clear();
		credentialUsernameField.sendKeys("com");

		var credentialPasswordField  = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
		credentialPasswordField .clear();
		credentialPasswordField .sendKeys("123");

		var saveCredentialButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-credential")));
		saveCredentialButton.click();

		var responseDialog = wait.until(ExpectedConditions.elementToBeClickable(By.id("alertModalBody")));
		Assert.assertEquals(responseDialog.getText(), "Your changes were successfully saved.");

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

		var found = wait.until(ExpectedConditions.textToBe(
				By.xpath("//*[@id='credentialTable']/tbody/tr/th"),
				"https://gmail.com")
		);

		Assert.assertTrue(found);

		var passwordColumn = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[3]"));
		wait.until(ExpectedConditions.visibilityOf(passwordColumn));

		Assert.assertNotSame(initialCredentialPassword , passwordColumn.getText());
		Assert.assertNotSame("123", passwordColumn.getText());
	}

	@Test
	public void deleteCredential() {

	  wait = new WebDriverWait(driver, 10);

		// Removing note
		var credentialsTable = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody"));
		var initialCredentialNumber = credentialsTable.findElements(By.tagName("tr")).size();

		var deleteCredentialButton  = wait.until(
			ExpectedConditions.elementToBeClickable(
				credentialsTable
					.findElement(By.tagName("td"))
					.findElement(By.tagName("a"))
			)
		);

		deleteCredentialButton .click();

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

		wait.until(
			ExpectedConditions.numberOfElementsToBe(
				By.xpath("//*[@id='credentialTable']/tbody/tr"),
				initialCredentialNumber - 1
			)
		);
	}

}
