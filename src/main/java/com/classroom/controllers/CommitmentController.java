package com.classroom.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classroom.entity.Commitment;
import com.classroom.repository.CommitmentRepository;

@RestController
@RequestMapping("/")
public class CommitmentController {
	
	@Autowired
	CommitmentRepository repo;
	
	@GetMapping("/commitment/contact/{name}")
	public ResponseEntity<List<Commitment>> consultCommitmentByNameContact(@PathVariable("name") String name){
		return ResponseEntity.ok(repo.consultCommitmentByNameContact(name));
	}
	
	@GetMapping("/commitment")
	public ResponseEntity<List<Commitment>> getCommitment() {
		return ResponseEntity.ok(repo.findAll());
	}
	
	@PostMapping("/commitment")
	public ResponseEntity<Commitment> postCommitment(@RequestBody Commitment commitment){
		return ResponseEntity.ok(repo.save(commitment));
	}
}
