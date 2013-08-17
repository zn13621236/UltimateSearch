package com.archer.us;

import com.archer.us.service.EmailExtractorService;
import com.archer.us.service.bing.BingSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
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
//        Set<String> urls = bingSearchService.search("seattle roofing", 3);
        System.out.println(emailExtractorService.extractEmail("http://www.seattle-roofing.com/"));
    }

}
