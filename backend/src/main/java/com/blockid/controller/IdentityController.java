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
import com.blockid.dto.*;
import com.blockid.model.*;
import com.blockid.service.IdentityService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/identity")
public class IdentityController {
    private final IdentityService service;
    public IdentityController(IdentityService service) { this.service = service; }

    @PostMapping("/submit")
    public String submit(@RequestBody IdentityRequest req, Authentication auth) {
        return service.submitIdentity(auth.getName(), req.getData());
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id, Authentication auth) {
        return service.approveIdentity(id, auth.getName());
    }

    @PostMapping("/verify")
    public String verify(@RequestBody VerifyRequest req) {
        return service.verifyIdentity(req.getId(), req.getData());
    }

    @GetMapping("/all")
    public List<IdentityRecord> getAll() { return service.getAllIdentities(); }

    @GetMapping("/my")
    public List<IdentityRecord> getMy(Authentication auth) { return service.getMyIdentities(auth.getName()); }

    @GetMapping("/logs")
    public List<VerificationLog> getLogs() { return service.getAuditLogs(); }
}