package com.projeto.springapi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${api.auth.username}")
    private String authUsername;

    @Value("${api.auth.password}")
    private String authPassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (this.authUsername.equals(username)) {
            return new User(username, new BCryptPasswordEncoder().encode(authPassword),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado com o nome: " + username);
        }
    }

}
