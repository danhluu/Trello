package com.blameo.trello.service;

import com.blameo.trello.model.Task;
import com.blameo.trello.model.TaskUser;
import com.blameo.trello.model.User;
import com.blameo.trello.model.WorkList;
import com.blameo.trello.model.dto.SearchUserDto;
import com.blameo.trello.repository.TaskRepository;
import com.blameo.trello.repository.TaskUserRepository;
import com.blameo.trello.repository.UserRepository;
import com.blameo.trello.repository.WorkListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskUserRepository taskUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkListRepository workListRepository;

    @Autowired
    BoardService boardService;

    public void createTask(Long workListId, String title, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Task task = new Task();
        task.setDisPlayOrder(taskRepository.countDisplayOrder(workListId));
        task.setTitle(title);
        WorkList workList = workListRepository.getById(workListId);
        task.setWorkList(workList);
        task.setCreateBy(user.getId());
        taskRepository.save(task);
    }

    public String addPersonToTask(Long taskId, Long userId) {
        if (taskRepository.findById(taskId).isPresent() && userRepository.findById(userId).isPresent()) {
            Task task = taskRepository.getById(taskId);
            User user = userRepository.getById(userId);
            TaskUser taskUser = new TaskUser();
            taskUser.setUser(user);
            taskUser.setTask(task);
            if (!taskUserRepository.findByUserAndTask(user, task).isPresent()) {
                taskUserRepository.save(taskUser);
                return "Add person to task success";
            }
            return "User is exist in task";
        }
        return "Task or user is not exist";
    }


    public String deletePersonToTask(Long taskId, Long userId) {
        if (taskRepository.findById(taskId).isPresent() && userRepository.findById(userId).isPresent()) {
            Task task = taskRepository.getById(taskId);
            User user = userRepository.getById(userId);
            TaskUser taskUser = new TaskUser();
            taskUser.setUser(user);
            taskUser.setTask(task);
            if (taskUserRepository.findByUserAndTask(user, task).isPresent()) {
                TaskUser tu = taskUserRepository.findByUserAndTask(user, task).get();
                taskUserRepository.deleteById(tu.getId());
                return "Delete person to task success";
            } else return "User is not exist in task";
        }
        return "Task or user is not exist";
    }

    public void updateDisplayOrder(Long removeId, Long removedIndex, Long addId, Long addedIndex) {
        List<Task> listRemove = taskRepository.getAllByWorkListId(removeId);
        List<Task> listAdd = taskRepository.getAllByWorkListId(addId);
        Task taskRemove = taskRepository.getByDisPlayOrder(removedIndex, removeId);
        WorkList workList = workListRepository.getById(addId);
        taskRemove.setWorkList(workList);

        listAdd.forEach(x -> {
            if (x.getDisPlayOrder() >= addedIndex) {
                x.setDisPlayOrder(x.getDisPlayOrder() + 1);
                taskRepository.save(x);
            }
        });
        taskRemove.setWorkList(workList);
        taskRemove.setDisPlayOrder(addedIndex);
        taskRepository.save(taskRemove);

        listRemove.forEach(x -> {
            if (x.getDisPlayOrder() > removedIndex) {
                x.setDisPlayOrder(x.getDisPlayOrder() - 1);
                taskRepository.save(x);
            }
        });
    }

    public List<SearchUserDto> getAllPersonInTask(Long taskId) {
        List<SearchUserDto> userDtos = new ArrayList<>();
        List<TaskUser> taskUsers = taskUserRepository.findByTask(taskRepository.getById(taskId));

        taskUsers.forEach(x -> {
            SearchUserDto userDto = new SearchUserDto();
            BeanUtils.copyProperties(x.getUser(), userDto);
            userDtos.add(userDto);
        });
        return userDtos;
    }

    public List<SearchUserDto> getAllPersonToAddToTask(Long taskId) {
        List<SearchUserDto> userDtos = new ArrayList<>();
        Task task = taskRepository.getById(taskId);
        List<SearchUserDto> listInBoard = boardService.getUserInBoard(task.getWorkList().getBoard().getBoardId());
        List<SearchUserDto> listInTask = getAllPersonInTask(taskId);

        for (int i = 0; i < listInBoard.size(); i++) {
            boolean check = true;
            for (int j = 0; j < listInTask.size(); j++) {
                if (listInBoard.get(i).getId() == listInTask.get(j).getId()) {
                    check = false;
                    break;
                }
            }
            if (check) {
                userDtos.add(listInBoard.get(i));
            }
        }
        return userDtos;
    }
}
