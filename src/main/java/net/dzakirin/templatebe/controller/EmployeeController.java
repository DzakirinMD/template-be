package net.dzakirin.templatebe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import net.dzakirin.templatebe.dto.EmployeeDto;
import net.dzakirin.templatebe.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @Operation(
            summary = "Create Employee",
            description = "An API to create employee from supplied EmployeeDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class),
                            examples = @ExampleObject(
                                    name = "ExampleEmployee",
                                    summary = "Example Employee",
                                    description = "A complete example of an employee data",
                                    value = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\" }"
                            )
                    )
            )
    )
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody EmployeeDto employeeDto
    ) {
        var savedEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping("/{employeeId}")
    @Operation(
            summary = "Find Employee by ID",
            description = "An API to find an employee by their ID"
    )
    public ResponseEntity<EmployeeDto> findById(@PathVariable UUID employeeId) {
        var employeeDto = employeeService.findById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping
    @Operation(
            summary = "Find all Employees",
            description = "An API to find all employee "
    )
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PutMapping("/{employeeId}")
    @Operation(
            summary = "Update Employee",
            description = "An API to update an employee with the given ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class),
                            examples = @ExampleObject(
                                    name = "UpdatedEmployee",
                                    summary = "Updated Employee",
                                    description = "An example of updated employee data",
                                    value = "{ \"firstName\": \"Jane\", \"lastName\": \"Doe\", \"email\": \"janedoe@example.com\" }"
                            )
                    )
            )
    )
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable UUID employeeId,
            @Valid @RequestBody EmployeeDto employeeDto
    ) {
        var updatedEmployee = employeeService.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    @Operation(
            summary = "Delete Employee",
            description = "An API to delete an employee with the given ID"
    )
    public ResponseEntity<String> deleteEmployee(@PathVariable UUID employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully!. id: " + employeeId);
    }
}
