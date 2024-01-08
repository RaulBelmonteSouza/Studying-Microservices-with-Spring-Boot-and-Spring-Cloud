package com.squad.currencyexchangeservice;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private final Environment environment;
    private final CurrencyExchangeRepository currencyExchangeRepository;

    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
        this.environment = environment;
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to) {

        CurrencyExchange currencyExchange = currencyExchangeRepository.findTopByFromAndTo(from, to)
                .orElseThrow(() -> new RuntimeException("Unable to find data for " + from + " to " + to));

        String port = environment.getProperty("local.server.port");
        String applicationName = environment.getProperty("spring.application.name");
        currencyExchange.setEnvironment(port + " - " + applicationName);

        return currencyExchange;
    }

}
