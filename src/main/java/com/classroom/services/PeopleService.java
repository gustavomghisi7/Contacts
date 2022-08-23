package com.classroom.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classroom.entity.People;
import com.classroom.repository.PeopleRepository;

@Service
public class PeopleService {
	
	@Autowired
	PeopleRepository repo;
	
	public People consultPeopleById(Long idpeople) {
		Optional<People> obj = repo.findById(idpeople);
		return obj.orElseThrow(() -> new EntityNotFoundException("People not found"));
	}
	
	public People savePeople(People people) {
		return repo.save(people);
	}

}
