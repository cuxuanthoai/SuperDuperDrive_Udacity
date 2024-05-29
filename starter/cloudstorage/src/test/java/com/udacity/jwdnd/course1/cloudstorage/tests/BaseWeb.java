package com.udacity.jwdnd.course1.cloudstorage.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.web.server.LocalServerPort;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseWeb {
  protected WebDriver driver ;
  @LocalServerPort
  protected int port;
  @BeforeMethod
  void setup() {
    WebDriverManager.chromedriver().setup();
  }

  @AfterMethod(alwaysRun = true)
  public void postCondition() {
    driver.quit();
  }

}
