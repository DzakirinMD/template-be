package net.dzakirin.templatebe.service;

import net.dzakirin.templatebe.dto.DepartmentDto;
import net.dzakirin.templatebe.mapper.DepartmentManualMapper;
import net.dzakirin.templatebe.model.DepartmentEntity;
import net.dzakirin.templatebe.repo.DepartmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepo departmentRepo;

    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    public List<DepartmentDto> findDepartmentsByEmployeePostcodeOrderByDate(String postcode) {
        List<DepartmentEntity> departments = departmentRepo.findDepartmentsByEmployeePostcodeOrderByDate(postcode);
        return departments.stream()
                .map(DepartmentManualMapper::toDepartmentDto)
                .toList();
    }

}
