package com.blameo.trello.repository;

import com.blameo.trello.model.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeadlineRepository extends JpaRepository<Deadline, Long> {

    Deadline getByTaskId(Long taskId);
}
