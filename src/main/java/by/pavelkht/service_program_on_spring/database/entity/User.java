package by.pavelkht.service_program_on_spring.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_list", schema = "public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String email;
    private String phone;
    private String login;

    @Column(name = "pass")
    private String password;

    private String image;
}
