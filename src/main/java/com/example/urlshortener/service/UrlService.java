package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlRecord;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    // Thread-safe counter for generating IDs
    private final AtomicLong counter = new AtomicLong(100000);

    // Base62 Characters (26 lowercase + 26 uppercase + 10 digits = 62)
    private static final String BASE62_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = BASE62_ALPHABET.length();

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String longUrl) {
        // Generate unique ID
        long id = counter.getAndIncrement();

        // Encode ID to Base62 for short code
        String shortCode = encodeBase62(id);

        // Create and save record
        UrlRecord record = new UrlRecord(longUrl, shortCode, LocalDateTime.now());
        urlRepository.save(record);

        return shortCode;
    }

    public Optional<String> getLongUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(UrlRecord::getLongUrl);
    }

    // DSA Algorithm: Base62 Encoder (Convert an integer ID into a string URL code)
    private String encodeBase62(long num) {
        StringBuilder encoded = new StringBuilder();

        if (num == 0) {
            return String.valueOf(BASE62_ALPHABET.charAt(0));
        }

        while (num > 0) {
            int remainder = (int) (num % BASE);
            encoded.append(BASE62_ALPHABET.charAt(remainder));
            num /= BASE;
        }

        return encoded.reverse().toString();
    }
}
