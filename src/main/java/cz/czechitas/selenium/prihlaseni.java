package cz.czechitas.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;
import java.util.concurrent.TimeUnit;

public class prihlaseni {



    WebDriver prohlizec;
    WebDriverWait wait;


    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        wait = new WebDriverWait(prohlizec, 15);
        prohlizec.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    //test1   -validne udaje existujuceho uzivatela
    @Test
    public void PrihlasenieUzivatelaSPlatymiUdajmiAExistujicimUctom() {
        String url = "http://czechitas-datestovani-hackathon.cz/en/login?back=my-account";
        String emailElementId = "//*[@id=\"email\"]";
        String textPreEmail = "karel.gott@gmail.com";
        String hesloElementId = "//*[@id=\"passwd\"]";
        String textPreHeslo = "jsemkarelgott";
        prihlasenieDoAplikacie(url, emailElementId, textPreEmail, hesloElementId, textPreHeslo);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/ul/li[1]/a/span")));
    }

    //test2   ocakavame ze padne  -spatnyemail
    @Test
    public void PrihlasenieUzivatelaSNevalidnymEmailomVExistujucomUcte() {
        String url = "http://czechitas-datestovani-hackathon.cz/en/login?back=my-account";
        String emailElementId = "//*[@id=\"email\"]";
        String textPreEmail = "karel.gott@gmail3.com";
        String hesloElementId = "//*[@id=\"passwd\"]";
        String textPreHeslo = "jsemkarelgott";
        prihlasenieDoAplikacie(url, emailElementId, textPreEmail, hesloElementId, textPreHeslo);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/ul/li[1]/a/span")));
    }


    //test3   ocakavame ze padne - spatne heslo
    @Test
    public void PrihlasenieUzivatelaSNevalidnymHeslomVExistujucomUcte() {
        String url = "http://czechitas-datestovani-hackathon.cz/en/login?back=my-account";
        String emailElementId = "//*[@id=\"email\"]";
        String textPreEmail = "karel.gott@gmail.com";
        String hesloElementId = "//*[@id=\"passwd\"]";
        String textPreHeslo = "jsemkarelgott789";
        prihlasenieDoAplikacie(url, emailElementId, textPreEmail, hesloElementId, textPreHeslo);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/ul/li[1]/a/span")));
    }

    //test4   odhlasenie z existujuceho uctu
    @Test
    public void OdhlasenieUzivatelaSExistujucimUctom() {
        String url = "http://czechitas-datestovani-hackathon.cz/en/login?back=my-account";
        String emailElementId = "//*[@id=\"email\"]";
        String textPreEmail = "karel.gott@gmail.com";
        String hesloElementId = "//*[@id=\"passwd\"]";
        String textPreHeslo = "jsemkarelgott";
        prihlasenieDoAplikacie(url, emailElementId, textPreEmail, hesloElementId, textPreHeslo);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/ul/li[1]/a/span")));
        WebElement ucetButton = prohlizec.findElement(By.xpath("//*[@id=\"user_info_acc\"]"));
        ucetButton.click();
        WebElement logOutButton = prohlizec.findElement(By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[7]/ul/li/ul/li[3]/a"));
        logOutButton.click();
    }


    public void prihlasenieDoAplikacie(String url, String emailElementId, String textPreEmail, String hesloElementId, String textPreHeslo) {
        prohlizec.navigate().to(url);
        WebElement elementEmail = prohlizec.findElement(By.xpath(emailElementId));
        elementEmail.click();
        elementEmail.sendKeys(textPreEmail);

        WebElement elementHeslo = prohlizec.findElement(By.xpath(hesloElementId));
        elementHeslo.click();
        elementHeslo.sendKeys(textPreHeslo);
        elementHeslo.sendKeys(Keys.ENTER);
    }


    @AfterEach
    public void tearDown() {
        prohlizec.quit();
    }
}
