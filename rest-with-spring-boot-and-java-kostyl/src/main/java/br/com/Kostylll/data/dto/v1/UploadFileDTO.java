package br.com.Kostylll.data.dto.v1;

import java.io.Serializable;
import java.util.Objects;

public class UploadFileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName;
    private String fileType;
    private String fileDownloadUri;
    private Long  fileSize;

    public UploadFileDTO() {}

    public UploadFileDTO(String fileName, String fileType, String fileDownloadUri, Long fileSize) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileDownloadUri = fileDownloadUri;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadFileDTO that = (UploadFileDTO) o;
        return Objects.equals(getFileName(), that.getFileName()) && Objects.equals(getFileType(), that.getFileType()) && Objects.equals(getFileDownloadUri(), that.getFileDownloadUri()) && Objects.equals(getFileSize(), that.getFileSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFileName(), getFileType(), getFileDownloadUri(), getFileSize());
    }
}
