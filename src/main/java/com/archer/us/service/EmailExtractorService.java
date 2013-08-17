package com.archer.us.service;

import com.archer.us.spider.ConnectionFactory;
import com.archer.us.spider.ThreadPool;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: ayang
 */
@Service
public class EmailExtractorService {
    private static final Logger logger = LoggerFactory.getLogger(EmailExtractorService.class);
    private static final String regex = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
    private static final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    public Set<String> extractEmail(Set<String> urls) {
        if (CollectionUtils.isEmpty(urls))
            return null;
        for (String url : urls) {
            System.out.println(url);
        }
        List<Future<List<String>>> futures = new ArrayList<>();
        for (final String url : urls) {
            Future<List<String>> future = ThreadPool.execute(new Callable<List<String>>() {
                public List<String> call() {
                    List<String> emails = new ArrayList<>();
                    try {
                        Document document = ConnectionFactory.getConnection(url, 60000).get();
                        Matcher matcher = pattern.matcher(document.toString());
                        while (matcher.find()) {
                            String email = matcher.group(0);
                            emails.add(email);
                            logger.debug(email);
                        }
                    } catch (IOException e) {
                        logger.error(String.format("Can't connect %s, due to: ", url), e);
                    }
                    return emails;
                }
            });
            futures.add(future);
        }
        Set<String> emails = new HashSet<>();
        for (Future<List<String>> future : futures) {
            try {
                emails.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Extract email failure, due to: ", e);
            }
        }
        ThreadPool.shutdown();
        return emails;
    }
}
