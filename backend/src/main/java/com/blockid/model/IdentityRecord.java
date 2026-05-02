package com.blockid.model;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class IdentityRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String data;          // Encrypted in DB
    private String hash;          // SHA-256
    private String previousHash;  // Step 4: Chain link
    private String signature;     // Digital Signature
    private String status;        // PENDING/APPROVED
}