package net.dzakirin.accountservice.service;

import lombok.CustomLog;
import net.dzakirin.accountservice.client.graphql.CountryClient;
import net.dzakirin.accountservice.dto.response.CountryDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

@CustomLog
@Service
public class CountryService {

    private final CountryClient countryClient;

    public CountryService(CountryClient countryClient) {
        this.countryClient = countryClient;
    }

    public CountryDto getCountryDetails(String countryCode) throws IOException {
        log.info("Fetching country details");
        return countryClient.getCountryDetails(countryCode);
    }
}
