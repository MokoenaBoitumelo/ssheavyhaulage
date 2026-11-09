// backend/src/main/java/com/ssheavyhaulage/controller/QuoteController.java
package com.ssheavyhaulage.controller;

import com.ssheavyhaulage.dto.QuoteRequest;
import com.ssheavyhaulage.dto.QuoteResponse;
import com.ssheavyhaulage.service.QuoteCalculatorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quotes")
@CrossOrigin(origins = "http://localhost:3000") // Secure CORS for React frontend
public class QuoteController {

    private final QuoteCalculatorService quoteCalculatorService;

    public QuoteController(QuoteCalculatorService quoteCalculatorService) {
        this.quoteCalculatorService = quoteCalculatorService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<QuoteResponse> calculateEstimate(@Valid @RequestBody QuoteRequest request) {
        QuoteResponse response = quoteCalculatorService.calculateQuote(request);
        return ResponseEntity.ok(response);
    }
}