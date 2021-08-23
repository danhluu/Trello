package com.blameo.trello.controller;


import com.blameo.trello.model.Deadline;
import com.blameo.trello.model.dto.DeadlineDto;
import com.blameo.trello.model.request.DeadlineRequest;
import com.blameo.trello.repository.DeadlineRepository;
import com.blameo.trello.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/deadline")
public class DeadlineController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    DeadlineRepository deadlineRepository;

    //taskId
    @GetMapping("/get-deadline")
    public ResponseEntity<?> getDeadline(@RequestParam("taskId") Long taskId) {
        if (taskRepository.findById(taskId).isPresent()) {

            Deadline deadline = deadlineRepository.getByTaskId(taskId);
            DeadlineDto dto = new DeadlineDto();
//            if(deadline!=null) {
                dto.setComplete(deadline.getComplete());
                dto.setDeadlineId(deadline.getDeadlineId());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                dto.setStartDate(formatter.format(deadline.getStartDate()));
                dto.setEndDate(formatter.format(deadline.getEndDate()));
//            }

            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // taskId, startDate, endDate
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DeadlineRequest deadlineRequest) throws ParseException {
        if (taskRepository.findById(deadlineRequest.getTaskId()).isPresent()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = formatter.parse(deadlineRequest.getStartDate());
            Date endDate = formatter.parse(deadlineRequest.getEndDate());

            Deadline deadline = new Deadline();
            deadline.setStartDate(startDate);
            deadline.setEndDate(endDate);
            deadline.setTaskId(deadlineRequest.getTaskId());
            deadline.setComplete(false);
            deadlineRepository.save(deadline);

            Deadline deadline1 = deadlineRepository.getByTaskId(deadlineRequest.getTaskId());
            DeadlineDto dto = new DeadlineDto();
            dto.setEndDate(deadlineRequest.getEndDate());
            dto.setStartDate(deadlineRequest.getStartDate());
            dto.setDeadlineId(deadline1.getDeadlineId());
            dto.setComplete(deadline1.getComplete());
            dto.setTaskId(deadlineRequest.getTaskId());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // taskId, startDate, endDate
    @PutMapping("/update")
    public ResponseEntity<?> updateDeadline(@RequestBody DeadlineRequest deadlineRequest) throws ParseException {
        if (deadlineRepository.findById(deadlineRequest.getDeadlineId()).isPresent()) {
            Deadline deadline = deadlineRepository.getById(deadlineRequest.getDeadlineId());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = formatter.parse(deadlineRequest.getStartDate());
            Date endDate = formatter.parse(deadlineRequest.getEndDate());

            deadline.setStartDate(startDate);
            deadline.setEndDate(endDate);
            deadline.setComplete(deadlineRequest.getComplete());
            deadlineRepository.save(deadline);

            Deadline deadline1 = deadlineRepository.getById(deadlineRequest.getDeadlineId());
            DeadlineDto dto = new DeadlineDto();
            dto.setEndDate(deadlineRequest.getEndDate());
            dto.setStartDate(deadlineRequest.getStartDate());
            dto.setDeadlineId(deadline1.getDeadlineId());
            dto.setComplete(deadline1.getComplete());
            dto.setTaskId(deadline1.getTaskId());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete-deadline")
    public ResponseEntity<?> deleteDeadline(@RequestParam("deadlineId") Long deadlineId) {
        if (deadlineRepository.findById(deadlineId).isPresent()) {

            deadlineRepository.deleteById(deadlineId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
