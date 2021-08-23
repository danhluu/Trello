package com.blameo.trello.repository;

import com.blameo.trello.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT count(task) FROM Task task where task.workList.workListId = :workListId")
    Long countDisplayOrder(Long workListId);

    @Query("SELECT t from Task t where t.workList.workListId = :workListId")
    List<Task> getAllByWorkListId(@Param("workListId") Long workListId);

    @Query("SELECT t from Task t where t.disPlayOrder = :displayOrder and t.workList.workListId= :workListId")
    Task getByDisPlayOrder(@Param("displayOrder") Long displayOrder, @Param("workListId") Long workListId);
}
