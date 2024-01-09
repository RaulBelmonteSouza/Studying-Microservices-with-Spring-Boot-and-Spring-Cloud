package com.squad.currencyconversionservice;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    private final Environment environment;

    public CurrencyConversionController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        String port = environment.getProperty("local.server.port");
        String applicationName = environment.getProperty("spring.application.name");
        String env = port + " - " + applicationName;

        return new CurrencyConversion(10001L,
                from,
                to,
                quantity,
                BigDecimal.valueOf(11),
                BigDecimal.valueOf(12),
                env);
    }

}
