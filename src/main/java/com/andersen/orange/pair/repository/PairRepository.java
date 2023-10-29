package com.andersen.orange.pair.repository;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PairRepository extends JpaRepository<Pair, Long> {
    @Query("SELECT p FROM Pair p JOIN p.users u WHERE u = :user")
    List<Pair> findPairsByUser(@Param("user") User user);
}
