package com.highright.highcare.bulletin.controller;

import com.highright.highcare.bulletin.dto.BoardDTO;
import com.highright.highcare.bulletin.dto.CommentDTO;
import com.highright.highcare.bulletin.dto.BoardPagingResponseDTO;
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

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public ResponseEntity<ResponseDTO> selectBoardList(
            @RequestParam(name = "categoryCode") String categoryCode,
            @RequestParam(name = "currentPage") String currentPage,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "empNo")int empNo) {
        System.out.println("categoryCode : " + categoryCode);

        int boardCategoryCode = Integer.valueOf(categoryCode);
        Criteria cri = new Criteria(Integer.valueOf(currentPage), 10);
        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        int total = 0;
        if(content == ""){
             total = boardService.selectBoardTotal(boardCategoryCode,empNo);

            pagingResponseDTO.setData(boardService.selectBoardListWithPaging(cri, boardCategoryCode,empNo));

        }else{
             total = boardService.selectSearchTotal(boardCategoryCode, content,empNo);
            pagingResponseDTO.setData(boardService.selectBoardListWithPagingSearch(cri, boardCategoryCode, content,empNo));
        }
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "조회 성공", pagingResponseDTO));
    }

    @GetMapping("/thread")
    public ResponseEntity<ResponseDTO> selectBoardDetail(
            @RequestParam(name = "bulletinCode") String bulletinCode
    ) {
        int code = Integer.parseInt(bulletinCode);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "글 조회 성공", boardService.selectBoard(code)));
    }
    @GetMapping("/comment")
    public ResponseEntity<ResponseDTO> selectCommentDetail(
            @RequestParam(name = "bulletinCode") String bulletinCode,
            @RequestParam(name = "currentPage") String currentPage
    ) {
        int total = boardService.selectCommentTotal(bulletinCode);
        int code = Integer.parseInt(bulletinCode);

        Criteria cri = new Criteria(Integer.valueOf(currentPage), 5);
        BoardPagingResponseDTO pagingResponseDTO = new BoardPagingResponseDTO();
        pagingResponseDTO.setData(boardService.selectBoardAndCommentPaging(cri, bulletinCode));
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
        pagingResponseDTO.setTotal(total);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "댓글 조회 성공", pagingResponseDTO));
    }
    @GetMapping("/thr")
    public ResponseEntity<ResponseDTO> selectBoardDetails(
            @RequestParam(name = "bulletinCode") String bulletinCode
    ){
        int code = Integer.parseInt(bulletinCode);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "글 조회 성공", boardService.selectBoards(code)));
    }

    @GetMapping("/boardTitle")
    public ResponseEntity<ResponseDTO> selectBoardTitle() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "게시글 조회 성공", boardService.selectBoardTitle()));

    }

    @PostMapping("/insertBoard")
    public ResponseEntity<ResponseDTO> insertBoard(@RequestBody BoardDTO boardDTO) {
        System.out.println("boardDTO get empNo : " + boardDTO.getEmpNo());
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED.value(), "글쓰기 성공", boardService.insertBoard(boardDTO)));
    }

    @PostMapping("/insertComment")
    public ResponseEntity<ResponseDTO> insertComment(@RequestBody BoardDTO boardDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED.value(), "댓글쓰기 성공", boardService.insertComment(boardDTO)));
    }
    @PutMapping("/updateBoard")
    public ResponseEntity<ResponseDTO> updateBoard(@RequestBody BoardDTO boardDTO){
        System.out.println("put mapping입니다");
        System.out.println("boardDTO : " + boardDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED.value(), "글 수정 성공", boardService.updateBoard(boardDTO)));
    }

    @PutMapping("/deleteBoard")
    public ResponseEntity<ResponseDTO> deleteBoard(@RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED.value(), "글 삭제 성공", boardService.deleteBoard(boardDTO)));
    }

    @PutMapping("/deleteComment")
    public ResponseEntity<ResponseDTO> deleteBoard(@RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED.value(), "댓글 삭제 성공", boardService.deleteComment(commentDTO)));

    }
    @PutMapping("/updateComment")
    public ResponseEntity<ResponseDTO> updateBoard(@RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED.value(), "댓글 수정 성공", boardService.updateComment(commentDTO)));

    }
    @GetMapping("/notice")
    public ResponseEntity<ResponseDTO> selectnotice(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "공지 조회 성공", boardService.selectNotice()));

    }
}
