package br.com.Kostylll.services;

import br.com.Kostylll.config.FileStorageConfig;
import br.com.Kostylll.exception.FileStoragreException;
import com.ctc.wstx.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    public final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {

        Path path = Paths.get(fileStorageConfig.getUpload_dir()).toAbsolutePath().normalize();
        this.fileStorageLocation = path;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
           throw new FileStoragreException("Não foi possível criar o diretório",e);
        }
    }

    public String storeFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")){
                throw new FileStoragreException("Arquivo Inválido para Nome!" + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        }catch (Exception e){
            throw new FileStoragreException("Nao foi possível salvar o arquivo: " + fileName,e);
        }
    }







}
