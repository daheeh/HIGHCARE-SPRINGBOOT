package com.highright.highcare.reservation.service;

import com.highright.highcare.reservation.dto.ResourceCategoryDTO;
import com.highright.highcare.reservation.dto.ResourceDTO;
import com.highright.highcare.reservation.dto.ResourceFileDTO;
import com.highright.highcare.reservation.dto.ResourceReservationStatusDTO;
import com.highright.highcare.reservation.entity.*;
import com.highright.highcare.reservation.repository.ResourceCategoryRepository;
import com.highright.highcare.reservation.repository.ResourceFileRepository;
import com.highright.highcare.reservation.repository.ResourceRespository;
import com.highright.highcare.reservation.repository.ResourceStatusRepository;
import com.highright.highcare.util.FileUploadUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResService {

    private final ResourceCategoryRepository resourceCategoryRepository;
    private final ResourceRespository resourceRespository;
    private final ResourceFileRepository resourceFileRepository;
    private final ResourceStatusRepository resourceStatusRepository;
    private final ModelMapper modelMapper;
    @Value("${image.image-dir}")
    String IMAGE_DIR; // src/main/resources/static/images

    @Value("${image.image-url}")
    String IMAGE_URL; //http://localhost:8080/images/
    public ResService(ResourceCategoryRepository resourceCategoryRepository,
                      ModelMapper modelMapper,
                      ResourceRespository resourceRespository,
                      ResourceFileRepository resourceFileRepository,
                      ResourceStatusRepository resourceStatusRepository){
        this.resourceCategoryRepository = resourceCategoryRepository;
        this.modelMapper = modelMapper;
        this.resourceRespository = resourceRespository;
        this.resourceFileRepository =resourceFileRepository;
        this.resourceStatusRepository = resourceStatusRepository;
    }
    public List<ResourceCategoryDTO> selectResCategory() {
        List<ResourceCategory> resList = resourceCategoryRepository.findAll();
        return resList.stream()
                .map(res -> modelMapper.map(res, ResourceCategoryDTO.class)).collect(Collectors.toList());
    }
    @Transactional
    public Object insertCategory(ResourceCategoryDTO resourceCategory) {
    ResourceCategory res = modelMapper.map(resourceCategory, ResourceCategory.class);

    resourceCategoryRepository.save(res);

    resourceCategory.setCategoryCode(res.getCategoryCode());
    return resourceCategory;

    }
    @Transactional
    public Object insertRes(ResourceDTO resourceDTO, MultipartFile image) throws IOException {

        java.util.Date utilDate = new java.util.Date();
        long currentMilliseconds = utilDate.getTime();
        java.sql.Date sqlDate = new java.sql.Date(currentMilliseconds);

        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        resource.setDeleteYn('N');
        resource.setCreationDate(sqlDate);
        resource.setModifiedDate(sqlDate);
        resource.setResourceCategory(resourceCategoryRepository.findById(resourceDTO.getCategoryCode()).get());

        resourceRespository.save(resource);


        String imageName = UUID.randomUUID().toString().replace("-","");
        String replaceFileName = null;

        int result = 0;
        ResourceFileDTO resourceFileDTO = new ResourceFileDTO();
        try{
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, image);
            resourceFileDTO.setChangedFileName(imageName);
            resourceFileDTO.setModifiedDate(sqlDate);
            resourceFileDTO.setCreationDate(sqlDate);
            resourceFileDTO.setDeleteYn('N');
            resourceFileDTO.setResourceCode(resource.getResourceCode());

            ResourceFile resourceFile = modelMapper.map(resourceFileDTO, ResourceFile.class);
            resourceFileRepository.save(resourceFile);

            result = 1;
        } catch (Exception e) {
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }
        return (result > 0) ? " 입력 성공": "입력 실페";

    }

    public Object selectRes(int categoryCode) {
        List<ResourceArea> resources= resourceRespository.findByResourceCategoryAndDeleteYn(resourceCategoryRepository.findById(categoryCode).get(), 'N');
        return resources.stream()
                .map(res-> modelMapper.map(res,ResourceDTO.class)).collect(Collectors.toList());
    }

    public Object selectContent(int resourceCode) {
        Resource resource = resourceRespository.findById(resourceCode).get();
        return modelMapper.map(resource, ResourceDTO.class);

    }

    public Object selectDate(Date reservationDate) {
        List<ResourceReservationStatus> resourceReservationStatuses = resourceStatusRepository.findAllByReservationDateAndReservationStatus(reservationDate,"APPROVAL");
        System.out.println("list" + resourceReservationStatuses);
        return resourceReservationStatuses.stream()
                .map(status -> modelMapper.map(status, ResourceReservationStatusDTO.class)).collect(Collectors.toList());
    }
}
