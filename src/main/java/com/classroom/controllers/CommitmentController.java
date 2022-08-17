package com.classroom.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CommitmentController {
	@GetMapping("/commitment")
	public String hello() {
		return "Hello, everybody!";
	}
}
