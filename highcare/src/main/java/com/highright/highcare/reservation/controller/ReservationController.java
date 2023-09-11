package com.highright.highcare.reservation.controller;

import com.highright.highcare.common.Criteria;
import com.highright.highcare.common.PageDTO;
import com.highright.highcare.common.PagingResponseDTO;
import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.reservation.dto.ResourceCategoryDTO;
import com.highright.highcare.reservation.dto.ResourceDTO;
import com.highright.highcare.reservation.dto.ResourceReservationStatusDTO;
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
    @GetMapping("/resList")
    public ResponseEntity<ResponseDTO> selectRes(@RequestParam(name = "categoryCode") String code) {
        int categoryCode = Integer.parseInt(code);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "시설 지역 조회 성공", resService.selectRes(categoryCode)));
    }

    @GetMapping("content")
    public ResponseEntity<ResponseDTO> selectContent(@RequestParam(name = "resourceCode") int resourceCode){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "시설 조회 성공", resService.selectContent(resourceCode)));

    }

    @GetMapping("dateRes")
    public ResponseEntity<ResponseDTO> selectDateRes(@RequestParam(name = "reservationDate")String reservationDate ,@RequestParam(name = "resourceCode")int resourceCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "예약 시간 조회 성공", resService.selectDate(reservationDate,resourceCode)));
    }

    @PostMapping("/status")
    public ResponseEntity<ResponseDTO> insertDateRes(@RequestBody ResourceReservationStatusDTO resourceReservationStatusDTO) {
        System.out.println("reservationDate : " + resourceReservationStatusDTO.getReservationDate());
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "예약 성공", resService.insertResStatus(resourceReservationStatusDTO)));
    }

    @PutMapping("/modRes")
    public ResponseEntity<ResponseDTO> updateRes(@ModelAttribute ResourceDTO resourceDTO, MultipartFile image) throws IOException {

                return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "시설수정 성공", resService.updateRes(resourceDTO, image)));
    }

    @PutMapping("/deleteRes")
    public ResponseEntity<ResponseDTO> deleteRes(@RequestBody ResourceDTO resourceDTO)  {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "시설수정 성공", resService.deleteRes(resourceDTO)));
    }

    @GetMapping("/reser")
    public ResponseEntity<ResponseDTO> selectReser(@RequestParam(name = "empNo") int empNo,@RequestParam(name = "currentPage") int currentPage){
        Criteria cri = new Criteria(Integer.valueOf(currentPage), 10);
        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        int total = resService.selectResTotal(empNo);
        pagingResponseDTO.setData(resService.selectStatusListWithPaging(cri,empNo));

        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "신청 현황 조회 성공", pagingResponseDTO));
    }

    @GetMapping("/reserAll")
    public ResponseEntity<ResponseDTO> selectReserAll(@RequestParam(name = "currentPage") int currentPage){
        Criteria cri = new Criteria(Integer.valueOf(currentPage), 10);
        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        int total = resService.selectAllResTotal();
        pagingResponseDTO.setData(resService.selectAllStatusListWithPaging(cri));

        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "신청 현황 조회 성공", pagingResponseDTO));
    }

    @PutMapping("/approval")
    public ResponseEntity<ResponseDTO> updateReser(@RequestBody ResourceReservationStatusDTO resourceReservationStatusDTO)  {
        System.out.println("resourceReservationStatusDTO.getStatusCode() = " + resourceReservationStatusDTO.getStatusCode());
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "신청 승인", resService.updateResStatus(resourceReservationStatusDTO)));
    }

    @PutMapping("/rejected")
    public ResponseEntity<ResponseDTO> updateRejected(@RequestBody ResourceReservationStatusDTO resourceReservationStatusDTO)  {
        System.out.println("resourceReservationStatusDTO.getStatusCode() = " + resourceReservationStatusDTO.getStatusCode());
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "신청 거절", resService.updateResrejected(resourceReservationStatusDTO)));
    }
}
