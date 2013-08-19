package com.archer.us.service;

import com.archer.us.service.bing.Result;
import com.archer.us.spider.ThreadPool;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.codehaus.jackson.map.ObjectMapper;
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

    public void extractEmail(Set<Result> urls) {
        if (CollectionUtils.isEmpty(urls))
            return;
        List<Future<Result>> futures = new ArrayList<>();
        final DefaultHttpClient httpClient = new DefaultHttpClient(new PoolingClientConnectionManager());
        for (final Result result : urls) {
            Future<Result> future = ThreadPool.execute(new Callable<Result>() {
                public Result call() {
                    HttpGet getRequest = new HttpGet(result.getUrl());
                    try {
                        HttpResponse response = httpClient.execute(getRequest);
                        String html = IOUtils.toString(response.getEntity().getContent());
                        Matcher matcher = pattern.matcher(html);
                        while (matcher.find()) {
                            Set<String> emails = result.getEmails();
                            if (emails == null) {
                                emails = new HashSet<>();
                                result.setEmails(emails);
                            }
                            String email = matcher.group(0);
                            emails.add(email);
                            logger.debug(email);
                        }
                    } catch (IOException e) {
                        logger.error(String.format("Can't connect %s, due to: ", result), e);
                    }
                    return result;
                }
            });
            futures.add(future);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        int index = 0;
        for (Future<Result> future : futures) {
            logger.debug("Executing thread {}", index);
            try {
                Result result = future.get();
                if (!CollectionUtils.isEmpty(result.getEmails()))
                    System.out.println(objectMapper.writeValueAsString(result));
            } catch (InterruptedException | ExecutionException | IOException e) {
                logger.error("Extract email failure, due to: ", e);
            }
        }
    }
}
