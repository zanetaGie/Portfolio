import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class IkeaFiltrTest {
    public static WebDriver driver;


    @BeforeTest
    public void startBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.ikea.com/pl/pl/");
    }

    public void takeScreenShot(int NrTestu) {

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileHandler.copy(src, new File("src/main/resources" + NrTestu + "_screenshot.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    @Test(priority = 1)
    public void closeCookies() {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement acceptButton = driver.findElement(By.xpath("//button [contains(text(),'Akceptuj wszystkie cookies' )] "));
        wait.until(ExpectedConditions.elementToBeClickable(acceptButton));
        acceptButton.click();
        System.out.println("Zaakceptowane cookies");
    }

    @Test(priority = 2)
    public void clickMenuButton() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement menuButton = driver.findElement(By.xpath("//button[@title = 'Menu'][@aria-label = 'Menu'][1]"));
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
    }

    @Test(priority = 3)
    public void leftMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement pomieszczenia = driver.findElement(By.xpath("/html/body/aside/div[3]/nav[1]/ul[1]/li[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(pomieszczenia)).click();
        takeScreenShot(1);
    }

    @Test(priority = 4)
    public void pomieszczenieSypialnia() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement sypialnia = driver.findElement(By.xpath("/html/body/aside/div[3]/nav[3]/ul/li[2]/a/span[2]"));
        wait.until(ExpectedConditions.elementToBeClickable(sypialnia)).click();
    }

    @Test(priority = 5)
    public void szafaChose() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement szafa = driver.findElement(By.xpath("//img[@src = 'https://www.ikea.com/global/assets/navigation/images/wardrobes-19053.jpeg?imwidth=500']"));
        wait.until(ExpectedConditions.elementToBeClickable(szafa)).click();
    }

    @Test(priority = 6)
    public void filtrowanieMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement filtryRight = driver.findElement(By.xpath("/html/body/div[6]/div/div/div[6]/div/div/div[2]/div[1]/div/div/button[8]"));
        filtryRight.click();
    //Zaznaczenie opcji w Filtrach, wyszukanie produktów i zrzut ekranu
        WebElement wlasciwosciCheckbox = driver.findElement(By.xpath("//*[@id='SEC_filter-FEATURE']/label[1]/span/span[1]"));
        if (wlasciwosciCheckbox.isEnabled()) {
            wlasciwosciCheckbox.click();
        } else {
            System.out.println("Niedostępna opcja");
        }

        WebElement cenaCheckbox = driver.findElement(By.xpath("//span[contains(text(), 'Cena')][@class = 'plp-accordion-item-header__title']"));
        if (cenaCheckbox.isEnabled()){
            cenaCheckbox.click();
        }else {
            System.out.println("Niedostępna opcja");
        }
        WebElement cenaValue = driver.findElement(By.xpath("//*[@id=\"SEC_filter-PRICE\"]/label[3]/span"));
        cenaValue.click();

        WebElement resutlsButton = driver.findElement(By.xpath("//span [@class = 'plp-btn__label--regular-weight']"));
        wait.until(ExpectedConditions.visibilityOf(resutlsButton));
        System.out.println(resutlsButton.getText());
        takeScreenShot(2);
        resutlsButton.click();
    }
    @Test (priority = 7)
    public void Sortowanie() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement sort = driver.findElement(By.xpath("//button[@aria-label = 'Pokaż opcje sortowania moda']"));
        wait.until(ExpectedConditions.elementToBeClickable(sort)).click();
    //Sortowanie po opcji najniższa cena i zapisanie obrazu
        WebElement najnizszaCena = driver.findElement(By.xpath("//span[@class = 'plp-radio-button__text'][contains(text(),'najniższej')]"));
        wait.until(ExpectedConditions.visibilityOf(najnizszaCena));
        if (!najnizszaCena.isSelected()) {
            najnizszaCena.click();
        }
        sort.click();
        wait.until(ExpectedConditions.invisibilityOf(najnizszaCena));

        WebElement picture = driver.findElement(By.xpath("//img[@class = 'range-revamp-aspect-ratio-image__image']"));
        wait.until(ExpectedConditions.elementToBeClickable(picture));
        if (picture.isDisplayed()) {
            takeScreenShot(3);
        }
    }
    @Test(priority = 8)
            public void pobieranieNazwPoSortowaniu(){
        //Wypisanie liczby i nazw wyfiltrowanych produktów
        List<WebElement> nazwyElementow = driver.findElements(By.xpath("//div[@class = 'range-revamp-header-section__title--small notranslate']"));

       int liczbaNazwElementow = nazwyElementow.size();
        System.out.println(liczbaNazwElementow);

        for(int i=0; i<liczbaNazwElementow; i++) {
            System.out.println(nazwyElementow.get(i).getText());
        }


    }
}
