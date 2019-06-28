import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PortalCrawl {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver");

        WebDriver driver = new ChromeDriver();

        String url = "https://portal.aait.edu.et/";
        driver.get(url);

        WebElement userName = driver.findElement(By.id("UserName"));
        WebElement password = driver.findElement(By.id("Password"));
        WebElement signingButton = driver.findElement(By.id("home")).findElement(By.className("btn"));

        userName.sendKeys("ATR/5064/09");
        password.sendKeys("####");
        signingButton.click();


        String title = driver.getTitle();
        int titleLength = driver.getTitle().length();
        System.out.println("Title of the page is : " + title);
        System.out.println("Length of the title is : "+ titleLength);
        String actualUrl = driver.getCurrentUrl();

        driver.findElement(By.id("m2")).click();
        driver.navigate().to("https://portal.aait.edu.et/Grade/GradeReport");

        String value  = driver.findElement(By.className("table")).getText();

        exportToFile(value);

        tester(actualUrl, url, driver);
    }

    public static void tester(String actualUrl, String url, WebDriver driver){
        if (actualUrl.equals(url)){
            System.out.println("Verification Successful - The correct Url is opened.");
        } else {
            System.out.println("Verification Failed - An incorrect Url is opened.");
            //In case of Fail, you like to print the actual and expected URL for the record purpose
            System.out.println("Actual URL is : " + actualUrl);
            System.out.println("Expected URL is : " + url);
        }

        String pageSource = driver.getPageSource();
        int pageSourceLength = pageSource.length();
        System.out.println("Total length of the Page Source is : " + pageSourceLength);
    }

    public static void exportToFile(String text){
        try (PrintWriter out = new PrintWriter("grade.txt")) {
            out.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
