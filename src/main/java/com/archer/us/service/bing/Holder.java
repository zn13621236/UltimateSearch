package com.archer.us.service.bing;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author: ayang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Holder {
    @JsonProperty("d")
    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}
