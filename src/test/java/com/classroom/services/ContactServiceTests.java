package com.classroom.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.classroom.entity.Contact;
import com.classroom.repository.ContactRepository;
import com.classroom.services.dto.ContactDTO;

@ExtendWith(SpringExtension.class)
public class ContactServiceTests {
	
	private Long existingId;
	private Long nonExistentId;
	
	private Contact validContact;
	private Contact invalidContact;
	
	@BeforeEach
	void setup() {
		existingId = 1l;
		nonExistentId = 1000l;
		
		validContact = new Contact("Maria", "maria@gmail.com", "(48)99988-1234");
		invalidContact = new Contact("Maria", "mariagmail.com", "(48)99988-123");
		
		Mockito.doThrow(IllegalArgumentException.class).when(repository).save(invalidContact);
		Mockito.when(repository.save(validContact)).thenReturn(validContact);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteById(nonExistentId);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(new Contact()));
		Mockito.doThrow(EntityNotFoundException.class).when(repository).findById(nonExistentId);
	}
	
	@InjectMocks
	ContactService service;

	@Mock
	ContactRepository repository;
	
	@Test
	public void returnExceptionWhenSavingUnsuccessful() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> service.saved(invalidContact));
		Mockito.verify(repository).save(invalidContact);
	}
	
	@Test
	public void returnContactDTOwhenSaveSuccessfully() {
		ContactDTO contactDTO = service.saved(validContact);
		Assertions.assertNotNull(contactDTO);
		Mockito.verify(repository).save(validContact);
	}
	
	@Test
	public void returnNothingWhenDeletingWithExistingId() {
		Assertions.assertDoesNotThrow(() -> {
			service.excludeContact(existingId);
		});
		
		Mockito.verify(repository,Mockito.times(1)).deleteById(existingId);
	}
	
	@Test 
	public void throwsEntityNotFoundWhenDeletingNonExisting() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.excludeContact(nonExistentId);
		});
		
		Mockito.verify(repository,Mockito.times(1)).deleteById(nonExistentId);
	}
	
	@Test
	public void consultByIdReturnsContact() {
		ContactDTO ct = service.consultContactById(existingId);
		Assertions.assertNotNull(ct);
		
		Mockito.verify(repository).findById(existingId);
	}
	
	@Test 
	public void throwsEntityNotFoundWhenConsultNonExisting() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.consultContactById(nonExistentId);
		});
		
		Mockito.verify(repository,Mockito.times(1)).findById(nonExistentId);
	}
	
}
