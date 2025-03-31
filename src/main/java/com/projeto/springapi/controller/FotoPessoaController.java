package com.projeto.springapi.controller;

import com.projeto.springapi.dto.LinkFotoDTO;
import com.projeto.springapi.service.MinIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fotos")
public class FotoPessoaController {

    @Autowired
    private MinIOService minIOService;

    @PostMapping("/upload")
    public ResponseEntity<List<LinkFotoDTO>> uploadFotos(@RequestParam("files") MultipartFile[] files,
            @RequestParam("pesId") Long pesId) {
        List<String> links = minIOService.uploadFiles(files, pesId);
        List<LinkFotoDTO> linkFotoDTOs = links.stream()
                .map(link -> new LinkFotoDTO(link))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(linkFotoDTOs);
    }

    @GetMapping("/links/{pesId}")
    public ResponseEntity<List<LinkFotoDTO>> getFotoLinks(@PathVariable Long pesId) {
        List<String> links = minIOService.getFotoLinks(pesId);
        List<LinkFotoDTO> linkFotoDTOs = links.stream()
                .map(link -> new LinkFotoDTO(link))
                .collect(Collectors.toList());
        return ResponseEntity.ok(linkFotoDTOs);
    }
}
