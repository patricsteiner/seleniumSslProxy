import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.TestCase.assertEquals;

public class ExampleTest {

    private WebDriver webDriver;
    private SeleniumSslProxy proxy;

    @Before
    public void setup() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File clientSslCertificate = new File(classLoader.getResource("certificates/some-certificate.pfx").getFile());
        String certificatePassword = "superSecret";
        this.proxy = new SeleniumSslProxy(clientSslCertificate, certificatePassword);
        this.proxy.start();
        this.webDriver = setupChromeDriver(this.proxy, false);
    }

    @Test
    public void pageTitleIsFoo() {
        // given
        String url = "http://myurl.lol"; // NOTE: do not use https here!

        // when
        this.webDriver.get(url);
        this.webDriver.manage().timeouts().implicitlyWait(5, SECONDS);

        // then
        WebElement title = this.webDriver.findElement(By.className("title"));
        assertEquals("Foo", title.getText());
    }

    @After
    public void teardown() {
        this.webDriver.quit();
        this.proxy.stop();
    }

    private WebDriver setupChromeDriver(Proxy proxy, boolean headless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("useAutomationExtension", false); // to prevent error popup
        chromeOptions.addArguments("start-maximized");
        chromeOptions.setHeadless(headless);
        if (proxy != null) {
            chromeOptions.setProxy(proxy);
        }
        return new ChromeDriver(chromeOptions);
    }

}
