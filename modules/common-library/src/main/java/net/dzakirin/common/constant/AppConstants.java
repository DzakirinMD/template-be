package net.dzakirin.common.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstants {
    // Header
    public static final String HEADER_INTERNAL_API_KEY = "X-Internal-Api-Key";

    // Security / Auth
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    // Pagination Defaults
    public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final String DEFAULT_PAGE_SIZE = "5";
    public static final String DEFAULT_SORT_BY = "createdAt";


}
