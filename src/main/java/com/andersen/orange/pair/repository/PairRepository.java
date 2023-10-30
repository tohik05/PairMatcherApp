package com.andersen.orange.pair.repository;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PairRepository extends JpaRepository<Pair, Long> {
    @Query("SELECT p FROM Pair p JOIN p.users u WHERE u = :user")
    List<Pair> findPairsByUser(@Param("user") User user);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_pair SET mark = ?3 WHERE user_id = ?1 AND pair_id = ?2", nativeQuery = true)
//    @Query(value = "INSERT INTO user_pair (user_id, pair_id, mark) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertUserPairMark(Long userId, Long pairId, Double mark);
}
