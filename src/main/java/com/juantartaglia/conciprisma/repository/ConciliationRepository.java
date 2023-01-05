package com.juantartaglia.conciprisma.repository;

import com.juantartaglia.conciprisma.model.ConciliationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConciliationRepository extends JpaRepository<ConciliationEntity, Long> {
}
