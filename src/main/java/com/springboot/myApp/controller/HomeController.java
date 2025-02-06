package com.springboot.myApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
public class HomeController {

    private final Map<String, String> searchRequests = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/digital")
    public String digital() {
        return "digital";
    }

    @GetMapping("/physical")
    public String physical() {
        return "physical";
    }

    @PostMapping("/search")
    public String search(@RequestParam("amount") int amount, @RequestParam("mode") String mode, Model model) {
        String searchId = UUID.randomUUID().toString();
        searchRequests.put(searchId, mode + ":" + amount);

        model.addAttribute("searchId", searchId);
        model.addAttribute("message", "A search ID is generated, and your search is in progress");

        scheduler.schedule(() -> {
            searchRequests.remove(searchId);
            // Redirect to home page after timeout
        }, 30, TimeUnit.SECONDS);

        // Check for matching requests
        for (Map.Entry<String, String> entry : searchRequests.entrySet()) {
            if (!entry.getKey().equals(searchId) && entry.getValue().equals((mode.equals("digital") ? "physical" : "digital") + ":" + amount)) {
                String connectionId = UUID.randomUUID().toString();
                model.addAttribute("connectionId", connectionId);
                model.addAttribute("successMessage", "Your connection is successful with user " + entry.getKey() + ", and this is your Connection ID");
                searchRequests.remove(entry.getKey());
                searchRequests.remove(searchId);
                return "connectionSuccess";
            }
        }

        return "searchResult";
    }

    @PostMapping("/proceed")
    public String proceed(@RequestParam("connectionId") String connectionId, Model model) {
        // Handle proceed action for physical user
        model.addAttribute("connectionId", connectionId);
        return "payment";
    }

    @PostMapping("/payment")
    public String payment(@RequestParam("connectionId") String connectionId, Model model) {
        // Integrate payment API here
        RestTemplate restTemplate = new RestTemplate();
        String paymentApiUrl = "https://api.example.com/payments";
        String requestId = UUID.randomUUID().toString();

        // Create a request object for the payment API
        PaymentRequest paymentRequest = new PaymentRequest(connectionId, requestId, 100); // Example amount

        // Make the API call
        ResponseEntity<String> response = restTemplate.postForEntity(paymentApiUrl, paymentRequest, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Payment processed successfully");
        } else {
            model.addAttribute("message", "Payment failed");
        }

        model.addAttribute("requestId", requestId);
        return "acknowledgment";
    }

    // PaymentRequest class to represent the payment request payload
    static class PaymentRequest {
        private String connectionId;
        private String requestId;
        private int amount;

        public PaymentRequest(String connectionId, String requestId, int amount) {
            this.connectionId = connectionId;
            this.requestId = requestId;
            this.amount = amount;
        }

        // Getters and setters
        public String getConnectionId() {
            return connectionId;
        }

        public void setConnectionId(String connectionId) {
            this.connectionId = connectionId;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}