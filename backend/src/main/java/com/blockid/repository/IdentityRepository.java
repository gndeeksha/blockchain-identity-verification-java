//package com.blockid.repository;
//
//import com.blockid.model.IdentityRecord;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface IdentityRepository extends JpaRepository<IdentityRecord, Long> {
//}

package com.blockid.repository;

import com.blockid.model.IdentityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // ✅ Add this import

public interface IdentityRepository extends JpaRepository<IdentityRecord, Long> {

    // ✅ Add this method: It tells Spring Data JPA to create a
    // "SELECT * FROM identity_record WHERE username = ?" query automatically.
    List<IdentityRecord> findByUsername(String username);
}