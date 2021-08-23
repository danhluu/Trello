package com.blameo.trello.service;

import com.blameo.trello.model.Board;
import com.blameo.trello.model.BoardUser;
import com.blameo.trello.model.User;
import com.blameo.trello.model.dto.BoarDto;
import com.blameo.trello.model.dto.SearchUserDto;
import com.blameo.trello.model.dto.UserDto;
import com.blameo.trello.repository.BoardRepository;
import com.blameo.trello.repository.BoardUserRepository;
import com.blameo.trello.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardUserRepository boardUserRepository;

    public void createBoard(String title, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        Board board = new Board();
        board.setTitle(title);
        board.setCreateBy(user.getId());
        BoardUser boardUser = new BoardUser();
        boardUser.setUser(user);
        boardUser.setBoard(board);
        boardRepository.save(board);
        boardUserRepository.save(boardUser);
    }

    public List<SearchUserDto> getUserInBoard(Long boardId){
        Board board =boardRepository.getById(boardId);
        List<BoardUser> boardUserList=boardUserRepository.findByBoard(board);
        List<SearchUserDto> searchUserDtos = new ArrayList<>();
        boardUserList.forEach(x->{
            SearchUserDto searchUserDto = new SearchUserDto();
            BeanUtils.copyProperties(x.getUser(), searchUserDto);
            searchUserDtos.add(searchUserDto);
        });
        return searchUserDtos;
    }
}
