package tests;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.jsonwebtoken.security.Keys;

public class Myntratest {
	
	    @SuppressWarnings("deprecation")
		public static void main(String[] args) {
        	System.setProperty("webdriver.chrome.driver", "/Users/atalwar/Downloads/chromedriver-mac-x64/chromedriver");
	        WebDriver driver = new ChromeDriver();
	 
	        try {
	            // Navigate to Myntra
	            ChromeOptions options = new ChromeOptions();
	            driver = new ChromeDriver(options); 
	            driver.manage().window().maximize(); 
	            driver.get("https://www.myntra.com");
	            
	            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	            // Wait for the top menu to load
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#desktop-header-cnt")));

	            // Click on Men and then T-shirts
	            driver.findElement(By.xpath("//a[text()='Men']")).click();
	            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='T-Shirts']"))).click();

	            // Filter by brand (Van Heusen)
	            filterBrand(driver, "Van Heusen");

	            // Find discounted products
	            findDiscountedProducts(driver);

	        } catch (TimeoutException e) {
	            System.out.println("Error: Timeout occurred while waiting for an element.");
	        } catch (NoSuchElementException e) {
	            System.out.println("Error: Element not found.");
	        } finally {
	            driver.quit();
	        }
	    }

	    public static void filterBrand(WebDriver driver, String brandName) {
	        // Wait for the filter menu to load
	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='filter-wrap']")));

	        // Click on the Brand section
	        driver.findElement(By.xpath("//label[text()='Brand']")).click();

	     // Click on the search box
	        driver.findElement(By.xpath("//input[@type='text' and @placeholder='Search Brand']")).click();

	        // Enter brand name and press Enter
	        driver.findElement(By.xpath("//input[@type='text' and @placeholder='Search Brand']")).sendKeys(brandName + "\n");
	        // Wait for the filtered brands to load
	        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@class='filter-dropdown-list']")));

	        // Select the brand
	        driver.findElement(By.xpath("//li[text()='" + brandName + "']")).click();
	    }

	    public static void findDiscountedProducts(WebDriver driver) {
	        // Implement logic to find discounted products and print details (price, discount, link)
	        // You can use similar techniques as in the filterBrand method to locate product elements and extract details.

	        // Example implementation (modify based on actual element structure):
	        for (WebElement productElement : driver.findElements(By.xpath("//div[@class='product-base']"))) {
	            try {
	                if (productElement.findElement(By.xpath(".//span[text()='Discount' or text()='More Discount']")) != null) {
	                    String productName = productElement.findElement(By.xpath(".//a[@class='product-name-link']")).getText();
	                    String price = productElement.findElement(By.xpath(".//span[@class='price']")).getText().strip();
	                    // Extract discount percentage (implementation depends on element structure)
	                    String discount = "..."; // Placeholder, implement logic to get discount

	                    // Print product details
	                    System.out.println("Product: " + productName);
	                    System.out.println("Price: " + price);
	                    System.out.println("Discount: " + discount);
	                    System.out.println("Link: https://www.myntra.com/" + productElement.findElement(By.xpath(".//a[@class='product-base']")).getAttribute("href"));
	                    System.out.println("------------------------");
	                }
	            } catch (NoSuchElementException e) {
	                // Handle cases where elements might not be present for some products
	            }
	        }
	    }
	


}
