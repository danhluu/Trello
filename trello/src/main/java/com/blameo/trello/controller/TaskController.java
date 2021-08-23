package com.blameo.trello.controller;

import com.blameo.trello.model.Task;
import com.blameo.trello.model.User;
import com.blameo.trello.model.dto.SearchUserDto;
import com.blameo.trello.model.request.TaskRequest;
import com.blameo.trello.repository.TaskRepository;
import com.blameo.trello.repository.WorkListRepository;
import com.blameo.trello.service.BoardService;
import com.blameo.trello.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/task")
public class TaskController {

    @Autowired
    WorkListRepository workListRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest, Authentication authentication) {
        if (workListRepository.findById(taskRequest.getWorkListId()).isPresent()) {
            taskService.createTask(taskRequest.getWorkListId(), taskRequest.getTitle(), authentication);
            return ResponseEntity.ok("Create success");
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/add-person-to-task")
    public ResponseEntity<?> addPersonToTask(@RequestBody TaskRequest taskRequest) {
        String message = taskService.addPersonToTask(taskRequest.getTaskId(), taskRequest.getUserId());
        return ResponseEntity.ok(message);
    }


    @DeleteMapping("/delete-person-to-task")
    public ResponseEntity<?> deletePersonToTask(@RequestParam("userId") Long userId, @RequestParam("taskId") Long taskId) {
        String message = taskService.deletePersonToTask(taskId, userId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update-display-order")
    public ResponseEntity<?> updateDisplayOrder(@RequestBody TaskRequest taskRequest) {
        taskService.updateDisplayOrder(taskRequest.getRemoveId(), taskRequest.getRemovedIndex(), taskRequest.getAddId(), taskRequest.getAddedIndex());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/get-all-person-in-task")
    public ResponseEntity<?> getAllPersonInTask(@RequestParam("taskId") Long taskId) {
        List<SearchUserDto> user = taskService.getAllPersonInTask(taskId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/get-list-person-to-add-to-task")
    public ResponseEntity<?> getListUser(@RequestParam("taskId") Long taskId) {
        List<SearchUserDto> userDtos = taskService.getAllPersonToAddToTask(taskId);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
    @PutMapping ("/update-image")
    public ResponseEntity<?> updateImage(@RequestBody TaskRequest taskRequest) {
        if(taskRepository.findById(taskRequest.getTaskId()).isPresent()){
            Task task = taskRepository.getById(taskRequest.getTaskId());
            task.setImage(taskRequest.getUrlImage());
            taskRepository.save(task);
            return ResponseEntity.ok(taskRequest.getUrlImage());
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
