//package net.dzakirin.authservice.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import net.dzakirin.authservice.repository.TokenRepository;
//import net.dzakirin.authservice.services.JwtService;
//import net.dzakirin.authservice.services.UserDetailsServiceImpl;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserDetailsServiceImpl userDetailsService;
//    private final TokenRepository tokenRepository;
//
//    /**
//     * Inside the doFilterInternal() method:
//     * <p>
//     * 1. Extract the JWT from the Authorization header (by removing the "Bearer " prefix).
//     * <p>
//     * 2. If the request contains a valid JWT:
//     * - Validate the JWT.
//     * - Parse the username from the JWT.
//     * <p>
//     * 3. Using the username:
//     * - Retrieve the corresponding UserDetails from the UserDetailsService.
//     * - Create an Authentication object using the UserDetails.
//     * <p>
//     * 4. Set the authentication in the SecurityContext:
//     * - Call SecurityContextHolder.getContext().setAuthentication(authentication)
//     * to store the authentication information.
//     * <p>
//     * After this, you can retrieve the authenticated user's details from the SecurityContext
//     * anywhere in your application like this:
//     * <p>
//     * UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
//     * .getAuthentication().getPrincipal();
//     * <p>
//     * You can then access:
//     * - userDetails.getUsername() -> for the username
//     * - userDetails.getPassword() -> for the password
//     * - userDetails.getAuthorities() -> for the roles/authorities
//     *
//     * @param request     the HTTP request
//     * @param response    the HTTP response
//     * @param filterChain the filter chain to proceed with the next filter
//     * @throws ServletException if a servlet error occurs
//     * @throws IOException      if an input/output error occurs
//     */
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain
//    ) throws ServletException, IOException {
//        if (request.getServletPath().contains("/auth")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userName;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        jwt = authHeader.substring(7);
//        userName = jwtService.extractUsername(jwt);
//        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
//            var isTokenValid = tokenRepository.findByToken(jwt)
//                    .map(t -> !t.isExpired() && !t.isRevoked())
//                    .orElse(false);
//            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}
