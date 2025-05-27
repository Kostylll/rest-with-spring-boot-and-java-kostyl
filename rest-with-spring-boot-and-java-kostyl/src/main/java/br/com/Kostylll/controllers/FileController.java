package br.com.Kostylll.controllers;

import br.com.Kostylll.data.dto.v1.UploadFileDTO;
import br.com.Kostylll.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/file/v1")
@RestController
public class FileController {

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileDTO uploadFile (MultipartFile file){

       var fileName = fileStorageService.storeFile(file);
       var fileDownLoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
               .path("/api/file/v1/downloadFile/")
               .path(fileName)
               .toUriString();

       return  new UploadFileDTO(fileName,fileDownLoadUri,file.getContentType(), file.getSize());
    }

    public List<UploadFileDTO> uploadMultipleFiles (MultipartFile[] files){
        return  null;

    }

    public ResponseEntity<ResponseEntity> downloadFile (String fileName){
        return null;
    }

}
