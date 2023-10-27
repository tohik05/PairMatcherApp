package com.andersen.orange.user.model;

import com.andersen.orange.pair.model.Pair;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "team")
    @Enumerated(value = EnumType.STRING)
    private Teams team;
    @Column(name = "is_Deleted")
    private boolean isDeleted;
    @OneToMany(mappedBy = "user")
    private List<Pair> pairs;
}
