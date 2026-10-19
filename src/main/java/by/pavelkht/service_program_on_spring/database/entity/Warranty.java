package by.pavelkht.service_program_on_spring.database.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "order_warranty", schema = "public")
public class Warranty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    private Integer serialNumber;
    private String oilLevel;
    private String oilCondition;
    private String reason;
    private String descriptionDefect;
    private String linkPhoto;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
