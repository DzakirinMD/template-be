package net.dzakirin.templatebe.client.graphql;

import net.dzakirin.templatebe.dto.request.GraphqlRequestBody;
import net.dzakirin.templatebe.dto.response.CountryDto;
import net.dzakirin.templatebe.util.GraphqlSchemaReaderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class CountryClient {

    private final String baseUrl;

    public CountryClient(
            @Value("https://countries.trevorblades.com/") String baseUrl
    ) {
        this.baseUrl = baseUrl;
    }


    public CountryDto getCountryDetails(final String countryCode) throws IOException {

        WebClient webClient = WebClient.builder().build();

        GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

        final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getCountryDetails.graphql")
                .replace("countryCode", countryCode);

        graphQLRequestBody.setQuery(query);

        return webClient.post()
                .uri(baseUrl)
                .bodyValue(graphQLRequestBody)
                .retrieve()
                .bodyToMono(CountryDto.class)
                .block();
    }
}
