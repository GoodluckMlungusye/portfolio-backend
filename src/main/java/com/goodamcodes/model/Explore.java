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
public class Explore {

    @Id
    @SequenceGenerator(
            name = "explore_sequence",
            sequenceName = "explore_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "explore_sequence"
    )
    private Long id;
    private int counts;
    private String description;
    private String image;
}
