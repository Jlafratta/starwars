package com.conexia.starwars.config.jwt;

import com.conexia.starwars.config.jwt.domain.JwtRequest;
import com.conexia.starwars.config.jwt.domain.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest user) throws Exception {
        // Autenticacion del usuario
        authenticate(user.getApiKey(), user.getToken());
        // Traigo el user de la db
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getApiKey());
        // Obtengo el token
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String apiKey, String token) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(apiKey, token));
        } catch (DisabledException e) {
            throw new DisabledException("Api Key deshabilitado");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciales invalidas");
        } catch (InternalAuthenticationServiceException e) {
            throw new UsernameNotFoundException("Api key no encontrado: " + apiKey);
        }
    }
}
