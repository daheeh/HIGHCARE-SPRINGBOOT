package com.highright.highcare.bulletin.sevice;

import com.highright.highcare.bulletin.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.highright.highcare.bulletin.dto.BoardDTO;
import com.highright.highcare.bulletin.entity.Board;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    public BoardService(BoardRepository boardRepository,
                        ModelMapper modelMapper){
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;

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
}
