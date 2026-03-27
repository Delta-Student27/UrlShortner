package com.example.urlshortner.Controller;

import com.example.urlshortner.Service.UrlService;
import com.example.urlshortner.dto.ShortenRequest;
import com.example.urlshortner.dto.BulkShortenRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // ===============================
    // Health Check
    // ===============================
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("URL Shortener Backend is running!");
    }

    // ===============================
    // Create Short URL (Single)
    // ===============================
    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody ShortenRequest request) {

        if (request.getOriginalUrl() == null || request.getOriginalUrl().isEmpty()) {
            return ResponseEntity.badRequest().body("URL cannot be empty ❌");
        }

        if (!urlService.isValidUrl(request.getOriginalUrl())) {
            return ResponseEntity.badRequest().body("Invalid URL ❌");
        }

        if (!urlService.isUrlReachable(request.getOriginalUrl())) {
            return ResponseEntity.badRequest().body("URL unreachable ❌");
        }

        String shortCode = urlService.createShortUrl(request.getOriginalUrl());

        Map<String, String> response = new HashMap<>();
        response.put("shortUrl", "http://localhost:8082/api/" + shortCode);

        return ResponseEntity.ok(response);
    }

    // ===============================
    // Create Short URLs (Bulk)
    // ===============================
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
            result.put(url, "http://localhost:8082/api/" + shortCode);
        }

        return ResponseEntity.ok(result);
    }

    // ===============================
    // Redirect Short URL
    // ===============================
    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode,
                         HttpServletResponse response) throws IOException {

        String originalUrl = urlService.getOriginalUrl(shortCode);

        if (originalUrl == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short URL not found ❌");
            return;
        }

        response.sendRedirect(originalUrl);
    }
}