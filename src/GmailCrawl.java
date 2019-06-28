import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GmailCrawl {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String url = "https://www.gmail.com";
        driver.get(url);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Set your email here
        driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys("#########@gmail.com");
        driver.findElement(By.id("identifierNext")).click();

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        // Set your password here
        passwordField.sendKeys("#########");
        driver.findElement(By.id("passwordNext")).click();

        // The main purpose of this sleep is to enter verification raised by google or captcha
        // It needs manual filling captcha field 
        Thread.sleep(30000L);

        List<WebElement> unreadEmails =  driver.findElement(By.className("Cp")).findElement(By.tagName("table")).findElements(By.className("zE"));
        System.out.println();
        System.out.println("The No of your Unread Emails is: " + unreadEmails.size());
        System.out.println();

        for (int i = 0; i < unreadEmails.size(); i++){
            System.out.println("=> Subject " + (i + 1) + ": " + unreadEmails.get(i).findElement(By.className("y6")).getText());
        }

        System.out.println();
        verifier(driver.getCurrentUrl(), driver.getCurrentUrl(), driver);
        System.out.println();
    }

    public static void verifier(String actualUrl, String url, WebDriver driver){
        if (actualUrl.equals(url)){
            System.out.println("Verification Successful - The correct Url is opened.");
        } else {
            System.out.println("Verification Failed - An incorrect Url is opened.");
            System.out.println("Actual URL is : " + actualUrl);
            System.out.println("Expected URL is : " + url);
        }

        String pageSource = driver.getPageSource();
        int pageSourceLength = pageSource.length();
        System.out.println("Total length of the Page Source is : " + pageSourceLength);
    }
}
