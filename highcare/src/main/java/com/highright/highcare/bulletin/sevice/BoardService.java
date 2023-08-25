package com.highright.highcare.bulletin.sevice;

import com.highright.highcare.bulletin.dto.BulletinCategoriesDTO;
import com.highright.highcare.bulletin.entity.BulletinCategories;
import com.highright.highcare.bulletin.repository.BoardRepository;
import com.highright.highcare.common.Criteria;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final ModelMapper modelMapper;
    public BoardService(BoardRepository boardRepository,
                        ModelMapper modelMapper,
                        BoardCategoryRepository boardCategoryRepository){
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
        this.boardCategoryRepository = boardCategoryRepository;

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

    public Object selectBoard(int code) {
        Board board = boardRepository.findById(code).get();
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }
}
