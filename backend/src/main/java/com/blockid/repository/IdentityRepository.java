package com.blockid.repository;

import com.blockid.model.IdentityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityRepository extends JpaRepository<IdentityRecord, Long> {
}