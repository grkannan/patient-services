package org.gatex.patientservices.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long visitId;

    @Column(nullable = false)
    private Double consultationFee = 0.0;

    @Column(nullable = false)
    private Double additionalCharges = 0.0;

    @Column(nullable = false)
    private Double totalAmount = 0.0;

    @Column(nullable = false)
    private Boolean paid = false;

    private LocalDateTime paidAt;

    private String paymentMethod;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // 🔥 Relationship with BillItem
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> items = new ArrayList<>();
    private String invoiceNumber;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        calculateTotal();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        calculateTotal();
    }

    public void calculateTotal() {
        double itemsTotal = items.stream()
                .mapToDouble(item -> item.getAmount() != null ? item.getAmount() : 0.0)
                .sum();

        this.totalAmount =
                (consultationFee != null ? consultationFee : 0.0)
                        + (additionalCharges != null ? additionalCharges : 0.0)
                        + itemsTotal;
    }
}