package net.dzakirin.userservice.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.CustomLog;
import lombok.NoArgsConstructor;
import net.dzakirin.userservice.security.services.UserDetailsServiceImpl;
import net.dzakirin.userservice.security.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@CustomLog
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Inside the doFilterInternal() method:
     * <p>
     * 1. Extract the JWT from the Authorization header (by removing the "Bearer " prefix).
     * <p>
     * 2. If the request contains a valid JWT:
     *    - Validate the JWT.
     *    - Parse the username from the JWT.
     * <p>
     * 3. Using the username:
     *    - Retrieve the corresponding UserDetails from the UserDetailsService.
     *    - Create an Authentication object using the UserDetails.
     * <p>
     * 4. Set the authentication in the SecurityContext:
     *    - Call SecurityContextHolder.getContext().setAuthentication(authentication)
     *      to store the authentication information.
     * <p>
     * After this, you can retrieve the authenticated user's details from the SecurityContext
     * anywhere in your application like this:
     * <p>
     *   UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
     *                               .getAuthentication().getPrincipal();
     * <p>
     * You can then access:
     *   - userDetails.getUsername() -> for the username
     *   - userDetails.getPassword() -> for the password
     *   - userDetails.getAuthorities() -> for the roles/authorities
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to proceed with the next filter
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
