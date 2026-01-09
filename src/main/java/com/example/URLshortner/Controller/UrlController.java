package com.example.URLshortner.Controller;

import com.example.URLshortner.Service.UrlService;
import com.example.URLshortner.dto.ShortenRequest;
import com.example.URLshortner.dto.BulkShortenRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    @GetMapping("/")
    public String home() {
        return "URL Shortener Backend is running!";
    }



    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody ShortenRequest request) {
        String shortCode = urlService.createShortUrl(request.getOriginalUrl());
        return ResponseEntity.ok(shortCode);
    }


    @PostMapping("/shorten/bulk")
    public ResponseEntity<Map<String, String>> bulkShorten(
            @RequestBody BulkShortenRequest request) {

        Map<String, String> result =
                urlService.createBulkShortUrls(request.getUrls());

        return ResponseEntity.ok(result);
    }
}
