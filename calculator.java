import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class calculator {
    public static WebDriver driver;

    //Ustawienia poczatkowe
    @BeforeTest
    public void startBrowser(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.sigmaaldrich.com/PL/pl/support/calculators-and-apps/solution-dilution-calculator");
    }
    //Ustawienia koncowe
    @AfterTest
    public void closeBrowser(){
        driver.quit();
    }

    @Test (priority = 1)
    public void sendStockValue(){
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@name = 'solutiondilution']")));
        WebElement stock = driver.findElement(By.name("inputsc"));
        stock.clear();
        stock.sendKeys("50");
        System.out.println("Wpisano koncentrację początkową: "+stock.getAttribute("value"));
    }

    @Test (priority = 2)
    public void stockUnit(){
        Select stockLista = new Select(driver.findElement(By.name("scunitoption")));
        stockLista.selectByValue("milliM");
        System.out.println("Wybrano wartość " + stockLista.getFirstSelectedOption().getText());
    }

    @Test(priority = 3)
    public void desiredVol (){
        WebElement desiredVolume = driver.findElement(By.name("inputdfv"));
        desiredVolume.clear();
        desiredVolume.sendKeys("300");
        System.out.println("Wpisano oczekiwaną objętość: " + desiredVolume.getAttribute("value"));
    }

    @Test(priority = 4)
    public void desiredVolUnit(){
        Select desiredVoluneLista = new Select(driver.findElement(By.name("vunitoption")));
        desiredVoluneLista.selectByValue("milliL");
        System.out.println("Wybrano wartość " + desiredVoluneLista.getFirstSelectedOption().getText());
    }

    @Test (priority = 5)
    public void desiredConcent (){
        WebElement desiredConration = driver.findElement(By.name("inputdc"));
        desiredConration.clear();
        desiredConration.sendKeys("25");
        //Jesli chcemy zobaczyć alert to wysyłamy większą wartość np. 125
        //desiredConration.sendKeys("125");
        System.out.println("Wpisano oczekiwaną koncentrację: " + desiredConration.getAttribute("value"));
    }
    @Test(priority = 6)
    public void desiredConcentUnit(){
        Select desiredConcentLista =  new Select(driver.findElement(By.name("cunitoption")));
        desiredConcentLista.selectByValue("milliM");
        System.out.println("Wybrano wartość: " + desiredConcentLista.getFirstSelectedOption().getText());
    }
    @Test(priority = 7)
    public void resutl(){
        WebElement requiredVolButtton = driver.findElement(By.xpath("//input[@onclick = 'sdcheckReq()']"));
        requiredVolButtton.click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        WebElement resultFrame = driver.findElement(By.name("resultmass"));
        //Wyprintowanie alertu i zaakceptowanie
        //System.out.println(driver.switchTo().alert().getText());
        //driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("resultmass")));
        WebElement unitFinal = driver.findElement(By.id("volumeunits"));
        System.out.println("Wymagana objętość to: "+ resultFrame.getAttribute("value") +" " + unitFinal.getText());

    }



    }

