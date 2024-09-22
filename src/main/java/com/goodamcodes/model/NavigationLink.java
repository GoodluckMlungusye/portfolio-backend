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
public class NavigationLink {

    @Id
    @SequenceGenerator(
            name = "navigationLink_sequence",
            sequenceName = "navigationLink_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "navigationLink_sequence"
    )
    private Long id;
    private String name;
}
