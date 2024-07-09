package com.conexia.starwars.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String apikey = null;
        String jwtToken = null;
        // Separo el token del 'Bearer ' inicial
        if (!isNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                apikey = jwtTokenUtil.getApiKeyFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.info("JWT Token has expired");
            }
        } else if (!isNull(requestTokenHeader)) {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        // Valido el token si no esta seteado en el contexto
        if (!isNull(apikey) && isNull(SecurityContextHolder.getContext().getAuthentication())) {
            // obtengo el usuario a loguear
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(apikey);
            // configuro el user de spring security
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // seteo la autenticacion el contexto de spring
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}

