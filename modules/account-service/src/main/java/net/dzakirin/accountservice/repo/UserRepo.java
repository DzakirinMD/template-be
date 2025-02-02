package net.dzakirin.accountservice.repo;

import net.dzakirin.accountservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * FROM user e WHERE e.address ->> 'postcode' = :postcode", nativeQuery = true)
    List<UserEntity> findByAddressPostCode(@Param("postcode") String postcode);
}
