package com.example.URLshortner.Service;

import org.springframework.stereotype.Service;
import com.example.URLshortner.repository.UrlRepository;
import com.example.URLshortner.model.UrlMapping;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

@Service
public class UrlService {
    public final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    //Creating Short URL
    public String createShortUrl(String originalUrl){
        UrlMapping urlMapping = new UrlMapping();

        urlMapping.setOriginalUrl(originalUrl);

        String shortCode = generateShortCode();
        urlMapping.setShortCode(shortCode);

        urlMapping.setClickCount(0L);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlRepository.save(urlMapping);
        return shortCode;
    }

    //Generate short code
    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }


    //Get Original URL
    public String getOriginalUrl(String shortCode){
        Optional<UrlMapping> urlMappingOptional = urlRepository.findByShortCode(shortCode);

        UrlMapping urlMapping = urlMappingOptional.orElseThrow(()-> new RuntimeException("URL not found")); 
        urlMapping.setClickCount(urlMapping.getClickCount()+1);

        urlRepository.save(urlMapping);
        return urlMapping.getOriginalUrl();
    }

}
