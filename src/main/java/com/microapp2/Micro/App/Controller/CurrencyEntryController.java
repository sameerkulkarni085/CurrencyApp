package com.microapp2.Micro.App.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CurrencyEntryController {

    @GetMapping("/currency-entry")
    public String showCurrencyEntryPage() {
        return "currencyEntry"; // Refers to currencyEntry.html in the templates folder
    }
    
    @PostMapping("/submit-currency")
    public String processCurrency(@RequestParam("amount") String amount, Model model) {
        // Add logic to start matching based on the entered amount
        model.addAttribute("amount", amount);
        return "matchResult"; // Refers to a matchResult.html template (create as needed)
    }
}