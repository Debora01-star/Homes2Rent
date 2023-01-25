package com.Homes2Rent.Homes2Rent.dto;


import org.hibernate.validator.constraints.Length;



@Length
public class UploadInputDto {

        private Long Id;

        public String name;

        public String content;

        public String contentType;

        public int contentLength;
        private int contentlenght;


        public UploadInputDto(Long Id, String name, String content, String contentType, int contentlenght) {
                this.Id = Id;
                this.name = name;
                this.content = content;
                this.contentType = contentType;
                this.contentLength = contentlenght;

        }

        public UploadInputDto() {
        }

        public Long getId() {
                return Id;
        }

        public void setId(Long id) {
                Id = id;
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

        public int getContentlenght() {
                return contentlenght;
        }

        public void setContentlenght(int contentlenght) {
                this.contentlenght = contentlenght;
        }
}