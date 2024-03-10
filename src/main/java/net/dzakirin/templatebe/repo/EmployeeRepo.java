package net.dzakirin.templatebe.repo;

import net.dzakirin.templatebe.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, UUID> {

    @Query(value = "SELECT * FROM employee e WHERE e.address ->> 'postcode' = :postcode", nativeQuery = true)
    List<EmployeeEntity> findByAddressPostCode(@Param("postcode") String postcode);
}
