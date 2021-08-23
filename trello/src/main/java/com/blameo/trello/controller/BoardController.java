package com.blameo.trello.controller;

import com.blameo.trello.model.Board;
import com.blameo.trello.model.BoardUser;
import com.blameo.trello.model.User;
import com.blameo.trello.model.dto.BoarDto;
import com.blameo.trello.model.dto.SearchUserDto;
import com.blameo.trello.model.dto.UserDto;
import com.blameo.trello.model.request.BoardRequest;
import com.blameo.trello.model.request.SearchUserRequest;
import com.blameo.trello.repository.BoardRepository;
import com.blameo.trello.repository.BoardUserRepository;
import com.blameo.trello.repository.UserRepository;
import com.blameo.trello.service.BoardService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/board")
public class BoardController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardUserRepository boardUserRepository;

    @Autowired
    BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<?> createBoard(@RequestBody BoardRequest boardRequest, Authentication authentication) {
        boardService.createBoard(boardRequest.getTitle(), authentication);
        return ResponseEntity.ok("Create success");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteBoard(@RequestBody BoardRequest boardRequest) {
        try {
            Board board = boardRepository.getById(boardRequest.getBoardId());
            board.setIsHide(true);
            boardRepository.save(board);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(Authentication authentication) {
        try {
            List<BoardUser> boardUserList =
                    boardUserRepository.findByUser(userRepository.findByUsername(authentication.getName()));
            List<Board> boardList = new ArrayList<>();
            boardUserList.forEach(bu -> {
                boardList.add(boardRepository.findByBoardUsers(bu));
            });
            List<Board> list = boardList.stream().filter(x -> x.getIsHide().equals(false)).collect(Collectors.toList());
            List<BoarDto> dtoList = new ArrayList<>();
            list.forEach(x -> {
                BoarDto dto = new BoarDto();
                BeanUtils.copyProperties(x, dto);
                dtoList.add(dto);
            });
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-user-to-board")
    public ResponseEntity<?> addUserToBoard(@RequestBody BoardRequest boardRequest) {
        try {
            User user = userRepository.getById(boardRequest.getUserId());
            Board board = boardRepository.getById(boardRequest.getBoardId());
            if (!boardUserRepository.findByBoardAndUser(boardRequest.getUserId(), boardRequest.getBoardId()).isPresent()) {
                BoardUser boardUser = new BoardUser();
                boardUser.setUser(user);
                boardUser.setBoard(board);
                boardUserRepository.save(boardUser);
                return new ResponseEntity<>("Add user to board success",HttpStatus.OK);
            }
            return new ResponseEntity<>("User is exist in board", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-list-person-to-add-to-board")
    public ResponseEntity<?> getListUser(@RequestParam("boardId") Long boardId,@RequestParam("keyword") String keyword) {
        Set<User> list =userRepository.getUserByKeyword(keyword, boardId);
        List<SearchUserDto> dtoList = new ArrayList<>();
        list.forEach(x ->{
            SearchUserDto dto = new SearchUserDto();
            BeanUtils.copyProperties(x, dto);
            dtoList.add(dto);
        });
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/get-all-user-in-board")
    public ResponseEntity<?> getUserInBoard(@RequestParam("boardId") Long boardId) {
        if (boardRepository.findById(boardId).isPresent()) {
            List<SearchUserDto> list = boardService.getUserInBoard(boardId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/delete-person-from-board")
    public ResponseEntity<?> deleteUserToBoard(@RequestParam("userId") Long userId, @RequestParam("boardId") Long boardId) {

        Optional<BoardUser> bu = boardUserRepository.findByBoardAndUser(userId, boardId);
        if(bu.isPresent()){
            boardUserRepository.deleteById(bu.get().getId());
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
