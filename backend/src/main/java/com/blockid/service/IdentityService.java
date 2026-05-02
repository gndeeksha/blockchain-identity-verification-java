//package com.blockid.service;
//
//import com.blockid.blockchain.HashUtil;
//import com.blockid.model.IdentityRecord;
//import com.blockid.repository.IdentityRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class IdentityService {
//
//    private final IdentityRepository identityRepository;
//    private final BlockchainService blockchainService;
//
//    public IdentityService(IdentityRepository identityRepository,
//                           BlockchainService blockchainService) {
//        this.identityRepository = identityRepository;
//        this.blockchainService = blockchainService;
//    }
//
//    //  SUBMIT IDENTITY
//    public String submitIdentity(String username, String data) {
//
//        String hash = HashUtil.sha256(data);
//
//        IdentityRecord record = new IdentityRecord();
//        record.setUsername(username);
//        record.setData(data);
//        record.setHash(hash);
//        record.setStatus("PENDING");
//
//        identityRepository.save(record);
//
//        // Add to blockchain (DB-based)
//        blockchainService.addBlock(hash);
//
//        return "Identity submitted";
//    }
//
//    //  VERIFY IDENTITY
//    public String verifyIdentity(Long id, String newData) {
//
//        IdentityRecord record = identityRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Record not found"));
//
//        String newHash = HashUtil.sha256(newData);
//
//        if (newHash.equals(record.getHash())) {
//            return "VALID (Data not tampered)";
//        } else {
//            return "INVALID (Data has been tampered)";
//        }
//    }
//
//    //  APPROVE IDENTITY (ADMIN)
//    public String approveIdentity(Long id) {
//
//        IdentityRecord record = identityRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Record not found"));
//
//        record.setStatus("APPROVED");
//        identityRepository.save(record);
//
//        return "Identity approved";
//    }
//}

package com.blockid.service;

import com.blockid.blockchain.HashUtil;
import com.blockid.model.*;
import com.blockid.repository.*;
import com.blockid.util.EncryptionUtil;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IdentityService {
    private final IdentityRepository identityRepository;
    private final VerificationLogRepository logRepository;

    public IdentityService(IdentityRepository identityRepository, VerificationLogRepository logRepository) {
        this.identityRepository = identityRepository;
        this.logRepository = logRepository;
    }

    public String submitIdentity(String username, String data) {
        try {
            String currentHash = HashUtil.sha256(data);
            List<IdentityRecord> all = identityRepository.findAll();
            String prevHash = all.isEmpty() ? "0" : all.get(all.size() - 1).getHash();

            IdentityRecord record = new IdentityRecord();
            record.setUsername(username);
            record.setData(EncryptionUtil.encrypt(data)); // AES Encryption
            record.setHash(currentHash);
            record.setPreviousHash(prevHash);
            record.setStatus("PENDING");
            identityRepository.save(record);
            return "Identity stored and hashed.";
        } catch (Exception e) { throw new RuntimeException("Encryption error"); }
    }

    public String approveIdentity(Long id, String adminName) {
        IdentityRecord record = identityRepository.findById(id).orElseThrow();
        record.setStatus("APPROVED");
        record.setSignature(HashUtil.sha256(record.getHash() + adminName)); // Digital Signature
        identityRepository.save(record);
        return "Approved by " + adminName;
    }

    public String verifyIdentity(Long id, String inputData) {
        IdentityRecord record = identityRepository.findById(id).orElseThrow();
        String result = HashUtil.sha256(inputData).equals(record.getHash()) ? "VALID" : "INVALID";

        VerificationLog log = new VerificationLog();
        log.setRecordId(id); log.setResult(result); log.setTimestamp(LocalDateTime.now());
        logRepository.save(log); // Audit Trail
        return result;
    }

    public List<IdentityRecord> getAllIdentities() {
        return identityRepository.findAll().stream().map(this::decrypt).collect(Collectors.toList());
    }

    public List<IdentityRecord> getMyIdentities(String user) {
        return identityRepository.findByUsername(user).stream().map(this::decrypt).collect(Collectors.toList());
    }

    public List<VerificationLog> getAuditLogs() { return logRepository.findAll(); }

    private IdentityRecord decrypt(IdentityRecord r) {
        try { r.setData(EncryptionUtil.decrypt(r.getData())); return r; } catch (Exception e) { return r; }
    }
}