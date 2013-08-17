package com.archer.us.service.bing;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author: ayang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {
    private List<Result> results;

    @JsonProperty("__next")
    private String next;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
