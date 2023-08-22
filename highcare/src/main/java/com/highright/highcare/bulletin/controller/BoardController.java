package com.highright.highcare.bulletin.controller;

import com.highright.highcare.bulletin.dto.BulletinCategoriesDTO;
import com.highright.highcare.bulletin.sevice.BoardService;
import com.highright.highcare.common.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/boardAdd")
    public ResponseEntity<ResponseDTO> boardNameAdd(@RequestBody BulletinCategoriesDTO bulletinCategoriesDTO){


        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),"게시판 카테고리 추가 성공",boardService.boardAdd(bulletinCategoriesDTO)));
    }
}
