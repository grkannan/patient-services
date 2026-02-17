package org.gatex.patientservices.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bill_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double amount;

    // 🔥 Proper relationship
    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;
}