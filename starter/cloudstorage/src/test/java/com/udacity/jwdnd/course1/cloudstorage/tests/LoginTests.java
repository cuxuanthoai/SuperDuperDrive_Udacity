package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginForm;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginTests extends  BaseWeb {

	private LoginForm login;
	private SignupForm signup;


	@BeforeClass
	public void initializeForms() {
		login 		= new LoginForm(driver);
		signup 		= new SignupForm(driver);
	}

	private boolean performLogin(String username, String password) {
		driver.get("http://localhost:" + this.port + "/login");

		login.setUsernameValue(username);
		login.setPasswordValue(password);
		login.submitLoginForm();

		return login.isLoginSuccessful();
	}

	private boolean performUserCreation(String firstName, String lastName, String username, String password) {
		driver.get("http://localhost:" + this.port + "/signup");

		signup.setUsernameValue(username);
		signup.setPasswordValue(password);
		signup.setLastNameValue(firstName);
		signup.setFirstNameValue(lastName);
		signup.submitForm();

		return signup.isRegistrationSuccessful();
	}

	@Test
	public void failedLoginForNonexistentUser() {
		Assertions.assertFalse(performLogin("thoaikx", "12345"));
	}

	@Test
	public void successfulLogin() {
		Assertions.assertTrue(
			performUserCreation(
			"thoaikx",
			"thoaikx",
			"thoaikx.",
			"thoaikx"
			)
		);

		Assert.assertTrue(performLogin("thoaikx", "thoaikx"));
		Assert.assertEquals("Home", driver.getTitle());
	}

}
