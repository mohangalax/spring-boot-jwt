package com.mohan.playlist.playlistjwtsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {
	
	@GetMapping("/verify")
	public String verify() {
		return "Rest connection successfully.";
	}

}
