package com.archer.us.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: ayang
 */
@Service
public class EmailExtractorService {
    private static final Logger logger = LoggerFactory.getLogger(EmailExtractorService.class);
    private static final String regex = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
    public static final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    public Set<String> extractEmail(Set<String> urls) {
        if (CollectionUtils.isEmpty(urls))
            return null;
        Set<String> emails = new HashSet<>();
        for (String url : urls) {
            System.out.println(url);
        }
        for (String url : urls) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.getElementsMatchingText(regex);
                for (Element element : elements) {
                    System.out.println(element.text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return emails;
    }

    public Set<String> extractEmail(String url) {
        Set<String> emails = new HashSet<>();
        try {
            Document document = Jsoup.connect(url).get();
            String html = document.toString();

            Matcher matcher = pattern.matcher(html);
            while (matcher.find()) {
                String email = matcher.group(0);
                System.out.println(email);
                emails.add(email);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emails;
    }
}
