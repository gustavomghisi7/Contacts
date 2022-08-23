package com.classroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classroom.entity.People;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
	
}
