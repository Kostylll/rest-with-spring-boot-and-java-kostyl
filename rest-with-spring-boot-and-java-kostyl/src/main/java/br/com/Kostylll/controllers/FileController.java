package br.com.Kostylll.controllers;

import br.com.Kostylll.data.dto.v1.UploadFileDTO;
import br.com.Kostylll.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileDTO> uploadMultipleFiles (@RequestParam("files") MultipartFile[] files){

        return Arrays.asList(files)
                .stream()
                .map(file ->
                    uploadFile(file)
                ).collect(Collectors.toList());

    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile (@PathVariable String fileName, HttpServletRequest request){

        Resource resource = (Resource) fileStorageService.loadFileAsResource(fileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment=\"" + resource.getFilename() + "\"")
                .body(resource);

    };

}
