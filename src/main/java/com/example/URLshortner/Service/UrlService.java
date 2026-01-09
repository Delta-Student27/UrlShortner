package com.example.URLshortner.Service;

import org.springframework.stereotype.Service;
import com.example.URLshortner.repository.UrlRepository;
import com.example.URLshortner.model.UrlMapping;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UrlService {

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
