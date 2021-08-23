package com.blameo.trello.repository;

import com.blameo.trello.model.Board;
import com.blameo.trello.model.WorkList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkListRepository extends JpaRepository<WorkList, Long> {
    List<WorkList> findByBoard(Board board);

    @Query("SELECT count(wl) FROM WorkList wl where wl.board.boardId = :boardId")
    Long countDisplayOrder(Long boardId);


    @Query("SELECT wl from WorkList wl " +
            "where wl.displayOrder >= :addIndex " +
            "and wl.displayOrder <= :removeIndex " +
            "and wl.board.boardId = :boardId")
    List<WorkList> getListWorkListByDisplayOrder(@Param("addIndex") Long addIndex,
                                                 @Param("removeIndex") Long removeIndex,
                                                 @Param("boardId")  Long boardId);

    @Query("SELECT wl from WorkList wl " +
            "where wl.displayOrder = :displayOrder " +
            "and wl.board.boardId = :boardId")
    Optional<WorkList> getWL(@Param("displayOrder") Long displayOrder,@Param("boardId") Long boardId);
}
