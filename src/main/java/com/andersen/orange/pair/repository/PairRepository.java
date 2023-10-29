package com.andersen.orange.pair.repository;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PairRepository extends JpaRepository<Pair, Long> {
    List<Pair> findPairsByUserId(Long id);

}
