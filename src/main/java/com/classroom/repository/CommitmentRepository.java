package com.classroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.classroom.entity.Commitment;

@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Long> {
	@Query("select cp from Commitment cp, Contact ct where cp.contact = ct.id and ct.name = ?1")
	List<Commitment> consultCommitmentByNameContact(String name);
}
