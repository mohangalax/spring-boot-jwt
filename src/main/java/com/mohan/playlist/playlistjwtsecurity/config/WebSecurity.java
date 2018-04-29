package com.mohan.playlist.playlistjwtsecurity.config;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mohan.playlist.playlistjwtsecurity.security.JWTAuthenticationFilter;
import com.mohan.playlist.playlistjwtsecurity.security.JWTAuthorizationFilter;

import static com.mohan.playlist.playlistjwtsecurity.constant.SecurityConstants.SIGN_UP_URL;
import static com.mohan.playlist.playlistjwtsecurity.constant.SecurityConstants.H2_CONSOLE_URL;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public WebSecurity(final UserDetailsService userDetailsService, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(H2_CONSOLE_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }
    
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
