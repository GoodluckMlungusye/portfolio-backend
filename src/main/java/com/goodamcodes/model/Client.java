package com.goodamcodes.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class Client {
    @Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_sequence"
    )
    private Long id;
    private String name;
    @Column(name = "phoneNumber", nullable = false, unique = true)
    private String phoneNumber;
    @Email
    private String email;
    private String subject;
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", updatable = false)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
