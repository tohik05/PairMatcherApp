package com.andersen.orange.user.repository;

import com.andersen.orange.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :userId")
    void softDelete(@Param("userId") Long userId);

    List<User> findByIsDeletedFalse();

    Optional<User> findByIdAndIsDeletedFalse(Long id);

    Optional<User> findByNameAndLastname(String name, String lastname);



}
