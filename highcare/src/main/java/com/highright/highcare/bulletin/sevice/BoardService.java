package com.highright.highcare.bulletin.sevice;

import com.highright.highcare.bulletin.dto.BulletinCategoriesDTO;
import com.highright.highcare.bulletin.dto.BulletinEmployeeDTO;
import com.highright.highcare.bulletin.dto.CommentDTO;
import com.highright.highcare.bulletin.entity.BulletinCategories;
import com.highright.highcare.bulletin.entity.BulletinEmployee;
import com.highright.highcare.bulletin.entity.Comment;
import com.highright.highcare.bulletin.repository.BoardRepository;
import com.highright.highcare.bulletin.repository.BulletinEmployeeRepository;
import com.highright.highcare.bulletin.repository.CommentRepository;
import com.highright.highcare.common.Criteria;
import net.minidev.json.JSONUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.highright.highcare.bulletin.dto.BoardDTO;
import com.highright.highcare.bulletin.entity.Board;
import com.highright.highcare.bulletin.repository.BoardCategoryRepository;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final ModelMapper modelMapper;

    private final CommentRepository commentRepository;

    private final BulletinEmployeeRepository bulletinEmployeeRepository;
    public BoardService(BoardRepository boardRepository,
                        ModelMapper modelMapper,
                        BoardCategoryRepository boardCategoryRepository,
                        CommentRepository commentRepository,
                        BulletinEmployeeRepository bulletinEmployeeRepository){
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
        this.boardCategoryRepository = boardCategoryRepository;
        this.commentRepository = commentRepository;
        this.bulletinEmployeeRepository = bulletinEmployeeRepository;
    }

    public List<BoardDTO> selectBoardList(){
        System.out.println("=======================");
        System.out.println("service전");
        List<Board> boardList = boardRepository.findAll();
        System.out.println("====================");
        System.out.println("조회후");
        System.out.println("boardList = " + boardList);
        return  boardList.stream()
                .map(board-> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());
    }

    public List<BulletinCategoriesDTO> selectBoardTitle(){
        List<BulletinCategories> boardTitleList = boardCategoryRepository.findAll();
        return boardTitleList.stream()
                .map(boardTitle-> modelMapper.map(boardTitle, BulletinCategoriesDTO.class)).collect(Collectors.toList());
    }
    @Transactional
    public Object boardAdd(BulletinCategoriesDTO bulletinCategoriesDTO) {

        /* 게시판 중복 검사*/
        if(boardCategoryRepository.findByNameBoard(bulletinCategoriesDTO.getNameBoard())!= null){

            System.out.println("asdf");
            System.out.println("이미 있는 게시판입니다");
            return null;
        }
        BulletinCategories category = modelMapper.map(bulletinCategoriesDTO, BulletinCategories.class);

        boardCategoryRepository.save(category);

        bulletinCategoriesDTO.setCategoryCode(category.getCategoryCode());

        return bulletinCategoriesDTO;
    }

    public int selectBoardTotal(int boardCategoryCode) {
        System.out.println("서비스까지 옴");
        List<Board> boardList;
        if(boardCategoryCode>2) {
            boardList = boardRepository.findByDeleteYnAndBulletinCategories('N', boardCategoryRepository.findByCategoryCode(boardCategoryCode));
            System.out.println("3이상" + boardList.size());
        }else{
             boardList = boardRepository.findByDeleteYn('N');
            System.out.println("2이하" + boardList.size());
        }

        return boardList.size();
    }

    public Object selectBoardListWithPaging(Criteria cri, int boardCategoryCode) {
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("bulletinCode").descending());

        Page<Board> result;
        if(boardCategoryCode>2) {
            result = boardRepository.findByDeleteYnAndBulletinCategories('N', boardCategoryRepository.findByCategoryCode(boardCategoryCode),paging);

        }else{
            result = boardRepository.findByDeleteYn('N',paging);
        }


        List<Board> boardList = (List<Board>)result.getContent();
        System.out.println("서비스 ");
        System.out.println(boardList);
        System.out.println("보내줄 값");
        System.out.println(boardList.stream().map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList()));
        return boardList.stream().map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());
    }
    @Transactional
    public Object selectBoard(int code) {
        Board board = boardRepository.findById(code).get();
        board.setViews(board.getViews()+1);
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        List<Comment> comment = commentRepository.findByBoard(board);
        List<CommentDTO> commentList = comment.stream().map(comment1 -> modelMapper.map(comment1, CommentDTO.class)).collect(Collectors.toList());

        boardDTO.setCommentList(commentList);
        boardDTO.setCommentCnt(commentList.size());
        return boardDTO;
    }
    @Transactional
    public Object insertBoard(BoardDTO boardDTO) {
        System.out.println("boardDTO get empNo : " + boardDTO.getEmpNo());
//        BulletinCategories bulletinCategories = boardCategoryRepository.findByCategoryCode(boardDTO.getCategoryCode());
        System.out.println("서비스 옴 : " + boardDTO.getCategoryCode());
            BulletinCategoriesDTO bulletinCategoriesDTO = modelMapper.map(boardCategoryRepository.findByCategoryCode(boardDTO.getCategoryCode()), BulletinCategoriesDTO.class);
        boardDTO.setBulletinCategories(bulletinCategoriesDTO);
        // 임시용
        java.util.Date utilDate = new java.util.Date();
        long currentMilliseconds = utilDate.getTime();
        java.sql.Date sqlDate = new java.sql.Date(currentMilliseconds);
        boardDTO.setCreationDate(sqlDate);
        boardDTO.setModifiedDate(sqlDate);
        boardDTO.setDeleteYn('N');

        BulletinEmployeeDTO bulletinEmployeeDTO = modelMapper.map(bulletinEmployeeRepository.findById(boardDTO.getEmpNo()).get(), BulletinEmployeeDTO.class);

        boardDTO.setBulletinEmployee(bulletinEmployeeDTO);
        System.out.println("bulleinEdto : " + bulletinEmployeeDTO);
        Board board = modelMapper.map(boardDTO, Board.class);
        boardRepository.save(board);

        return 1;
    }
    @Transactional
    public Object insertComment(BoardDTO boardDTO) {
        java.util.Date utilDate = new java.util.Date();
        long currentMilliseconds = utilDate.getTime();
        java.sql.Date sqlDate = new java.sql.Date(currentMilliseconds);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentContent(boardDTO.getContent());
        commentDTO.setBoard(boardDTO);
        commentDTO.setCreationDate(sqlDate);
        commentDTO.setModifiedDate(sqlDate);
        commentDTO.setDeleteYn('N');
        BulletinEmployeeDTO bulletinEmployeeDTO = modelMapper.map(bulletinEmployeeRepository.findById(boardDTO.getEmpNo()).get(), BulletinEmployeeDTO.class);
        commentDTO.setBulletinEmployee(bulletinEmployeeDTO);
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        commentRepository.save(comment);

        return 1;

    }
    @Transactional
    public Object updateBoard(BoardDTO boardDTO) {
        java.util.Date utilDate = new java.util.Date();
        long currentMilliseconds = utilDate.getTime();
        java.sql.Date sqlDate = new java.sql.Date(currentMilliseconds);

        String title = boardDTO.getTitle();
        String content = boardDTO.getContent();
        Board board = boardRepository.findById(boardDTO.getBulletinCode()).get();

        board.setContent(content);
        board.setTitle(title);
        board.setModifiedDate(sqlDate);



        return 1;

    }
    @Transactional
    public Object deleteBoard(BoardDTO boardDTO) {
        java.util.Date utilDate = new java.util.Date();
        long currentMilliseconds = utilDate.getTime();
        java.sql.Date sqlDate = new java.sql.Date(currentMilliseconds);
        Board board = boardRepository.findById(boardDTO.getBulletinCode()).get();
        board.setDeleteYn('Y');
        board.setModifiedDate(sqlDate);

        return 1;

    }
}
