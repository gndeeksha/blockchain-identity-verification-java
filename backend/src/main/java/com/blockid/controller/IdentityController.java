package com.blockid.controller;

import com.blockid.dto.IdentityRequest;
import com.blockid.dto.VerifyRequest;
import com.blockid.service.IdentityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/identity")
public class IdentityController {

    private final IdentityService identityService;

    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    // SUBMIT
    @PostMapping("/submit")
    public String submit(@RequestBody IdentityRequest request) {
        return identityService.submitIdentity(
                request.getUsername(),
                request.getData()
        );
    }

    //  VERIFY
    @PostMapping("/verify")
    public String verify(@RequestBody VerifyRequest request) {
        return identityService.verifyIdentity(
                request.getId(),
                request.getData()
        );
    }

    //  APPROVE (ADMIN)
    @PostMapping("/approve")
    public String approve(@RequestParam Long id) {
        return identityService.approveIdentity(id);
    }
}