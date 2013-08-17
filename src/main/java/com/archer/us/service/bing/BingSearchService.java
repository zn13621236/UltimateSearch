package com.archer.us.service.bing;

import com.archer.us.service.SearchService;
import com.google.common.base.Charsets;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: ayang
 */
@Repository
public class BingSearchService implements SearchService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(BingSearchService.class);
    @Value("${bing.account.key}")
    private String accountKey;

    @Value("${bing.url}")
    private String url;

    private Client client;
    private String authorization = null;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void searchEmail(String url) {
    }

    @Override
    public void doRecursiveSearch(String searchEntry) {
    }

    @Override
    public Set<String> search(String query, int page) {
        String tmp = url + urlEncode(query);
        Set<String> urls = new HashSet<>();
        search(tmp, urls, page, 0);
        return urls;
    }

    private void search(String url, Set<String> urls, int page, int current) {
        if (current > page)
            return;
        logger.info("The bing search url is {}", url);
        ClientResponse response = client.resource(url).header("Authorization", authorization).get(ClientResponse.class);
        if (response.getStatus() == 200) {
            try {
                Holder holder = objectMapper.readValue(response.getEntity(String.class), Holder.class);
                if (holder != null && holder.getResults() != null) {
                    String next = holder.getResults().getNext();
                    logger.info("The next page is {}", next);
                    List<Result> results = holder.getResults().getResults();
                    if (!CollectionUtils.isEmpty(results)) {
                        for (Result result : results) {
                            urls.add(result.getUrl());
                        }
                    }
                    search(next + "&$format=json", urls, page, ++current);
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        authorization = String.format("Basic %s", new String(Base64.encodeBase64(String.format("%s:%s", accountKey, accountKey).getBytes(Charsets.UTF_8)), Charsets.UTF_8));
        client = Client.create();
    }

    private String urlEncode(String s) {
        if (s == null)
            return null;
        String query;
        try {
            query = URLEncoder.encode(s, Charsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            query = s;
        }
        return String.format("'%s'", query);
    }
}
