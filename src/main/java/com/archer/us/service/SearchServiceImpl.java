package com.archer.us.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service("searchService")
public class SearchServiceImpl implements SearchService{

	@Override
	public void searchEmail(String url) {
		// TODO Auto-generated method stub
		 try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			Elements eList = doc.select(":contains(a)");
			
			 for (Element link : links) {
				 System.out.println(link.attr("abs:href"));
				 
		        }
			 
			 for(Element e:eList){
				 System.out.println( e.text());
			 }
			 
			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 private  void print(String msg, Object... args) {
	        System.out.println(String.format(msg, args));
	    }

	
    private  String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
    @Override
    public void doRecursiveSearch(final String searchEntry){
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.bing.com/");
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys(searchEntry);
		element.submit();
		System.out.println("Page title is: " + driver.getTitle());
		// Wait for the page to load, timeout after 10 seconds
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith(searchEntry);
			}
		});
		System.out.println("Page title is: " + driver.getTitle());
		Document doc = Jsoup.parse(driver.getPageSource());
		doRecursiveSearch(doc,driver,0);
		// Close the browser
		driver.quit();    	
    }

	private void doRecursiveSearch(Document doc,WebDriver driver,int counter) {
		counter++;
		if(counter==2) return;
		Elements links = doc.select("a[href]");
		 for (Element link : links) {
			 System.out.println(link.attr("abs:href"));	
			 driver.get(link.attr("abs:href"));
			 Document doc2 = Jsoup.parse(driver.getPageSource());
			 doRecursiveSearch(doc2,driver,counter);
	        }
	}
    
    
}
