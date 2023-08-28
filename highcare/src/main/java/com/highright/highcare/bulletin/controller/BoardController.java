package com.highright.highcare.bulletin.controller;

import com.highright.highcare.bulletin.dto.BoardDTO;
import com.highright.highcare.bulletin.dto.BulletinCategoriesDTO;
import com.highright.highcare.bulletin.sevice.BoardService;
import com.highright.highcare.common.Criteria;
import com.highright.highcare.common.PageDTO;
import com.highright.highcare.common.PagingResponseDTO;
import com.highright.highcare.common.ResponseDTO;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

        Criteria cri = new Criteria(Integer.valueOf(currentPage), 10);
        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(boardService.selectBoardListWithPaging(cri, boardCategoryCode));

        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "조회 성공", pagingResponseDTO));
    }

    @GetMapping("/thread")
    public ResponseEntity<ResponseDTO> selectBoardDetail(
            @RequestParam(name = "bulletinCode") String bulletinCode
    ){
        System.out.println("thread 옴");
        System.out.println("bulletinCode : " + bulletinCode);
        int code = Integer.parseInt(bulletinCode);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),"글 조회 성공"
        ,boardService.selectBoard(code)));
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
    @PostMapping("/insertBoard")
    public ResponseEntity<ResponseDTO> insertBoard(@RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED.value(),"글쓰기 성공",boardService.insertBoard(boardDTO)));

    }
}
