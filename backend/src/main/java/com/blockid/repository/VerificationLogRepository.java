package com.blockid.repository;
import com.blockid.model.VerificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VerificationLogRepository extends JpaRepository<VerificationLog, Long> {}