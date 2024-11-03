package com.goodamcodes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class SubSkill {
    @Id
    @SequenceGenerator(
            name = "sub_skill_sequence",
            sequenceName = "sub_skill_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sub_skill_sequence"
    )
    private Long id;
    private String name;
    private int percentageLevel;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    @JsonBackReference
    private Skill skill;
}
