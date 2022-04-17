import content.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class SocialNetworksRegistrationTests {
    private WebDriver driver;

    private static final String AGREE_REQUIRED = "Please agree with the Terms of Service and Privacy Policy";

    private static final String SLACK_URL = "https://slack.com/";
    private static final String GOOGLE_URL = "https://accounts.google.com/";
    private static final String FACEBOOK_URL = "https://www.facebook.com/";
    private static final String OFFICE_URL = "https://login.microsoftonline.com/";
    private static final String APPLE_URL = "https://appleid.apple.com/";


    @BeforeAll
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void getDriver() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void closeDriver() {
        driver.quit();
    }

    @Test
    public void slackTest() {
        SignupPage signupPage = new SignupPage(driver);

        signupPage
                .open()
                .closeCookiePopup()
                .signUpBySlack.click();
        signupPage
                .submitFromPopup()
                .assertText(signupPage.popupTermsError, AGREE_REQUIRED)
                .setUpTermsPopup()
                .submitFromPopup();
        assertThat(format("Gone to %s", SLACK_URL),
                driver.getCurrentUrl(), containsString(SLACK_URL));
    }

    @Test
    public void googleTest() {
        SignupPage signupPage = new SignupPage(driver);

        signupPage
                .open()
                .closeCookiePopup()
                .signUpByGoogle.click();
        signupPage
                .setUpTermsPopup()
                .submitFromPopup();
        assertThat(format("Gone to %s", GOOGLE_URL),
                driver.getCurrentUrl(), containsString(GOOGLE_URL));
    }

    @Test
    public void appleTest() {
        SignupPage signupPage = new SignupPage(driver);

        signupPage
                .open()
                .closeCookiePopup()
                .signUpByApple.click();
        signupPage
                .setUpTermsPopup()
                .submitFromPopup();
        assertThat(format("Gone to %s", APPLE_URL),
                driver.getCurrentUrl(), containsString(APPLE_URL));
    }

    @Test
    public void officeTest() {
        SignupPage signupPage = new SignupPage(driver);

        signupPage
                .open()
                .closeCookiePopup()
                .signUpByOffice.click();
        signupPage
                .setUpTermsPopup()
                .submitFromPopup();
        assertThat(format("Gone to %s", OFFICE_URL),
                driver.getCurrentUrl(), containsString(OFFICE_URL));
    }

    @Test
    public void facebookTest() {
        SignupPage signupPage = new SignupPage(driver);

        signupPage
                .open()
                .closeCookiePopup()
                .signUpByFacebook.click();
        signupPage
                .setUpTermsPopup()
                .submitFromPopup();
        assertThat(format("Gone to %s", FACEBOOK_URL),
                driver.getCurrentUrl(), containsString(FACEBOOK_URL));
    }
}
