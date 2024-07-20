package pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomePageMyntra {
	
	WebDriver driver;
	
	By menMenu = By.xpath("//a[@data-group='men']");
//			By.xpath("//*[@id=\"desktop-header-cnt\"]/div[2]/nav/div/div[1]/div/a");
	By tShirtsOptions = By.xpath("//a[@data-group='men']/following-sibling::div//a[contains(text(),'T-Shirts')]");
//	By.xpath("");
	
	public HomePageMyntra(WebDriver driver) {
		this.driver = driver;
	}
	
	public void hoverOverMenMenu() {
        Actions actions = new Actions(driver);
        WebElement menMenuElement = driver.findElement(menMenu);
        actions.moveToElement(menMenuElement).perform();
    }
	
	public void clickOnFirstTShirtOption() {
        List<WebElement> tShirtsLinks = driver.findElements(tShirtsOptions);
        if (!tShirtsLinks.isEmpty()) {
            tShirtsLinks.get(0).click();
        } else {
            throw new RuntimeException("No T-Shirts options found under Men menu");
        }
	
	
	
	

}
}
