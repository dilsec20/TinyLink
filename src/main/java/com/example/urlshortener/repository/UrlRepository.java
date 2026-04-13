package com.example.urlshortener.repository;

import com.example.urlshortener.model.UrlRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlRecord, Long> {

    Optional<UrlRecord> findByShortCode(String shortCode);
}
