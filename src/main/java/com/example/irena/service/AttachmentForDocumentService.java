package com.example.irena.service;
import com.example.irena.entity.AttachmentEntity;
import com.example.irena.exception.attach.CouldNotRead;
import com.example.irena.exception.attach.FileUploadException;
import com.example.irena.exception.attach.OriginalFileNameNullException;
import com.example.irena.exception.attach.SomethingWentWrong;
import com.example.irena.model.attach.AttachmentDownloadDTO;
import com.example.irena.model.attach.AttachmentResponseDTO;
import com.example.irena.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttachmentForDocumentService {

    private final AttachmentRepository repository;


    private String attachUploadFolder="D:/KODLAR/Irena/src/main/resources/static/document/";

    @Value("${attach.download.url}")
    private String attachDownloadUrl;


//    public String getYearMonthDay() {
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        int month = Calendar.getInstance().get(Calendar.MONTH);
//        int day = Calendar.getInstance().get(Calendar.DATE);
//        return year + "/" + month + "/" + day; // 2022/03/23
//    }


    public String getExtension(String fileName) {
        // mp3/jpg/npg/mp4.....
        if (fileName == null) {
            throw new OriginalFileNameNullException("File name null");
        }
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public AttachmentEntity saveToSystem(MultipartFile file) {
        try {
            System.out.println(file.getContentType());
            File folder = new File(attachUploadFolder );
            if (!folder.exists()) folder.mkdirs();
            String fileName = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String extension = getExtension(file.getOriginalFilename()); //zari.jpg

            // attaches/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachUploadFolder +  "/" + fileName + "." + extension);
            File f = Files.write(path, bytes).toFile();

            AttachmentEntity entity = new AttachmentEntity();
            entity.setId(fileName);
            entity.setOriginName(file.getOriginalFilename());
            entity.setType(extension);
            entity.setPath(attachUploadFolder);
            entity.setSize(file.getSize());
            entity.setContentType(file.getContentType());
                MultimediaObject instance = new MultimediaObject(f);
                MultimediaInfo result = instance.getInfo();
                entity.setDuration((double) (result.getDuration() / 1000));

        return   repository.save(entity);
        } catch (IOException e) {
            throw new FileUploadException("File could not upload");
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }

    }

    private AttachmentEntity getAttachment(String fileName) throws FileNotFoundException {
        String id = fileName.split("\\.")[0];
        Optional<AttachmentEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new FileNotFoundException("File Not Found");
        }
        return optional.get();
    }
    public byte[] open(String fileName) {
        try {
            AttachmentEntity entity = getAttachment(fileName);

            Path file = Paths.get(attachUploadFolder + entity.getPath() + "/" + fileName);
            return Files.readAllBytes(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public AttachmentDownloadDTO download(String fileName) {
        try {
            AttachmentEntity entity = getAttachment(fileName);


            File file = new File(attachUploadFolder + entity.getPath() + "/" + fileName);

            File dir = file.getParentFile();
            File rFile = new File(dir, entity.getId() + "." + entity.getType());

            Resource resource = new UrlResource(rFile.toURI());

            if (resource.exists() || resource.isReadable()) {
                return new AttachmentDownloadDTO(resource, entity.getContentType());
            } else {
                throw new CouldNotRead("Could not read");
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new SomethingWentWrong("Something went wrong");
        }
    }


    public Page<AttachmentResponseDTO> getWithPage(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AttachmentEntity> pageObj = repository.findAll(pageable);

        List<AttachmentEntity> entityList = pageObj.getContent();
        List<AttachmentResponseDTO> dtoList = new ArrayList<>();


        for (AttachmentEntity entity : entityList) {

            AttachmentResponseDTO dto = new AttachmentResponseDTO();
            dto.setId(entity.getId());
            dto.setPath(entity.getPath());
            dto.setExtension(entity.getType());
            dto.setUrl(attachDownloadUrl + "/" + entity.getId() + "." + entity.getType());
            dto.setOriginalName(entity.getOriginName());
            dto.setSize(entity.getSize());
            dto.setCreatedData(entity.getCreatedDate());
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }


    public String deleteById(String fileName) {
        try {
            AttachmentEntity entity = getAttachment(fileName);
            Path file = Paths.get(attachUploadFolder + entity.getPath() + "/" + fileName);

            Files.delete(file);
            repository.deleteById(entity.getId());

            return "deleted";
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getUrl(String imageId) throws FileNotFoundException {
        Optional<AttachmentEntity> optional = repository.findById(imageId);
        if (optional.isEmpty()) {
            throw new FileNotFoundException("File not found");
        }
        return attachDownloadUrl + optional.get().getId() + "." + optional.get().getType();

    }


}

