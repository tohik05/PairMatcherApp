package com.andersen.orange.mark.repository;

import com.andersen.orange.mark.model.IndividualMark;
import com.andersen.orange.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndividualMarkRepository extends JpaRepository<IndividualMark, Long> {
    List<IndividualMark> findByUser(User user);
}
