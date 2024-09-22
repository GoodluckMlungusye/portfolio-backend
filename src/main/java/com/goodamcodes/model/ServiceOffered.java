package com.goodamcodes.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class ServiceOffered {
    @Id
    @SequenceGenerator(
            name = "serviceOffered_sequence",
            sequenceName = "serviceOffered_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "serviceOffered_sequence"
    )
    private Long id;
    private String name;
    private String description;
    private String image;
}
