package com.highright.highcare.bulletin.controller;

import com.highright.highcare.bulletin.sevice.BoardService;
import com.highright.highcare.common.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bulletin")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public ResponseEntity<ResponseDTO> selectBoardList(){
        System.out.println("와성용");
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "게시글 조회 성공", boardService.selectBoardList()));
    }
}
