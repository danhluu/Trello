package com.blameo.trello.repository;

import com.blameo.trello.model.Board;
import com.blameo.trello.model.BoardUser;
import com.blameo.trello.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
    List<BoardUser> findByUser(User user);
    List<BoardUser> findByBoard(Board board);

    @Query("SELECT ub from BoardUser ub where ub.user.id = :userId and ub.board.boardId = :boardId")
    Optional<BoardUser> findByBoardAndUser(@Param("userId") Long userId, @Param("boardId") Long boardId);
}
