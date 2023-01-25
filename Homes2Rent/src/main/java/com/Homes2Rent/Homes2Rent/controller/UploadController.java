package com.Homes2Rent.Homes2Rent.controller;


import com.Homes2Rent.Homes2Rent.dto.UploadDto;
import com.Homes2Rent.Homes2Rent.dto.UploadInputDto;
import com.Homes2Rent.Homes2Rent.dto.UserDto;
import com.Homes2Rent.Homes2Rent.exceptions.BadRequestException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.exceptions.UsernameNotFoundException;
import com.Homes2Rent.Homes2Rent.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Homes2Rent.Homes2Rent.service.UploadService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;



    @RestController
    public class UploadController {

        // We importeren hier (via de constructor, maar het mag ook @Autowired zijn) nu de Service in plaats van direct de Repository.
        private final UploadService uploadService;


        @Autowired
        public UploadController(UploadService uploadService) {
            this.uploadService = uploadService;
        }

        // Je ziet dat de return waarde van deze methode nu ResponseEntity<List<TelevisionDto>> is in plaats van <ResponseEntity<List<Television>>
        @GetMapping("/uploads")
        public ResponseEntity<List<UploadDto>> getAllUploads(@RequestParam(value = "foto", required = false) Optional<String> foto) {

            List<UploadDto> dtos;
            if (foto.isEmpty()) {

                // We halen niet direct uit de repository een lijst met Televisions, maar we halen uit de service een lijst met TelevisionDto's
                dtos = uploadService.getAllUploads();

            } else {
                // Dit is ook een service methode geworden in plaats van direct de repository aan te spreken.
                dtos = uploadService.getAllUploadsbyFoto(foto.get());

            }

            return ResponseEntity.ok().body(dtos);

        }

        // De return waarde is ook hier een TelevisionDto in plaats van een Television
        @GetMapping("/uploads/{id}")
        public ResponseEntity<UploadDto> getUpload(@PathVariable("id") Long id) {

            // We spreken hier ook weer een service methode aan in plaats van direct de repository aan te spreken
            UploadDto upload = uploadService.getUpload(id);

            return ResponseEntity.ok().body(upload);

        }

        // Ook hier returnen we weer een TelevisionDto, maar ook de parameter is een dto geworden.
        // Het is niet verplicht om een "outputdto" en een "inputdto" te hebben, zeker omdat ze in dit geval hetzelfde zijn,
        // maar we willen jullie laten zien dat het mogelijk is. In sommige gevallen kan het zelfs nodig zijn.
        @PostMapping("/uploads")
        public ResponseEntity<UploadDto> addUpload(@Valid @RequestBody UploadInputDto uploadInputDto) {

            // Hier gebruiken we weer een service methode in plaats van direct de repository aan te spreken.

            UploadDto dto = uploadService.addUpload(UploadInputDto);


            return ResponseEntity.created(null).build();

        }
        // Hier veranderd niks aan de methode. We hebben niet meer de naam van de pathvariabele expliciet genoemd, omdat de
        // parameter-naam overeen komt met de naam van de pathvariabele.
        @DeleteMapping("/uploads/{id}")
        public ResponseEntity<Object> deleteUpload(@PathVariable Long id) {

            // Hier gebruiken we weer een service methode in plaats van direct de repository aan te spreken.
            uploadService.deleteUpload(id);

            return ResponseEntity.noContent().build();

        }

        // Deze methode returned nu een ResponseEntity<TelevisionDto> in plaats van een ResponseEntity<Television> en deze
        // methode vraagt nu om een Long en een TelevisionInputDto in de parameters in plaats van een Long en een Television.
        @PutMapping("/uploads/{id}")
        public ResponseEntity<UploadDto> updateUpload(@PathVariable Long id, @Valid @RequestBody UploadInputDto newUpload) throws RecordNotFoundException {

            // Hier gebruiken we weer een service methode in plaats van direct de repository aan te spreken.
            // Alle logica die hier eerst stond, is nu ook verplaatst naar de service laag.
            UploadDto dto = uploadService.updateUpload(id, newUpload);

            return ResponseEntity.ok().body(dto);
        }

        private UploadInputDto newUpload;
        private com.Homes2Rent.Homes2Rent.dto.UploadInputDto UploadInputDto;

    }