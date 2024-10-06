package com.spring.restapi.auth.filter;

import com.spring.restapi.auth.service.JwtService;
import com.spring.restapi.auth.service.TokenService;
import com.spring.restapi.common.response.RestResponse;
import com.spring.restapi.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenService tokenService;
    private final ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            token = authenticationHeader.substring(7);

            try {
                username = jwtService.extractUserName(token);

                if (tokenService.isTokenBlacklisted(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    writeResponse(response, RestResponse.error(HttpServletResponse.SC_UNAUTHORIZED, "Please log in again.", "Token is blacklisted."));
                    return;
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                writeResponse(response, RestResponse.error(HttpServletResponse.SC_UNAUTHORIZED, "Please log in again.", "Token expired at " + tokenService.extractTokenExpiration(token)));
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(UserService.class).loadUserByUsername(username);

            if (tokenService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void writeResponse(HttpServletResponse response, RestResponse<?> restResponse) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = restResponse.toJson();
        try (var writer = response.getWriter()) {
            writer.write(jsonResponse);
            writer.flush();
        }
    }

}
