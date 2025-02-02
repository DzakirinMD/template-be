package net.dzakirin.accountservice.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@UtilityClass
public final class GraphqlSchemaReaderUtil {

    public static String getSchemaFromFileName(final String filename) throws IOException {
        try (InputStream inputStream = GraphqlSchemaReaderUtil.class.getClassLoader().getResourceAsStream("graphql/" + filename)) {
            if (inputStream == null) {
                throw new IOException("Schema file not found: " + filename);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
