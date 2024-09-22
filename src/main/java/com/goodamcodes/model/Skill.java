package com.goodamcodes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class Skill {
    @Id
    @SequenceGenerator(
            name = "skill_sequence",
            sequenceName = "skill_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "skill_sequence"
    )
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skill")
    @JsonManagedReference
    private List<SubSkill> subSkillList;
}
