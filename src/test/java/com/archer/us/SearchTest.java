package com.archer.us;

import com.archer.us.service.EmailExtractorService;
import com.archer.us.service.bing.BingSearchService;
import com.archer.us.service.bing.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/root-context.xml"})
public class SearchTest {
    @Autowired
    private BingSearchService bingSearchService;

    @Autowired
    private EmailExtractorService emailExtractorService;

    @Test
    public void test() {
        Set<Result> results = bingSearchService.search("seattle roofing", 3);
        emailExtractorService.extractEmail(results);
//        System.out.println();
//        extractEmail("a info@real.com b");
    }

    private void extractEmail(String url) {
        Matcher matcher = Pattern.compile("([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})").matcher(url);
        while (matcher.find()) {
            String email = matcher.group(0);
            System.out.println(email);
        }
    }

}
