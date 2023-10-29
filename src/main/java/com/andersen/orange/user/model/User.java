package com.andersen.orange.user.model;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.team.model.Team;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @Column(name = "is_Deleted")
    private boolean isDeleted;
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Pair> pairs;
}
