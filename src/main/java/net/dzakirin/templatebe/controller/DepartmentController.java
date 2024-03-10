package net.dzakirin.templatebe.controller;

import net.dzakirin.templatebe.dto.DepartmentDto;
import net.dzakirin.templatebe.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/by-employee-postcode/{postcode}")
    public ResponseEntity<List<DepartmentDto>> findDepartmentsByEmployeePostcodeOrderByDate(@PathVariable String postcode) {
        List<DepartmentDto> departments = departmentService.findDepartmentsByEmployeePostcodeOrderByDate(postcode);
        return ResponseEntity.ok(departments);
    }
}
