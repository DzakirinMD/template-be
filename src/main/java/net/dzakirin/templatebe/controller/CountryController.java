package net.dzakirin.templatebe.controller;

import io.swagger.v3.oas.annotations.Operation;
import net.dzakirin.templatebe.dto.response.CountryDto;
import net.dzakirin.templatebe.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryClient;

    public CountryController(CountryService countryClient) {
        this.countryClient = countryClient;
    }

    @GetMapping("/{countryCode}")
    @Operation(
            summary = "Find Country by country code",
            description = "An API to find country information by country code using graphql"
    )
    public ResponseEntity<CountryDto> getCountryDetails(@PathVariable String countryCode) throws IOException {
        return ResponseEntity.ok(countryClient.getCountryDetails(countryCode));
    }
}
