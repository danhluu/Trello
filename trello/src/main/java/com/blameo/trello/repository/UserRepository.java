package com.blameo.trello.repository;


import com.blameo.trello.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select us from User us where us.email = :name")
    User findByUsername(String name);

    @Query("select count (us) from User us where us.email = :email")
    int checkExists(String email);

//    @Query("select us from User us where us.fullName like %:keyword% or us.email like %:keyword%")
    @Query("select us from User us " +
            "left join BoardUser bu on us.id = bu.user.id and bu.board.boardId = :boardId " +
            "where (bu.user.id is null) and (us.fullName like %:keyword% or us.email like %:keyword%)")
    Set<User> getUserByKeyword(@Param("keyword") String keyword, @Param("boardId") Long boardId);

//    Set<User> getByKeyworkInTask(@Param("keyword") String keyword, @Param("boardId") Long boardId);

}