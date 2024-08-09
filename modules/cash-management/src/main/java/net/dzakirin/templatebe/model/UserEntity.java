package net.dzakirin.templatebe.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"user\"") // Quotes are used because "user" is a reserved word in PostgreSQL
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(
            read = "address::text",
            write = "?::jsonb"
    )
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AccountEntity> accounts;
}
