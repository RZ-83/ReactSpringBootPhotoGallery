package com.photogallery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .csrf().disable()
                .formLogin().disable()
                .authorizeExchange()
                .pathMatchers("/albums/admin/**").hasRole("ADMIN")
                .pathMatchers("/albums/**").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/albums").hasAnyRole("ADMIN", "USER")

                .pathMatchers("/photos/admin/**").hasRole("ADMIN")
                .pathMatchers("/photos/**").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/photos").hasAnyRole("ADMIN","USER")

                .pathMatchers("/users/admin/**").hasRole("ADMIN")
                .pathMatchers("/users/**").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/users").hasAnyRole("ADMIN", "USER")

                .pathMatchers("/data/**").hasRole("ADMIN")
                .pathMatchers("/data").hasRole("ADMIN")
                .anyExchange().authenticated()
//                .and().formLogin()
                .and().httpBasic()
                .and().build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user  = User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();

        UserDetails admin  = User.withUsername("zak")
                .password(passwordEncoder.encode("zak123"))
                .roles("ADMIN","USER")
                .build();

        return new MapReactiveUserDetailsService(user,admin);
    }

}
