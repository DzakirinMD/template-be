### identity-service structure
```
identity-service/
├── config/
│   ├── SecurityConfig.java      // Define PasswordEncoder, Public/Private Keys
│   └── JwtProvider.java         // Logic to generate Token with Roles
├── controller/
│   ├── AuthController.java      // Endpoints: /login, /register, /refresh
│   └── AdminUserController.java // Endpoints: /admin/ban-user, /admin/grant-role
├── entity/
│   ├── UserCredential.java      // Table: id, email, password, enabled
│   ├── Role.java                // Table: id, name (ROLE_CUSTOMER, ROLE_SELLER)
│   └── RefreshToken.java        // Table: token, expiry (for security)
├── repository/
│   └── ...
└── service/
    └── AuthService.java         // Logic: Check Pass, Load Roles, Sign JWT
```