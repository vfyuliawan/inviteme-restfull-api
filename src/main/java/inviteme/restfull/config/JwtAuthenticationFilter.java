package inviteme.restfull.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import inviteme.restfull.utility.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");

            final String jwt;
            final String username;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            log.info("AUTHHEADER {}", jwt);
            // username = jwtService.extractUsername(jwt);
            Claims claims = jwtTokenUtil.parseToken(jwt);
            log.info("AUTHHEADER claims {}", claims);
            username = claims.getSubject();
            log.info("AUTHHEADER USERNAME {}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // if (jwtService.isTokenValid(username, userDetails)) {
                // UsernamePasswordAuthenticationToken authToken = new
                // UsernamePasswordAuthenticationToken(
                // userDetails, null, userDetails.getAuthorities()
                // );
                // authToken.setDetails(new
                // WebAuthenticationDetailsSource().buildDetails(request));
                // SecurityContextHolder.getContext().setAuthentication(authToken);
                // }
                // }
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);

            }

        } catch (ExpiredJwtException e) {
            request.setAttribute("expired", e.getMessage());
            throw e;
        } catch (SignatureException e) {
            request.setAttribute("signatureException", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            request.setAttribute("malformed", e.getMessage());
            throw e;
        }

    }

}

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

// @Autowired
// private JwtTokenUtil jwtTokenUtil;

// @Autowired
// private UserDetailsService userDetailsService;

// @Override
// protected void doFilterInternal(HttpServletRequest request,
// HttpServletResponse response, FilterChain chain)
// throws ServletException, IOException {

// final String authorizationHeader = request.getHeader("Authorization");

// String username = null;
// String jwt = null;

// if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
// {
// jwt = authorizationHeader.substring(7);
// try {
// username = jwtTokenUtil.parseToken(jwt).getSubject();
// } catch (Exception e) {
// logger.error("Token validation failed", e);
// }
// }

// if (username != null &&
// SecurityContextHolder.getContext().getAuthentication() == null) {
// UserDetails userDetails =
// this.userDetailsService.loadUserByUsername(username);

// if
// (jwtTokenUtil.parseToken(jwt).getSubject().equals(userDetails.getUsername()))
// {
// UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
// UsernamePasswordAuthenticationToken(
// userDetails, null, userDetails.getAuthorities());
// usernamePasswordAuthenticationToken
// .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
// SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
// }
// }
// chain.doFilter(request, response);
// }
// }