package com.ipn.mx.celex.application.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class BrevoEmailClient {

    private final RestClient restClient;
    private final String apiKey;

    public BrevoEmailClient(@Value("${celex.mail.brevo-api-key:}") String apiKey) {
        this.apiKey = apiKey == null ? "" : apiKey.trim();
        this.restClient = RestClient.builder()
                .baseUrl("https://api.brevo.com/v3")
                .build();
    }

    public boolean isConfigured() {
        return !apiKey.isBlank();
    }

    public void send(String fromEmail, String fromName, String to, String subject, String text) {
        Map<String, Object> body = Map.of(
                "sender", Map.of("email", fromEmail, "name", fromName),
                "to", List.of(Map.of("email", to)),
                "subject", subject,
                "textContent", text);

        restClient.post()
                .uri("/smtp/email")
                .contentType(MediaType.APPLICATION_JSON)
                .header("api-key", apiKey)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }
}
