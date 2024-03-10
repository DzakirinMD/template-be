package net.dzakirin.templatebe.service;

import net.dzakirin.templatebe.dto.EmployeeDto;
import net.dzakirin.templatebe.exception.ResourceNotFoundException;
import net.dzakirin.templatebe.mapper.EmployeeManualMapper;
import net.dzakirin.templatebe.mapper.EmployeeMapper;
import net.dzakirin.templatebe.repo.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private static final String EMPLOYEE_NOT_FOUND = "Employee id not found : ";

    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepo employeeRepo, EmployeeMapper employeeMapper) {
        this.employeeRepo = employeeRepo;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        var employeeEntity = employeeMapper.toEmployeeEntity(employeeDto);
        employeeRepo.save(employeeEntity);

        return employeeMapper.toEmployeeDto(employeeEntity);
    }

    public EmployeeDto findById(UUID employeeId) {
        var employeeEntity = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND + employeeId));

        return EmployeeManualMapper.toEmployeeDto(employeeEntity);
    }

    public List<EmployeeDto> findAll() {
        var employeeEntities = employeeRepo.findAll();

        return employeeEntities.stream()
                .map(EmployeeManualMapper::toEmployeeDto) // Use the manual mapper here
                .toList();
    }

    public List<EmployeeDto> findByAddressPostCode(String postcode) {
        var employees = employeeRepo.findByAddressPostCode(postcode); // You need to implement this method in your repository
        return employees.stream()
                .map(EmployeeManualMapper::toEmployeeDto)
                .toList();
    }



    public EmployeeDto updateEmployee(UUID employeeId, EmployeeDto employeeDto) {
        var employeeEntity = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND + employeeId));

        employeeEntity.setFirstName(employeeDto.getFirstName());
        employeeEntity.setLastName(employeeDto.getLastName());
        employeeEntity.setEmail(employeeEntity.getEmail());
        employeeRepo.save(employeeEntity);

        return employeeMapper.toEmployeeDto(employeeEntity);
    }

    public void deleteEmployee(UUID employeeId) {
        var employeeEntity = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND + employeeId));

        employeeRepo.delete(employeeEntity);
    }
}
