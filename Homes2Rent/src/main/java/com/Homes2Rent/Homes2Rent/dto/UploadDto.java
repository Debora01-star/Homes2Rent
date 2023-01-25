package com.Homes2Rent.Homes2Rent.dto;

import java.util.List;

public class UploadDto {


    private Long id;

    public String name;

    public String content;

    public String contentType;

    public int contentLength;
    private List<UploadDto> allUploadsByFoto;

    public List<UploadDto> uploadbyId;

    public UploadDto(Long id, String name, String content, String contentType, int contentLength) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.contentType = contentType;
        this.contentLength = contentLength;

    }

    public UploadDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public List<UploadDto> getAllUploadsByFoto() {
        return allUploadsByFoto;
    }

    public void setAllUploadsByFoto(List<UploadDto> allUploadsByFoto) {
        this.allUploadsByFoto = allUploadsByFoto;
    }

    public List<UploadDto> getUploadbyId() {
        return uploadbyId;
    }

    public void setUploadbyId(List<UploadDto> uploadbyId) {
        this.uploadbyId = uploadbyId;
    }

    public void setContentLenght(int contentLength) {
    }
}


