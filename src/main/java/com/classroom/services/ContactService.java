package com.classroom.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classroom.entity.Contact;
import com.classroom.repository.ContactRepository;
import com.classroom.services.dto.ContactDTO;
import com.classroom.services.exception.Validation;

@Service
public class ContactService {
	@Autowired
	ContactRepository repo;
	
	public ContactDTO saved(Contact contact) {
		if(contact.getTelephone().length() != 14) {
			throw new Validation("Telephone Invalid");
		}
		
		if(!contact.getEmail().contains("@")) {
			throw new Validation("Email Invalid");
		}
		
		Contact ct = repo.save(contact);
		ContactDTO contactDTO = new ContactDTO(ct);

		return contactDTO;
	}
	
	public List<Contact> consultContact() {
		List<Contact> contact = repo.findAll();
		return contact;
	}
	
	public Contact consultContactById(Long idcontact) {
		Optional<Contact> opcontact = repo.findById(idcontact);
		Contact ct = opcontact.orElseThrow(
				() -> new EntityNotFoundException("Contact not found"));
		return ct;
	}
	
	public void excludeContact(Long idcontact) {
		Contact ct = consultContactById(idcontact);
		repo.delete(ct);
	}
	
	public Contact chageContact(Long idcontact, Contact contact) {
		Contact ct = consultContactById(idcontact);
		BeanUtils.copyProperties(contact, ct, "id");
		/*
		ct.setEmail(contact.getEmail());
		ct.setName(contact.getName());
		ct.setTelephone(contact.getTelephone());
		*/
		return repo.save(ct);
	}
}
