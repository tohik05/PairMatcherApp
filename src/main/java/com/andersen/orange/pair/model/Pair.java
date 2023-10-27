package com.andersen.orange.pair.model;

import com.andersen.orange.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pairs")
public class Pair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "opponent_id")
    private Long opponentId;
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}