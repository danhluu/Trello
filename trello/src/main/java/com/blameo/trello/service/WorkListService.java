package com.blameo.trello.service;

import com.blameo.trello.model.Task;
import com.blameo.trello.model.WorkList;
import com.blameo.trello.repository.BoardRepository;
import com.blameo.trello.repository.WorkListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkListService {

    @Autowired
    WorkListRepository workListRepository;

    @Autowired
    BoardRepository boardRepository;


    public void updateDisplayOrder(Long addIndex, Long removeIndex, Long boardId) {
        List<WorkList> workLists;
        WorkList workList = workListRepository.getWL(removeIndex, boardId).get();
        if (removeIndex < addIndex) {
            workLists = workListRepository.getListWorkListByDisplayOrder(removeIndex + 1, addIndex, boardId);
            workLists.forEach(x -> {
                x.setDisplayOrder(x.getDisplayOrder() - 1);
                workListRepository.save(x);
            });
        } else {
            workLists = workListRepository.getListWorkListByDisplayOrder(addIndex, removeIndex - 1, boardId);
            workLists.forEach(x -> {
                x.setDisplayOrder(x.getDisplayOrder() + 1);
                workListRepository.save(x);
            });
        }
        workList.setDisplayOrder(addIndex);
        workListRepository.save(workList);
    }

    public List<WorkList> getAll(Long boardId) {
        List<WorkList> workLists = workListRepository.findByBoard(boardRepository.getById(boardId));
        workLists = workLists.stream()
                .sorted(Comparator.comparingLong(WorkList::getDisplayOrder))
                .collect(Collectors.toList());
        workLists.forEach(x -> {
            x.setTasks(
                    x.getTasks()
                            .stream()
                            .sorted(Comparator.comparingLong(Task::getDisPlayOrder))
                            .collect(Collectors.toList()));
        });
        return workLists;
    }

    public void deleteWorkList(Long workListId){
        WorkList workList = workListRepository.getById(workListId);
        Long displayOrder = workList.getDisplayOrder();
        workListRepository.deleteById(workListId);
        List<WorkList> workLists = workListRepository.findByBoard(workList.getBoard());
        workLists.forEach(x ->{
            if(x.getDisplayOrder()>displayOrder){
                x.setDisplayOrder(x.getDisplayOrder()-1);
                workListRepository.save(x);
            }
        });
    }
}
