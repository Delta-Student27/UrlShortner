package com.example.urlshortner.Controller;

import com.example.urlshortner.Service.UrlService;
import com.example.urlshortner.dto.ShortenRequest;
import com.example.urlshortner.dto.BulkShortenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000") // ⭐ IMPORTANT
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
        if (!urlService.isValidUrl(request.getOriginalUrl())) {
            return ResponseEntity.badRequest().body("Invalid URL ❌");
        }
        if (!urlService.isUrlReachable(request.getOriginalUrl())) {
            return ResponseEntity.badRequest().body("URL unreachable ❌");
        }
        String shortCode = urlService.createShortUrl(request.getOriginalUrl());
        return ResponseEntity.ok(shortCode);
    }

    @PostMapping("/shorten/bulk")
    public ResponseEntity<Map<String, String>> bulkShorten(
            @RequestBody BulkShortenRequest request) {

        Map<String, String> result = new HashMap<>();
        for (String url : request.getUrls()) {

            if (!urlService.isValidUrl(url)) {
                result.put(url, "Invalid URL ❌");
                continue;
            }

            if (!urlService.isUrlReachable(url)) {
                result.put(url, "URL unreachable ❌");
                continue;
            }

            String shortCode = urlService.createShortUrl(url);
            result.put(url, shortCode);
        }

        return ResponseEntity.ok(result);
    }
}
