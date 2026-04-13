package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // 1. Endpoint to Shorten the URL
    @PostMapping("/api/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> request) {
        String longUrl = request.get("longUrl");
        if (longUrl == null || longUrl.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "longUrl is required"));
        }

        String shortCode = urlService.shortenUrl(longUrl);
        String shortUrl = "http://localhost:8080/" + shortCode;

        return ResponseEntity.ok(Map.of("shortUrl", shortUrl, "shortCode", shortCode));
    }

    // 2. Endpoint to Redirect to the Long URL
    @GetMapping("/{shortCode}")
    public RedirectView redirectToLongUrl(@PathVariable String shortCode) {
        return urlService.getLongUrl(shortCode)
                .map(UrlController::createRedirectView)
                .orElseGet(() -> {
                    RedirectView redirectView = new RedirectView("/");
                    redirectView.setStatusCode(HttpStatus.NOT_FOUND);
                    return redirectView;
                });
    }

    // Helper to generate the HTTP 302 Redirect
    private static RedirectView createRedirectView(String longUrl) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(longUrl);
        return redirectView;
    }
}
