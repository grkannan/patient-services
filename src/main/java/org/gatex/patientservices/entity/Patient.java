package org.gatex.patientservices.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private Integer age;
    private String gender;
    private String phone;
    private String address;
    private String bloodGroup;

    private Boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}