package by.pavelkht.service_program_on_spring.database.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "part_list", schema = "public")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    private int article;
    private String name;

    @Column(name = "price")
    private BigDecimal price;
}
