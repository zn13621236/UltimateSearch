package com.archer.us;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTest {

	@Test
	public void test() {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.google.com");
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("seattle roofing");
		element.submit();
		System.out.println("Page title is: " + driver.getTitle());
		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("seattle roofing");
			}
		});

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());
	//	System.out.println(driver.getPageSource());
		
		
		Document doc = Jsoup.parse(driver.getPageSource());
		Elements links = doc.select("a[href]");
		 for (Element link : links) {
			 System.out.println(link.attr("abs:href")+"/n");	 
			 driver.get(link.attr("abs:href"));
			 System.out.println(driver.getCurrentUrl()+"/n");
			 System.out.println(driver.getTitle()+"/n");
	        }
//         List<WebElement> aList=driver.findElements(By.linkText("http"));
//        for(WebElement we:aList){
//        	System.out.println(we.getText()+"\n");
//        	
//        }
		// Close the browser
		driver.quit();
	}

}
