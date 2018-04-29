package com.mohan.playlist.playlistjwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohan.playlist.playlistjwtsecurity.model.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}
