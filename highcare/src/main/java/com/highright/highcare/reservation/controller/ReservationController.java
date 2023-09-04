package com.highright.highcare.reservation.controller;

import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.reservation.dto.ResourceCategoryDTO;
import com.highright.highcare.reservation.dto.ResourceDTO;
import com.highright.highcare.reservation.service.ResService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/res")
public class ReservationController {

    private final ResService resService;
    public ReservationController(ResService resService){
        this.resService = resService;
    }
    @GetMapping("/res")
    public ResponseEntity<ResponseDTO> selectResCategory(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "시설 카테고리 조회 성공", resService.selectResCategory()));
    }

    @PostMapping("/category")
    public ResponseEntity<ResponseDTO> insertCategory(@RequestBody ResourceCategoryDTO resourceCategory){
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "카테고리 추가 성공", resService.insertCategory(resourceCategory)));
    }

    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> insertRes(@ModelAttribute ResourceDTO resourceDTO, MultipartFile image) throws IOException {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "시설추가 성공", resService.insertRes(resourceDTO, image)));
    }

    }
