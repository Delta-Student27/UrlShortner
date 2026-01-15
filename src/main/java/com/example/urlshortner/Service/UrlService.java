package com.example.urlshortner.Service;

import org.springframework.stereotype.Service;
import com.example.urlshortner.model.UrlMapping;
import com.example.urlshortner.repository.UrlRepository;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.net.URI;
import java.net.URL;
import java.util.*;

@Service
public class UrlService {
    // syntax check
    public boolean isValidUrl(String url) {
        try {
            URI uri = new URI(url);
            if (uri.getScheme() == null || (!uri.getScheme().equals("http") && !uri.getScheme().equals("https"))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Check if URL really exists
    public boolean isUrlReachable(String urlStr) {
        try {
            
            URI uri = new URI(urlStr);
            if (uri.getScheme() == null || (!uri.getScheme().equals("http") && !uri.getScheme().equals("https"))) {
                return false;
            }

            
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            int code = conn.getResponseCode();
            return code >= 200 && code < 400;

        } catch (Exception e) {
            return false;
        }
    }
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // Single URL
    public String createShortUrl(String originalUrl) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortCode(generateShortCode());
        urlMapping.setClickCount(0L);
        urlMapping.setCreatedAt(LocalDateTime.now());

        urlRepository.save(urlMapping);
        return urlMapping.getShortCode();
    }

    // Multiple URLs
    public Map<String, String> createBulkShortUrls(List<String> urls) {
        Map<String, String> result = new HashMap<>();

        for (String url : urls) {
            if (!isValidUrl(url)) {
                result.put(url, "Invalid URL ❌");  // syntax error
                continue;
            }
            if (!isUrlReachable(url)) {
                result.put(url, "URL unreachable ❌");  // syntax ok but cannot reach
                continue;
            }
            String shortCode = createShortUrl(url);
            result.put(url, shortCode);
        }
        return result;
    }

    // Get original URL
    public String getOriginalUrl(String shortCode) {
        UrlMapping urlMapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        urlMapping.setClickCount(urlMapping.getClickCount() + 1);
        urlRepository.save(urlMapping);

        return urlMapping.getOriginalUrl();
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
