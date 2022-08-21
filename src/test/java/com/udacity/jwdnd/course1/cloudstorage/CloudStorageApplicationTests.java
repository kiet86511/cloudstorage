package com.udacity.jwdnd.course1.cloudstorage;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}

	@Test
	public void getLoginPage() throws InterruptedException {
		this.driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
	 * checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Check that the sign up was successful. // You may have to modify the element
		 * "success-msg" and the sign-up // success message below depening on the rest
		 * of your code.
		 */
		Assertions
				.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	/**
	 * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
	 * checks.
	 **/
	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code. This test is provided by Udacity to perform some basic
	 * sanity testing of your code to ensure that it meets certain rubric criteria.
	 * 
	 * If this test is failing, please ensure that you are handling redirecting
	 * users back to the login page after a succesful sign up. Read more about the
	 * requirement in the rubric: https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection", "Test", "RT", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-link")));
		WebElement backToLoginLink = driver.findElement(By.id("login-link"));
		backToLoginLink.click();

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code. This test is provided by Udacity to perform some basic
	 * sanity testing of your code to ensure that it meets certain rubric criteria.
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL", "Test", "UT", "123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code. This test is provided by Udacity to perform some basic
	 * sanity testing of your code to ensure that it meets certain rubric criteria.
	 * 
	 * If this test is failing, please ensure that you are handling uploading large
	 * files (>1MB), gracefully in your code.
	 * 
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload
	 * Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File", "Test", "LFT", "123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();

		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	@Test
	public void testLogout() {
		doMockSignUp("Large File", "Test", "LogOut", "123");
		doLogIn("LogOut", "123");
		
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
		WebElement logoutButton = driver.findElement(By.id("logout"));
		logoutButton.click();
		
		driver.get("http://localhost:" + this.port + "/home");
		
		Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}
	
	@Test
	public void testNoLogin() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}
	
	/**
	 * Test case for view uploaded file
	 */
	@Test
	public void testViewUploadedFile() {
		// Create a test count
		doMockSignUp("Success Login", "Test", "ViewFile", "ViewFile");
		doLogIn("ViewFile", "ViewFile");

		// Upload a valid file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		String fileName = "ViewUpLoadFile.xlsx";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uploadButton")));
		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertTrue(driver.getPageSource().contains("ViewUpLoadFile.xlsx"));
	}

	/**
	 * Test case for delete file
	 */
	@Test
	public void testDeleteFile() {
		// Create a test count
		doMockSignUp("Success Login", "Test", "DELETEFILE", "DELETEFILE");
		doLogIn("DELETEFILE", "DELETEFILE");

		// Upload a valid file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "fileDelete.xlsx";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uploadButton")));
		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("file-delete")));
		WebElement deleteFile = driver.findElement(By.id("file-delete"));
		deleteFile.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertFalse(driver.getPageSource().contains("UploadFile.xlsx"));
	}

	/**
	 * Test case for adding note
	 */
	@Test
	public void testAddNote() {
		// Create a test count
		doMockSignUp("Success File", "Test", "KietNQAN", "KietNQAN");
		doLogIn("KietNQAN", "KietNQAN");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note")));
		WebElement addNewNoteBtn = driver.findElement(By.id("new-note"));
		addNewNoteBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteModal")));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleTxt = driver.findElement(By.id("note-title"));
		noteTitleTxt.sendKeys("Test add note title");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescriptionTxt = driver.findElement(By.id("note-description"));
		noteDescriptionTxt.sendKeys("Test add note description");

		WebElement saveChangesBtn = driver.findElement(By.id("save-note-btn"));
		saveChangesBtn.click();

		Assertions.assertTrue(driver.getPageSource().contains("Test add note title"));
	}

	/**
	 * Test case for editing note
	 */
	@Test
	public void testEditNote() {
		// Create a test count
		doMockSignUp("Success File", "Test", "KietNQEN", "KietNQEN");
		doLogIn("KietNQEN", "KietNQEN");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note")));
		WebElement addNewNoteBtn = driver.findElement(By.id("new-note"));
		addNewNoteBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteModal")));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleTxt = driver.findElement(By.id("note-title"));
		noteTitleTxt.sendKeys("Test add note title");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescriptionTxt = driver.findElement(By.id("note-description"));
		noteDescriptionTxt.sendKeys("Test add note description");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-btn")));
		WebElement saveChangesBtn = driver.findElement(By.id("save-note-btn"));
		saveChangesBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-note-btn")));
		WebElement viewNoteBtn = driver.findElement(By.id("view-note-btn"));
		viewNoteBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteModal")));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTitleTxt = driver.findElement(By.id("note-title"));
		noteTitleTxt.clear();
		noteTitleTxt.sendKeys("Test edited note title");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		noteDescriptionTxt = driver.findElement(By.id("note-description"));
		noteDescriptionTxt.clear();
		noteDescriptionTxt.sendKeys("Test add note description Editted");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-btn")));
		saveChangesBtn = driver.findElement(By.id("save-note-btn"));
		saveChangesBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Assertions.assertTrue(driver.getPageSource().contains("Test edited note tit"));
	}

	/**
	 * Test case for deleting note
	 */
	@Test
	public void testDeleteNote() {
		// Create a test count
		doMockSignUp("Success File", "Test", "KietNQDN", "KietNQDN");
		doLogIn("KietNQDN", "KietNQDN");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note")));
		WebElement addNewNoteBtn = driver.findElement(By.id("new-note"));
		addNewNoteBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteModal")));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleTxt = driver.findElement(By.id("note-title"));
		noteTitleTxt.sendKeys("Test add note title");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescriptionTxt = driver.findElement(By.id("note-description"));
		noteDescriptionTxt.sendKeys("Test add note description");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-btn")));
		WebElement saveChangesBtn = driver.findElement(By.id("save-note-btn"));
		saveChangesBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTab2 = driver.findElement(By.id("nav-notes-tab"));
		noteTab2.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-note-btn")));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note")));
		WebElement deleteBtn = driver.findElement(By.id("delete-note"));
		deleteBtn.click();

//		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("view-note-btn")));
		Assertions.assertFalse(driver.getPageSource().contains("Test add note title"));
	}

	/**
	 * Test case for adding credential
	 */
	@Test
	public void testAddCredential() {
		// Create a test count
		doMockSignUp("Success", "Test", "KietNQAC", "KietNQAC");
		doLogIn("KietNQAC", "KietNQAC");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential")));
		WebElement addNewCredentialBtn = driver.findElement(By.id("new-credential"));
		addNewCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModal")));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys("add url");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("add username AC");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("addPassword");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential")));
		WebElement saveChangesBtn = driver.findElement(By.id("save-credential"));
		saveChangesBtn.click();

		Assertions.assertTrue(driver.getPageSource().contains("add url"));
	}

	/**
	 * Test case for editing credential
	 */
	@Test
	public void testEditCredential() {
		// Create a test count
		doMockSignUp("Success Edit credential", "Test", "KietNQEC", "KietNQEC");
		doLogIn("KietNQEC", "KietNQEC");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential")));
		WebElement addNewCredentialBtn = driver.findElement(By.id("new-credential"));
		addNewCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModal")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys("add url");

		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("add username EC");

		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("addPassword");
		WebElement saveChangesBtn = driver.findElement(By.id("save-credential"));
		saveChangesBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialsTab1 = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab1.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-credential-btn")));
		WebElement viewCredentialBtn = driver.findElement(By.id("view-credential-btn"));
		viewCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModal")));

		credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.clear();
		credentialUrl.sendKeys("edit url");

		credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.clear();
		credentialUsername.sendKeys("edit username EC");

		credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.clear();
		credentialPassword.sendKeys("editPassword");

		saveChangesBtn = driver.findElement(By.id("save-credential"));
		saveChangesBtn.click();

		Assertions.assertTrue(driver.getPageSource().contains("edit url"));
	}

	/**
	 * Test case for deleting credential
	 */
	@Test
	public void testDeleteCredential() {
		// Create a test count
		doMockSignUp("Success", "Test", "KietNQDC", "KietNQDC");
		doLogIn("KietNQDC", "KietNQDC");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential")));
		WebElement addNewCredentialBtn = driver.findElement(By.id("new-credential"));
		addNewCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModal")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys("add url");

		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("add username DC");

		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("addPassword");
		WebElement saveChangesBtn = driver.findElement(By.id("save-credential"));
		saveChangesBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialsTab1 = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab1.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-credential-btn")));
		WebElement deleteCredentialBtn = driver.findElement(By.id("delete-credential-btn"));
		deleteCredentialBtn.click();

		Assertions.assertFalse(driver.getPageSource().contains("add url"));
	}

}
