//package com.blockid.controller;
//
//import com.blockid.dto.IdentityRequest;
//import com.blockid.dto.VerifyRequest;
//import com.blockid.service.IdentityService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/identity")
//public class IdentityController {
//
//    private final IdentityService identityService;
//
//    public IdentityController(IdentityService identityService) {
//        this.identityService = identityService;
//    }
//
//    // SUBMIT
//    @PostMapping("/submit")
//    public String submit(@RequestBody IdentityRequest request) {
//        return identityService.submitIdentity(
//                request.getUsername(),
//                request.getData()
//        );
//    }
//
//    //  VERIFY
//    @PostMapping("/verify")
//    public String verify(@RequestBody VerifyRequest request) {
//        return identityService.verifyIdentity(
//                request.getId(),
//                request.getData()
//        );
//    }
//
//    //  APPROVE (ADMIN)
//    @PostMapping("/approve")
//    public String approve(@RequestParam Long id) {
//        return identityService.approveIdentity(id);
//    }
//}

package com.blockid.controller;

import com.blockid.dto.IdentityRequest;
import com.blockid.dto.VerifyRequest;
import com.blockid.model.IdentityRecord;
import com.blockid.service.IdentityService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/identity")
public class IdentityController {

    private final IdentityService identityService;

    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    // ✅ SUBMIT: Username is taken from the token (Authentication object)
    @PostMapping("/submit")
    public String submit(@RequestBody IdentityRequest request, Authentication authentication) {
        String loggedInUsername = authentication.getName();
        return identityService.submitIdentity(
                loggedInUsername,
                request.getData()
        );
    }

    // ✅ GET ALL: Used by Admin
    @GetMapping("/all")
    public List<IdentityRecord> getAll() {
        return identityService.getAllIdentities();
    }

    // ✅ VERIFY: Public or User check
    @PostMapping("/verify")
    public String verify(@RequestBody VerifyRequest request) {
        return identityService.verifyIdentity(
                request.getId(),
                request.getData()
        );
    }

    // ✅ APPROVE: Path Variable for Admin
    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        return identityService.approveIdentity(id);
    }
}