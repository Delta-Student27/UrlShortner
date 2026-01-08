package com.example.URLshortner.Controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.URLshortner.Service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> createShortUrl(@RequestParam String originalUrl){

        if(originalUrl == null || originalUrl.isEmpty()){
            return ResponseEntity.badRequest().body("Original URL is required");
        }

        String shortCode = urlService.createShortUrl(originalUrl);
        String shortUrl = "https://localhost:8080/" + shortCode;

        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode){
        
            String originalUrl = urlService.getOriginalUrl(shortCode);
            return ResponseEntity.status(302).header("Location", originalUrl).build();
       
    }
}
