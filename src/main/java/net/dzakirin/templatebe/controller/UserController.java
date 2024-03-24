package net.dzakirin.templatebe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import net.dzakirin.templatebe.dto.UserDto;
import net.dzakirin.templatebe.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Create User",
            description = "An API to create user from supplied UserDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class),
                            examples = @ExampleObject(
                                    name = "ExampleUser",
                                    summary = "Example User",
                                    description = "A complete example of user data including address",
                                    value = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\", \"address\": \"{\\\"postcode\\\": \\\"4567\\\", \\\"city\\\": \\\"Sometown\\\", \\\"date\\\": \\\"2021-10-10\\\"}\" }"
                            )
                    )
            )
    )
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody UserDto userDto
    ) {
        var savedUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Find User by ID",
            description = "An API to find an user by their ID"
    )
    public ResponseEntity<UserDto> findById(@PathVariable UUID userId) {
        var userDto = userService.findById(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    @Operation(
            summary = "Find all Users",
            description = "An API to find all user "
    )
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/address/{postcode}")
    @Operation(
            summary = "Find Users by Address Postcode",
            description = "An API to find all users by a given address postcode"
    )
    public ResponseEntity<List<UserDto>> findByAddressPostCode(@PathVariable String postcode) {
        return ResponseEntity.ok(userService.findByAddressPostCode(postcode));
    }

    @PutMapping("/{userId}")
    @Operation(
            summary = "Update User",
            description = "An API to update a user with the given ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class),
                            examples = @ExampleObject(
                                    name = "UpdatedUser",
                                    summary = "Updated User",
                                    description = "An example of updated user data including updated address",
                                    value = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\", \"address\": \"{\\\"postcode\\\": \\\"4567\\\", \\\"city\\\": \\\"Sometown\\\", \\\"date\\\": \\\"2021-10-10\\\"}\" }"
                            )
                    )
            )
    )
    public ResponseEntity<UserDto> updateUser(
            @PathVariable UUID userId,
            @Valid @RequestBody UserDto userDto
    ) {
        var updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(
            summary = "Delete User",
            description = "An API to delete an user with the given ID"
    )
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully!. id: " + userId);
    }
}
