package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginForm;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotesTests extends  BaseWeb {

	WebDriverWait wait ;



	@BeforeClass
	public void initialize() {
		var login = new LoginForm(driver);

		driver.get("http://localhost:" + this.port + "/login");
		login.setUsernameValue("thoaikx");
		login.setPasswordValue("abc123");
		login.isLoginSuccessful();

		Assertions.assertTrue(login.isLoginSuccessful());

		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait wait = new WebDriverWait(driver, 5);

		driver.findElement(By.id("nav-notes-tab")).click();
	}


	@Test
	public void addNewNote() {

			 wait = new WebDriverWait(driver, 10);


		var launchDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-button")));
		launchDialogButton.click();

		var noteTitleInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		noteTitleInput.clear();
		noteTitleInput.sendKeys(" test auto");

		var noteDescriptionInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")));
		noteDescriptionInput.clear();
		noteDescriptionInput.sendKeys("sample.");

		var saveNoteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-note")));
		saveNoteButton.click();

		var responseDialog = wait.until(ExpectedConditions.elementToBeClickable(By.id("alertModalBody")));
		Assertions.assertEquals(responseDialog.getText(), "Your changes were successfully saved.");

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

	}

	@Test
	public void modifyNote() {


		var notes = driver.findElement(By.xpath("//*[@id='userTable']/tbody"));

		var noteInitialTitle = wait.until(
			ExpectedConditions.elementToBeClickable(
				notes.findElement(By.tagName("th"))
						  .findElement(By.tagName("span"))
			)
		).getText();

		var editNoteButton = wait.until(
			ExpectedConditions.elementToBeClickable(
				notes.findElement(By.tagName("td"))
						  .findElement(By.tagName("button")))
		);

		editNoteButton.click();

		var noteTitleInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		noteTitleInput.clear();
		noteTitleInput.sendKeys("Selenium edited...");

		var noteDescriptionInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")));
		noteDescriptionInput.clear();
		noteDescriptionInput.sendKeys("This is just another exercise.");

		var saveNoteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-note")));
		saveNoteButton.click();

		var responseDialog = wait.until(ExpectedConditions.elementToBeClickable(By.id("alertModalBody")));
		Assertions.assertEquals(responseDialog.getText(), "Your changes were successfully saved.");

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

	}

	@Test
	public void eraseNote() {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		var notesTable = driver.findElement(By.xpath("//*[@id='userTable']/tbody"));
		var initialNotesNumber = notesTable.findElements(By.tagName("tr")).size();

		var removeNoteButton = wait.until(
			ExpectedConditions.elementToBeClickable(
				notesTable.findElement(By.tagName("td"))
						  .findElement(By.tagName("a"))
			)
		);

		removeNoteButton.click();

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

	}

}
