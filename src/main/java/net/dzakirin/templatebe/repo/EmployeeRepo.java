package net.dzakirin.templatebe.repo;

import net.dzakirin.templatebe.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, UUID> {
}
