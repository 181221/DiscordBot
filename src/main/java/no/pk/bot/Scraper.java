package no.pk.bot;
import no.pk.attributter.Lenker;
import no.pk.model.Rom;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;

public class Scraper {
    private static final String SUBJECT_LEDIGE_ROM = "Ledige rom: ";
    private static final String DRIVER_LOKAL = System.getenv("DRIVER_LOKAL");
    private WebDriver driver;

    public Scraper() {
        driver = setUpDriver();
    }
    public void loggInnFeide() {
        driver.navigate().to(Lenker.LOGIN_FEIDE);
        WebElement login = driver.findElement(By.className("submit"));
        WebElement username = driver.findElement(By.id("username"));
        WebElement pw = driver.findElement(By.id("password"));
        username.sendKeys(System.getenv("FEIDE_BRUKER").toString());
        pw.sendKeys(System.getenv("FEIDE_PW").toString());
        login.submit();
    }
    public void avsluttDriver() {
        driver.quit();
    }
    private static WebDriver setUpDriver() {
        System.setProperty("webdriver.chrome.driver", DRIVER_LOKAL);
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    }
    //TODO
    public void sendSMSogMail(ArrayList<Rom> allerom) {
        /*String msg = ReaderHjelp.lagMsg();
        setUpMail(TILMAIL, msg, SUBJECT_LEDIGE_ROM);
        TwilioSMS.SendSMS(LedigNaa());*/
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
