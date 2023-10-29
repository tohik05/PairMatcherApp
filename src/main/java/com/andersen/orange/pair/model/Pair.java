package com.andersen.orange.pair.model;

import com.andersen.orange.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "opponent_id")
    private User opponent;
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    public Pair(Long opponentId, Date date, User user) {
//        this.opponentId = opponentId;
//        this.date = date;
//        this.user = user;
//    }

    @Override
    public String toString() {
        return "Pair{" +
                "user=" + user.getName() + " " + user.getLastname() +
                ", opponent=" + opponent.getName() + " " + opponent.getLastname() +
                ", date=" + date +'}';
    }
}