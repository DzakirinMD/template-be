package net.dzakirin.controller;

import net.dzakirin.common.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<BaseResponse<String>> publicAccess() {
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .message("Public Content.")
                .data("Anyone can see this (if authenticated)")
                .build());
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> customerAccess() {
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .message("Customer Content.")
                .data("Seen by CUSTOMER or ADMIN")
                .build());
    }

    @GetMapping("/seller")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> sellerAccess() {
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .message("Seller Board.")
                .data("Seen only by SELLER or ADMIN")
                .build());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> adminAccess() {
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .message("Admin Board.")
                .data("Seen only by ADMIN")
                .build());
    }
}