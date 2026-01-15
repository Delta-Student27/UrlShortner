package com.example.urlshortner.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.urlshortner.model.UrlMapping;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping,Long> {
    Optional<UrlMapping> findByShortCode(String shortCode);
}
