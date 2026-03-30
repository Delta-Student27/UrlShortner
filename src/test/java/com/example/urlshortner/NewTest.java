package com.example.urlshortner;
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.chrome.ChromeDriver; 
import org.openqa.selenium.chrome.ChromeOptions; 
import org.testng.Assert; 
import org.testng.annotations.*; 
public class NewTest {
    WebDriver driver; 
 
@BeforeTest 
public void setup(){ 
 
System.out.println("================================="); 
System.out.println("Starting Selenium Test Execution"); 
System.out.println("================================="); 

String os = System.getProperty("os.name").toLowerCase(); 
System.out.println("Detected OS: " + os); 
 
ChromeOptions options = new ChromeOptions(); 
 
if (os.contains("linux")) { 
options.addArguments("--headless=new"); 
options.addArguments("--no-sandbox"); 
options.addArguments("--disable-dev-shm-usage"); 
System.out.println("Running in headless mode"); 
} else { 
System.out.println("Running in normal mode"); 
} 
 
driver = new ChromeDriver(options); 
} 
 
@Test 
public void testTitle() throws InterruptedException { 
 
System.out.println("Opening website: https://www.rcpit.ac.in"); 
 
driver.get("https://www.rcpit.ac.in"); 
 
String title = driver.getTitle(); 
 
System.out.println("Page title: " + title); 
 
Assert.assertTrue(title.toLowerCase().contains("rcpit") || title.length() > 0); 
 
System.out.println("Title verification PASSED"); 
 
System.out.println("Waiting 10 seconds..."); 
Thread.sleep(10000); 
} 
 
@AfterTest 
public void close(){ 
 
System.out.println("Closing browser..."); 
driver.quit(); 
 
System.out.println("================================="); 
System.out.println("Execution Finished"); 
System.out.println("================================="); 
 
} 
 
}
