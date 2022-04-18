package content.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class SignupPage {

    WebDriver driver;
    WebDriverWait wait;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 1);
    }

    public SignupPage open() {
        driver.get("https://miro.com/signup/");
        return this;
    }


    @FindBy(id = "name")
    WebElement fieldName;

    @FindBy(id = "email")
    WebElement fieldEmail;

    @FindBy(id = "password")
    WebElement fieldPassword;

    @FindBy(id = "signup-terms")
    WebElement checkboxTerms;

    @FindBy(id = "signup-subscribe")
    WebElement checkboxNews;


    @FindBy(linkText = "Terms")
    public WebElement linkTerms;

    @FindBy(linkText = "Privacy Policy.")
    public WebElement linkPolicy;

    @FindBy(className = "signup__submit")
    public WebElement submitButton;

    @FindBy(id = "cookie_enable_notice")
    public WebElement noticeTerms;

    @FindBy(id = "termsError")
    public WebElement errorTerms;

    @FindBy(id = "nameError")
    public WebElement errorName;

    @FindBy(id = "emailError")
    public WebElement errorEmail;

    @FindBy(id = "signup-form-password")
    public WebElement hintPassword;

    @FindBy(id = "passwordError")
    public WebElement errorPassword;

    @FindBy(css = "*[data-testid = 'please-enter-your-password-1']")
    public WebElement errorEmptyPassword;


    @FindBy(id = "kmq-slack-button")
    public WebElement signUpBySlack;

    @FindBy(id = "a11y-signup-with-google")
    public WebElement signUpByGoogle;

    @FindBy(id = "kmq-office365-button")
    public WebElement signUpByOffice;

    @FindBy(id = "apple-auth")
    public WebElement signUpByApple;

    @FindBy(className = "signup__btn--facebook")
    public WebElement signUpByFacebook;

    @FindBy(css = "*[data-testid = 'mr-form-gdpr-btn-signin-1']")
    public WebElement popupSubmitButton;

    @FindBy(id = "tos-signup-terms")
    public WebElement popupTermsCheckbox;

    @FindBy(id = "tos-signup-terms-error")
    public WebElement popupTermsError;

    @FindBy(className = "socialtos__close")
    public WebElement popupCloseButton;


    public SignupPage fillName(String name) {
        fieldName.clear();
        fieldName.sendKeys(name);
        return this;
    }

    public SignupPage fillEmail(String name) {
        fieldEmail.clear();
        fieldEmail.sendKeys(name);
        return this;
    }

    public SignupPage fillPassword(String name) {
        fieldPassword.sendKeys(name);
        return this;
    }

    public SignupPage setUpTerms() {
        if (!checkboxTerms.isSelected()) {
            checkboxTerms.sendKeys(" ");
        }
        return this;
    }

    public SignupPage subscribeNews() {
        if (!checkboxNews.isSelected()) {
            checkboxNews.sendKeys(" ");
        }
        return this;
    }

    public SignupPage submitForm() {
        submitButton.click();
        return this;
    }

    public SignupPage submitFromPopup() {
        popupSubmitButton.click();
        return this;
    }

    public SignupPage assertText(WebElement element, String expectedText) {
        wait.until(textToBePresentInElement(element, expectedText));
        return this;
    }

    public SignupPage setUpTermsPopup() {
        if (!popupTermsCheckbox.isSelected()) {
            popupTermsCheckbox.sendKeys(" ");
        }
        return this;
    }

    public SignupPage closeCookiePopup() {
        try {
            driver.findElement(id("onetrust-accept-btn-handler")).click();
        } catch (Exception ignore) {}
        return this;
    }
}
