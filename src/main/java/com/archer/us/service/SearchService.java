package com.archer.us.service;

import java.util.Set;

public interface SearchService {
    void searchEmail(String url);

    void doRecursiveSearch(String searchEntry);

    Set<String> search(String query, int page);
}
