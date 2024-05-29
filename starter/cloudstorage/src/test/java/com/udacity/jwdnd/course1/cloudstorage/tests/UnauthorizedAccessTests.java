package com.udacity.jwdnd.course1.cloudstorage.tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UnauthorizedAccessTests extends  BaseWeb {


	@BeforeClass
	public void setup() {
		this.driver = new ChromeDriver();
	}

	@Test
	public void redirectToLoginPageForUnauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/home");
		Assert.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unauthorizedUserCanAccessLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assert.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unauthorizedUserCanAccessSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assert.assertEquals("Sign Up", driver.getTitle());
	}

}
