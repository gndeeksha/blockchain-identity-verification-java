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
import com.blockid.model.IdentityRecord;
import com.blockid.repository.IdentityRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IdentityService {

    private final IdentityRepository identityRepository;
    private final BlockchainService blockchainService;

    public IdentityService(IdentityRepository identityRepository, BlockchainService blockchainService) {
        this.identityRepository = identityRepository;
        this.blockchainService = blockchainService;
    }

    public List<IdentityRecord> getAllIdentities() {
        return identityRepository.findAll();
    }

    public String submitIdentity(String username, String data) {
        String hash = HashUtil.sha256(data);
        IdentityRecord record = new IdentityRecord();
        record.setUsername(username);
        record.setData(data);
        record.setHash(hash);
        record.setStatus("PENDING");
        identityRepository.save(record);
        blockchainService.addBlock(hash);
        return "Identity submitted";
    }

    public String approveIdentity(Long id) {
        IdentityRecord record = identityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        record.setStatus("APPROVED");
        identityRepository.save(record);
        return "Identity approved";
    }

    public String verifyIdentity(Long id, String newData) {
        IdentityRecord record = identityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        String newHash = HashUtil.sha256(newData);
        return newHash.equals(record.getHash()) ? "VALID" : "INVALID";
    }
}