package com.mohan.playlist.playlistjwtsecurity.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohan.playlist.playlistjwtsecurity.model.ApplicationUser;
import com.mohan.playlist.playlistjwtsecurity.repository.ApplicationUserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserController(final ApplicationUserRepository applicationUserRepository,
                          final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @PostMapping("/sign-up")
    public void signUp(@RequestBody final ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }
}
