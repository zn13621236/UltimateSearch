package com.archer.us;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.archer.us.service.SearchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/root-context.xml" })
public class SeleniumTest {

	@Autowired
	SearchService ss;
	
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
		// Close the browser
		driver.quit();
	}
	
	@Test
	public void searchTest(){
		ss.doRecursiveSearch("seattle roofing");
		
	}
	

}
