package net.dzakirin.templatebe.repo;

import net.dzakirin.templatebe.model.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity, UUID> {

    @Query(value = "SELECT DISTINCT d.*, e.address ->> 'date' as date FROM department d " +
            "LEFT JOIN employee e ON d.id = e.department_id AND e.address ->> 'postcode' = :postcode " +
            "ORDER BY e.address ->> 'date' DESC", nativeQuery = true)
    List<DepartmentEntity> findDepartmentsByEmployeePostcodeOrderByDate(@Param("postcode") String postcode);
}
