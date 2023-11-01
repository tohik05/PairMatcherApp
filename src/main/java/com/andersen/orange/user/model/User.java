package com.andersen.orange.user.model;

import com.andersen.orange.mark.model.IndividualMark;
import com.andersen.orange.mark.model.Mark;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.team.model.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
    @ManyToMany
    @JoinTable(name = "user_pair",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pair_id"))
    private List<Pair> pairs;
    @OneToMany(mappedBy = "user")
    private List<Mark> marks;
    @OneToMany(mappedBy = "user")
    private List<IndividualMark> individualMarks;
}
