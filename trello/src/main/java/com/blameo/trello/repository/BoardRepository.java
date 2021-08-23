package com.blameo.trello.repository;

import com.blameo.trello.model.Board;
import com.blameo.trello.model.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByBoardUsers(BoardUser boardUser);
}
