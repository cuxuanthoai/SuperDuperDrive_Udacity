package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginForm;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupForm;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogoutTests  extends  BaseWeb{

	private LoginForm login;
	private SignupForm signup;


	@BeforeClass
	public void initializeForms() {
		login = new LoginForm(driver);
		signup = new SignupForm(driver);
	}

	@Test
	public void successfulLogout() {
		driver.get("http://localhost:" + this.port + "/signup");

		signup.setUsernameValue("thoaikx");
		signup.setPasswordValue("123");
		signup.setLastNameValue("123");
		signup.setFirstNameValue("123");

		signup.submitForm();
		Assert.assertTrue(signup.isRegistrationSuccessful());

		driver.get("http://localhost:" + this.port + "/login");

		login.setUsernameValue("thoaikx");
		login.setPasswordValue("123");
		login.submitLoginForm();

		Assert.assertTrue(login.isLoginSuccessful());

		driver.get("http://localhost:" + this.port + "/logout");
		Assertions.assertNotSame("Home", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assert.assertEquals("Home", driver.getTitle());
	}

}
