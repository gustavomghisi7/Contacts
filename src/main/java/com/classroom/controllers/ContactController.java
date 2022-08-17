package com.classroom.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.classroom.entity.Contact;
import com.classroom.services.ContactService;
import com.classroom.services.dto.ContactDTO;

@RestController
@RequestMapping("/")
public class ContactController {
	
	@Autowired
	ContactService service;
	
	@GetMapping
	public String home() {
		return "Welcome to home page";
	}
	
	@GetMapping("/contact/email/{email}")
	public ResponseEntity<List<ContactDTO>> getContactByEmail(@PathVariable("email") String email){
		return ResponseEntity.ok(service.consultContactByEmail(email));
	}
	
	@GetMapping("/contact")
	public ResponseEntity<List<ContactDTO>> getContact() {
		List<ContactDTO> contacts = service.consultContact();
		return ResponseEntity.status(HttpStatus.OK).body(contacts);
	}
	
	@GetMapping("/contact/{idcontact}")
	public ResponseEntity<ContactDTO> getContactById(@PathVariable("idcontact") Long idcontact) {
		return ResponseEntity.ok(service.consultContactById(idcontact));
	}
	
	@PostMapping("/contact")
	public ResponseEntity<ContactDTO> postContact(@Valid @RequestBody Contact contact) {
		ContactDTO ct = service.saved(contact);
		return ResponseEntity.status(HttpStatus.CREATED).body(ct);
	}
	
	@DeleteMapping("/contact/{idcontact}")
	public ResponseEntity<Void> deleteContact(@PathVariable("idcontact") Long idcontact) {
		service.excludeContact(idcontact);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/contact/{idcontact}")
	public ResponseEntity<ContactDTO> updateContact(@PathVariable("idcontact")
		Long idcontact, @RequestBody Contact contact) {
		return ResponseEntity.ok(service.chageContact(idcontact, contact));
	}

}
