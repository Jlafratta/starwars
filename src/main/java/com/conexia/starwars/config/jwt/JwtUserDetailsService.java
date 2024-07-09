package com.conexia.starwars.config.jwt;

import com.conexia.starwars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service()
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String apikey) throws UsernameNotFoundException {
        com.conexia.starwars.domain.model.User user = userRepository.findFirstByApiKey(apikey);
        if (user.getApiKey().equals(apikey)) {
            return new User(user.getApiKey(), user.getToken(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Api Key no encontrado: " + apikey);
        }
    }
}