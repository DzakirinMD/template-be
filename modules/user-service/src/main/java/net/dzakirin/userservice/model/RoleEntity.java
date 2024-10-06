package net.dzakirin.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import net.dzakirin.userservice.constant.RoleName;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleName roleName;

    @ManyToMany(mappedBy = "roleEntities")
    private List<UserEntity> userEntities;
}
