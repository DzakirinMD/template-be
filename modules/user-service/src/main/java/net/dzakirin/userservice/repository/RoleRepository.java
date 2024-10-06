package net.dzakirin.userservice.repository;

import net.dzakirin.userservice.constant.RoleName;
import net.dzakirin.userservice.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByRoleName(RoleName roleName);
}
