package net.dzakirin.cashmanagement.dto.response;

import lombok.Getter;

/**
 * {
 *  "data": {
 *  "country": {
 *     "name": "Belgium",
 *     "capital": "Brussels",
 *     "currency": "EUR"
 *   }
 *  }
 * }
 */

@Getter
public class CountryDto {

    private CountryData data;

    @Getter
    public class CountryData {

        private Country country;

        @Getter
        public class Country {

            private String name;
            private String capital;
            private String currency;
        }
    }
}