package com.archer.us.service;

import com.archer.us.service.bing.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

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

            for (Element e : eList) {
                System.out.println(e.text());
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }


    private String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }

    @Override
    public void doRecursiveSearch(final String searchEntry) {
        // The Firefox driver supports javascript
        WebDriver driver = new FirefoxDriver();

        // Go to the Google Suggest home page
        driver.get("http://www.google.com/webhp?complete=1&hl=en");

        // Enter the query string "Cheese"
        WebElement query = driver.findElement(By.name("q"));
        query.sendKeys(searchEntry);

        // Sleep until the div we want is visible or 5 seconds is over
        long end = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < end) {
            WebElement resultsDiv = driver.findElement(By.className("gssb_e"));

            // If results have been returned, the results are displayed in a drop down.
            if (resultsDiv.isDisplayed()) {
                break;
            }
        }

        // And now list the suggestions
        List<WebElement> allSuggestions = driver.findElements(By.xpath("//td[@class='gssb_a gbqfsf']"));

        for (WebElement suggestion : allSuggestions) {
            System.out.println(suggestion.getText());
        }


//        WebDriver driver = new FirefoxDriver();
////		driver.get("http://www.bing.com/");
//        driver.get("http://www.google.com/");
//		WebElement element = driver.findElement(By.name("q"));
//		element.sendKeys(searchEntry);
//
//        driver.findElement(By.name("btnK")).click();
////		element.submit();
//		System.out.println("Page title is: " + driver.getTitle());
//		// Wait for the page to load, timeout after 10 seconds
//		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//			public Boolean apply(WebDriver d) {
//				return d.getTitle().toLowerCase().startsWith(searchEntry);
//			}
//		});
//        System.out.println("Page title is: " + driver.getTitle());
//        Document doc = Jsoup.parse(driver.getPageSource());
//        doRecursiveSearch(doc, driver, 0);
        // Close the browser
        driver.quit();
    }

    @Override
    public Set<Result> search(String query, int page) {
        return null;
    }

    private void doRecursiveSearch(Document doc, WebDriver driver, int counter) {
        counter++;
        if (counter == 2) return;
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            System.out.println(link.attr("abs:href"));
            driver.get(link.attr("abs:href"));
            Document doc2 = Jsoup.parse(driver.getPageSource());
            doRecursiveSearch(doc2, driver, counter);
        }
    }
}
