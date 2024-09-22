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
public class Education {
    @Id
    @SequenceGenerator(
            name = "education_sequence",
            sequenceName = "education_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "education_sequence"
    )
    private Long id;
    private String duration;
    private String institute;
    private String programme;
    private String description;

}
