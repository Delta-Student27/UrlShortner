package com.example.urlshortner.dto;

import java.util.List;

public class BulkShortenRequest {
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
