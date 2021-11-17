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

import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurzy {

    final String url = "https://cz-test-jedna.herokuapp.com/prihlaseni";
    final String emailElementId= "email";
    final String textPreEmail = "OliJanko123@gmail.com";
    final String hesloElementId = "password";
    final String textPreHeslo = "hesloRodic123";

    WebDriver prohlizec;
    WebDriverWait wait;


    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        wait = new WebDriverWait(prohlizec, 15);
        prohlizec.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    // Test 1
    @Test
    public void prihlasenieDoWebAplikacieAkoRodic() {
        prihlasenieDoAplikacie(url,emailElementId,textPreEmail,hesloElementId,textPreHeslo);
        //dat nech pocka na nacitanie stranky
        final String viditelnyElementNaStrankeXPath = "/html/body/div/div/div/div/div/div[1]/a";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(viditelnyElementNaStrankeXPath)));
    }


    //Test 2 a 3
    @Test
    public void rodicVytvoriNovuPrihlaskuNaKurzProDite(){
        prihlasenieDoAplikacie(url,emailElementId,textPreEmail,hesloElementId,textPreHeslo);

        final String vytvoritNovuPrihlaskuButtonXPath = "/html/body/div/div/div/div/div/div[1]/a";
        klikniNaElement(vytvoritNovuPrihlaskuButtonXPath);


        final String buttonViceInformaciXPath = "/html/body/div/div/div[1]/div[4]/div/div[2]/a";
        klikniNaElement(buttonViceInformaciXPath);


        final String buttonVytvoritPrihlaskuUKurzuXPath = "/html/body/div/div/div/div/div[2]/div/div/div[2]/a";
        klikniNaElement(buttonVytvoritPrihlaskuUKurzuXPath);


        final String buttonTerminXPath =  "/html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/button/div/div/div";
        klikniNaElement(buttonTerminXPath);


        WebElement buttonTerminPolozka = prohlizec.findElement(By.xpath("//*[@id=\"bs-select-1-0\"]"));
        buttonTerminPolozka.click();


        final String inputKrestniJmenoZakaXPath = "//*[@id=\"forename\"]";
        final String krestniJmenoZakaTestovaciHodnota = "Andy";
        vpisHodnotuDoElementu(inputKrestniJmenoZakaXPath,krestniJmenoZakaTestovaciHodnota);


        final String inputPrijmeniZakaXPath = "//*[@id=\"surname\"]";
        final String prijmeniZakaTestovaciHodnota = "Mrzuta";
        vpisHodnotuDoElementu(inputPrijmeniZakaXPath,prijmeniZakaTestovaciHodnota);

        final String inputDatumNarozeniZakaXPath =  "//*[@id=\"birthday\"]";
        final String datumNarozeniTestovaciHodnota = "20.05.2010";
        vpisHodnotuDoElementu(inputDatumNarozeniZakaXPath, datumNarozeniTestovaciHodnota);

        final String radioButtonHotoveXPath = "/html/body/div/div/div/div/div/form/table/tbody/tr[8]/td[2]/span[4]/label";
        klikniNaElement(radioButtonHotoveXPath);

        final String checkboxPodminkyXPath = "/html/body/div/div/div/div/div/form/table/tbody/tr[11]/td[2]/span/label";
        klikniNaElement(checkboxPodminkyXPath);

        final String buttonVytvoritPrihlaskuXPath = "/html/body/div/div/div/div/div/form/table/tbody/tr[11]/td[2]/input";
        klikniNaElement(buttonVytvoritPrihlaskuXPath);

        final String buttonPrihlaskyVMenuXPath = "/html/body/div/header/div/nav/span[2]/a";
        klikniNaElement(buttonPrihlaskyVMenuXPath);

        kontrolaPrihlaskyAOdhlasenieZKurzu(krestniJmenoZakaTestovaciHodnota, prijmeniZakaTestovaciHodnota);

        final String radioButtonNemocXPath = "/html/body/div/div/div/div/div/form/table/tbody/tr[1]/td[2]/span[1]/label";
        klikniNaElement(radioButtonNemocXPath);

        final String buttonOdhlasitZakaXPath = "/html/body/div/div/div/div/div/form/table/tbody/tr[3]/td[2]/input";
        klikniNaElement(buttonOdhlasitZakaXPath);
    }

    // vlastny scenar testu
    @Test
    public void akJerodicPrihlasenyTakhoOdhlasi(){
        prihlasenieDoAplikacie(url,emailElementId,textPreEmail,hesloElementId,textPreHeslo);

        final String  elementPreOdhlasenieXPath = "/html/body/div/header/nav/div/div[2]/div/a";
        klikniNaElement(elementPreOdhlasenieXPath);

        final String buttonOdhlasitXpath= "//*[@id=\"logout-link\"]";
        klikniNaElement(buttonOdhlasitXpath);
    }

    public void kontrolaPrihlaskyAOdhlasenieZKurzu (String krestniJmeno,String prijmeni){
        String riadokPrihlaskyXPath;
        if (prohlizec.findElements(By.xpath("//tr[@class='odd']")).size()!=0){
            riadokPrihlaskyXPath="//tr[@class='odd']";
        }     else {
            riadokPrihlaskyXPath = "//tr[@class='even']";
        }

        final String celeMeno = krestniJmeno + " " + prijmeni;
        WebElement riadokPrihlaskyMeno = prohlizec.findElement(By.xpath(riadokPrihlaskyXPath + "/td[1]"));
        Assertions.assertEquals(celeMeno, riadokPrihlaskyMeno.getText(),"zobrazene meno v riadku prihlasek");

        WebElement buttonOdhlaseniUcasti = prohlizec.findElement(By.xpath(riadokPrihlaskyXPath + "/td[5]/div/a[3]"));
        buttonOdhlaseniUcasti.click();
    }

    public void prihlasenieDoAplikacie (String url, String emailElementId, String email, String hesloElementId ,String heslo){
        prohlizec.navigate().to(url);
        WebElement elementEmail = prohlizec.findElement(By.id(emailElementId));
        elementEmail.sendKeys(email);

        WebElement elementHeslo = prohlizec.findElement(By.id(hesloElementId));
        elementHeslo.sendKeys(heslo);
        elementHeslo.sendKeys(Keys.ENTER);
    }

    public void klikniNaElement (String xPath){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
        WebElement vseobecnyElement = prohlizec.findElement(By.xpath(xPath));
        vseobecnyElement.click();
    }

    public void vpisHodnotuDoElementu (String xPath, String hodnota){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
        WebElement vseobecnyElement = prohlizec.findElement(By.xpath(xPath));
        vseobecnyElement.sendKeys(hodnota);
    }




    @AfterEach
    public void tearDown() {
        prohlizec.quit();
    }
}
