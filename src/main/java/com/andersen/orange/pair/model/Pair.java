package com.andersen.orange.pair.model;

import com.andersen.orange.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "pairs")
public class Pair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
    @JsonIgnore
    @ManyToMany(mappedBy = "pairs")
    private List<User> users;

    @Override
    public String toString() {
        return "Pair{" +
                "user=" + users.get(0).getName() + " " + users.get(0).getLastname() +
                ", opponent=" + users.get(1).getName() + " " + users.get(1).getLastname() +
                ", date=" + date + '}';
    }
}