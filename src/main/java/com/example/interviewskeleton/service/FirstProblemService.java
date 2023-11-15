package com.example.interviewskeleton.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FirstProblemService {
    private static final String FORBIDDEN_CHARACTER = "a";

    // updated method
    public void saveWords(List<String> words) {

        // Synchronous validation
        if (words.stream().anyMatch(word -> word.contains(FORBIDDEN_CHARACTER))) {
            throw new UnsupportedOperationException("This word is not valid!");
        }

        // Asynchronous processing and collecting results
        List<CompletableFuture<Void>> futures = words.stream()
                .map(word -> CompletableFuture.runAsync(() -> saveWordToExternalApi(word)))
                .collect(Collectors.toList());

    }

    private void saveWordToExternalApi(String word) {
        try {
            // Here we call external API. We use a sleep of 1s to simulate that it takes a lot of time, and we have no control over it.
            Thread.sleep(1000);
        } catch (Throwable anyException) {
            log.info("[BEST EFFORT] Saving word '{}' failed: {}", word, anyException.getMessage());
        }
    }
}
