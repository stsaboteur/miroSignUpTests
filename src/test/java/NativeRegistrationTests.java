import content.pages.EmailConfirmPage;
import content.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static utils.RandomUtils.*;

public class NativeRegistrationTests {

    private static final String EMAIL_CONFIRM_URL = "https://miro.com/email-confirm/";
    private static final String PRIVACY_POLICY_URL = "https://miro.com/legal/privacy-policy/";
    private static final String TERMS_URL = "https://miro.com/legal/terms-of-service/";

    private static final String NOT_ENOUGH_PASS = "Please use 8+ characters for secure password.";
    private static final String WEAK_PASS = "Weak password";
    private static final String SO_SO_PASS = "So-so password";
    private static final String GOOD_PASS = "Good password";
    private static final String GREAT_PASS = "Great password";
    private static final String SAME_PASS = "Sorry, name and password cannot be the same.";

    private static final String NAME_REQUIRED = "Please enter your name.";
    private static final String AGREE_REQUIRED = "Please agree with the Terms to sign up.";
    private static final String EMAIL_REQUIRED = "Enter your email address.";
    private static final String PASS_REQUIRED = "Enter your password.";
    private static final String BAD_EMAIL = "This email can not be registered, please try another domain";
    private static final String INVALID_EMAIL = "Enter a valid email address.";
    private static final String REGISTERED_EMAIL = "Sorry, this email is already registered";
    private static final String COOKIES_REQUIRED = "We use cookies to securely sign you in.\nPlease enable cookies on your browser and try again.";

    private WebDriver driver;

    @BeforeAll
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void getDriver() {
        driver = new ChromeDriver();
    }

    void getDriver(ChromeOptions options) {
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void closeDriver() {
        driver.quit();
    }

    @Test
    public void sucessTest() {

        SignupPage signupPage = new SignupPage(driver);
        EmailConfirmPage emailConfirmPage = new EmailConfirmPage(driver);

        String email = genEmail();

        signupPage
                .open()
                .fillName(genENString(true))
                .fillEmail(email)
                .fillPassword(genENString(8))
                .setUpTerms()
                .subscribeNews()
                .submitForm();

        assertThat(format("Email on confirmation page is correct: %s", email),
                emailConfirmPage.subtitle.getText(), containsString(email));

        //backend checks: all data was saved including news subscription agreement
    }

    @Test
    public void requiredValidationTest() {
        SignupPage signupPage = new SignupPage(driver);

        String email = genEmail();

        signupPage
                .open()
                .closeCookiePopup()
                .submitForm()
                .assertText(signupPage.errorName, NAME_REQUIRED)
                .assertText(signupPage.errorEmail, EMAIL_REQUIRED)
                .assertText(signupPage.errorEmptyPassword, PASS_REQUIRED)
                .assertText(signupPage.errorTerms, AGREE_REQUIRED);
        signupPage
                .fillName(genENString())
                .fillPassword(genENString(8))
                .setUpTerms()
                .fillEmail(email)
                .submitForm();
        assertThat(format("Gone to %s", EMAIL_CONFIRM_URL),
                driver.getCurrentUrl(), equalTo(EMAIL_CONFIRM_URL));

        //backend checks: all data was saved, news subscription was not agreed
    }

    @Test
    public void passwordValidationTest() {
        SignupPage signupPage = new SignupPage(driver);

        String email = genEmail();
        String name = genENString(9);

        signupPage
                .open()
                .closeCookiePopup()
                .fillName(name)
                .fillPassword(name)
                .setUpTerms()
                .fillEmail(email)
                .submitForm()
                .assertText(signupPage.errorPassword, SAME_PASS);
        signupPage
                .fillPassword(genSameLetter(7))
                .assertText(signupPage.hintPassword, NOT_ENOUGH_PASS)
                .fillPassword(genSameLetter(1))
                .assertText(signupPage.hintPassword, WEAK_PASS)
                .fillPassword(genSameLetter(3))
                .assertText(signupPage.hintPassword, SO_SO_PASS)
                .fillPassword(genSameLetter(2))
                .assertText(signupPage.hintPassword, GOOD_PASS)
                .fillPassword(genSameLetter(2))
                .assertText(signupPage.hintPassword, GREAT_PASS);
        signupPage
                .setUpTerms()
                .submitForm();
        assertThat(format("Gone to %s", EMAIL_CONFIRM_URL),
                driver.getCurrentUrl(), equalTo(EMAIL_CONFIRM_URL));
    }

    @Test
    public void emailValidationTest() {
        SignupPage signupPage = new SignupPage(driver);

        String email = genEmail();
        String badEmail = genEmail("test.com");

        signupPage
                .open()
                .closeCookiePopup()
                .fillName(genENString())
                .fillPassword(genENString(8))
                .setUpTerms()
                .fillEmail(genEmail(""))
                .submitForm()
                .assertText(signupPage.errorEmail, INVALID_EMAIL);
        signupPage
                .fillEmail(badEmail)
                .submitForm()
                .assertText(signupPage.errorEmail, BAD_EMAIL);
        signupPage
                .fillPassword(genENString(8))
                .setUpTerms()
                .fillEmail(email)
                .submitForm();
        assertThat(format("Gone to %s", EMAIL_CONFIRM_URL),
                driver.getCurrentUrl(), equalTo(EMAIL_CONFIRM_URL));
        signupPage
                .open()
                .fillName(genENString())
                .fillPassword(genENString(8))
                .setUpTerms()
                .fillEmail(email)
                .submitForm()
                .assertText(signupPage.errorEmail, REGISTERED_EMAIL);
    }

    @Test
    public void cookieValidationTest() {
        driver.quit();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.managed_default_content_settings.cookies", 2);
        options.setExperimentalOption("prefs", prefs);

        getDriver(options);
        SignupPage signupPage = new SignupPage(driver);

        signupPage
                .open()
                .assertText(signupPage.noticeTerms, COOKIES_REQUIRED);
    }

    @Test
    public void linkChecksTest() {
        SignupPage signupPage = new SignupPage(driver);

        signupPage
                .open()
                .linkPolicy.click();
        signupPage
                .linkTerms.click();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver
                .switchTo().window(tabs.get(2));
        assertThat(format("Gone to %s", PRIVACY_POLICY_URL),
                driver.getCurrentUrl(), equalTo(PRIVACY_POLICY_URL));
        driver
                .switchTo().window(tabs.get(1));
        assertThat(format("Gone to %s", TERMS_URL),
                driver.getCurrentUrl(), equalTo(TERMS_URL));
    }
}
