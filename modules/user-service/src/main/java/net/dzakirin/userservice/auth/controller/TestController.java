package net.dzakirin.userservice.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String getUser() {
        return "GET:: user controller";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String getModerator() {
        return "GET:: moderator controller";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAdmin() {
        return "GET:: admin controller";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String post() {
        return "POST:: admin controller";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String put() {
        return "PUT:: admin controller";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete() {
        return "DELETE:: admin controller";
    }
}
