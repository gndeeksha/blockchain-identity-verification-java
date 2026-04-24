package com.blockid.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdentityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String data;      // actual identity data

    private String hash;      // SHA-256 hash

    private String status;    // PENDING / APPROVED
}