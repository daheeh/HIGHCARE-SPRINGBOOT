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
    public ResponseEntity<ResponseDTO> selectBoardList(
            @RequestParam(name = "categoryCode") String categoryCode,
            @RequestParam(name = "currentPage") String currentPage){
        System.out.println("와성용");
        System.out.println("cateogryCode"+ categoryCode);
        System.out.println("currentPage"+ currentPage);
        int boardCategoryCode = Integer.valueOf(categoryCode);
        int total = boardService.selectBoardTotal(boardCategoryCode);
        return null;
    }

    @GetMapping("/boardTitle")
    public ResponseEntity<ResponseDTO> selectBoardTitle(){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "게시글 조회 성공", boardService.selectBoardTitle()));

    }
//    @PostMapping("/boardAdd")
//    public ResponseEntity<ResponseDTO> boardNameAdd(@RequestBody BulletinCategoriesDTO bulletinCategoriesDTO){
//
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(),"게시판 카테고리 추가 성공",boardService.boardAdd(bulletinCategoriesDTO)));
//    }
}
