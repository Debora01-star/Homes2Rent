package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.UploadDto;
import com.Homes2Rent.Homes2Rent.dto.UploadInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Upload;
import com.Homes2Rent.Homes2Rent.repository.UploadRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UploadService {
    // Zet de annotatie boven de klasse, zodat Spring het herkent en inleest als Service.


    // We importeren de repository nu in de service in plaats van in de controller.
    // dit mag met constructor injection of autowire.
    private final UploadRepository uploadRepository;
    private List<UploadDto> allUploadsByFoto;

    public UploadService(UploadRepository uploadRepository, List<UploadDto> allUploadsByFoto) {
        this.uploadRepository = uploadRepository;
        this.allUploadsByFoto = allUploadsByFoto;
    }


    // Vanuit de repository kunnen we een lijst van Uploads krijgen, maar de communicatie container tussen Service en
    // Controller is de Dto. We moeten de Uploads dus vertalen naar UploadsDtos. Dit moet een voor een, omdat
    // de translateToDto() methode geen lijst accepteert als argument, dus gebruiken we een for-loop.
    public List<UploadDto> getAllUploads() {
        List<Upload> uploadList = uploadRepository.findAll();
        List<UploadDto> uploadDtoList = new ArrayList<>();

        for (Upload upload : uploadList) {
            UploadDto dto = transferToDto(upload);
            uploadDtoList.add(dto);
        }
        return uploadDtoList;
    }

    // Vanuit de repository kunnen we een lijst van Uploads met een bepaalde brand krijgen, maar de communicatie
    // container tussen Service en Controller is de Dto. We moeten de Uploads dus vertalen naar UploadDtos. Dit
    // moet een voor een, omdat de translateToDto() methode geen lijst accepteert als argument, dus gebruiken we een for-loop.
    public List<UploadDto> getAllUploads(String foto) {
        List<Upload> uploadList = uploadRepository.findAllUploadsEqualsIgnoreCase(foto);
        List<UploadDto> uploadDtoList = new ArrayList<>();

        for (Upload upload : uploadList) {
            UploadDto dto = transferToDto(upload);
            uploadDtoList.add(dto);
        }
        return uploadDtoList;
    }

    // Deze methode is inhoudelijk hetzelfde. Wat verandert is, is dat we nu checken
    // op optional.isPresent in plaats van optional.isEmpty en we returnen een UploadDto in plaats van een Upload.
    public UploadDto getUpload(Long id) throws RecordNotFoundException {
        Optional<Upload> uploadOptional = uploadRepository.findById(id);
        if (uploadOptional.isPresent()) {
            Upload upload = uploadOptional.get();
            return transferToDto(upload);
        } else {
            throw new RecordNotFoundException("no foto found");
        }
    }

    // In deze methode moeten we twee keer een vertaal methode toepassen.
    // De eerste keer van dto naar Upload, omdat de parameter een dto is.
    // De tweede keer van upload naar dto, omdat de return waarde een dto is.
    public UploadDto addUpload(UploadInputDto dto) {

        Upload upload = transferToUpload(dto);
        uploadRepository.save(upload);

        return transferToDto(upload);
    }

    // Deze methode is inhoudelijk neit veranderd. Het is alleen verplaatst naar de Service laag.
    public void deleteUpload(@RequestBody Long id) {

        uploadRepository.deleteById(id);

    }

    // Deze methode  staat alleen nu in de Service laag en worden er Dto's en
    // vertaal methodes gebruikt.
    public UploadDto updateUpload(Long id, UploadInputDto newUpload) throws RecordNotFoundException {

        Optional<Upload> uploadOptional = uploadRepository.findById(id);
        if (uploadOptional.isPresent()) {

            Upload upload = uploadOptional.get();

            upload.setId(newUpload.getId());
            upload.setName(newUpload.getName());
            upload.setContent(newUpload.getContent());
            upload.setContentType(newUpload.getContentType());
            upload.setContentLength(newUpload.getContentlenght());

            return transferToDto(upload);

        } else {

            throw new RecordNotFoundException("no foto found");

        }

    }

    // Dit is de vertaal methode van UploadInputDto naar Upload.
    public Upload transferToUpload(UploadInputDto dto) {
        var upload = new Upload();

        upload.setId(dto.getId());
        upload.setName(dto.getName());
        upload.setContent(dto.getContent());
        upload.setContentType(dto.getContentType());
        upload.setContentLength(dto.getContentlenght());

        return upload;
    }

    // Dit is de vertaal methode van Upload naar UploadDto
    public UploadDto transferToDto(Upload upload) {
        UploadDto dto = new UploadDto();

        dto.setId(upload.getId());
        dto.setName(upload.getName());
        dto.setContent(upload.getContent());
        dto.setContentType(upload.getContentType());
        dto.setContentLenght(upload.getContentLength());

        return dto;
    }

    public List<UploadDto> getAllUploadsbyFoto(String s) {

        return allUploadsByFoto;


    }
}