package com.archer.us.service;

import com.archer.us.service.bing.Result;

import java.util.Set;

public interface SearchService {
    void searchEmail(String url);

    void doRecursiveSearch(String searchEntry);

    Set<Result> search(String query, int page);
}
