package com.classroom.services.dto;

import java.util.ArrayList;
import java.util.List;

import com.classroom.entity.Contact;

public class ContactDTO {
	
	private Long id;
	private String name;
	private String telephone;
	private String email;
	
	
	public ContactDTO() {

	}

	public ContactDTO(Long id, String name, String telephone, String email) {
		this.id = id;
		this.name = name;
		this.telephone = telephone;
		this.email = email;
	}
	
	public ContactDTO(Contact contact) {
		this.id = contact.getId();
		this.name = contact.getName();
		this.telephone = contact.getTelephone();
		this.email = contact.getEmail();
	}
	
	public static List<ContactDTO> convertToDTO(List<Contact> contacts){
		List<ContactDTO> contactsDTO = new ArrayList<>();
		for (Contact ct : contacts) {
			contactsDTO.add(new ContactDTO(ct));
		}
		
		return contactsDTO;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
