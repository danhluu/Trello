package com.blameo.trello.repository;

import com.blameo.trello.model.Task;
import com.blameo.trello.model.TaskUser;
import com.blameo.trello.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskUserRepository extends JpaRepository<TaskUser, Long> {

    Optional<TaskUser> findByUserAndTask(User user, Task task);

    List<TaskUser> findByTask(Task task);
}
