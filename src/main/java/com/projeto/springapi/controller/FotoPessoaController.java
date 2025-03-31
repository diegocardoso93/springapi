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
    // https://img.freepik.com/free-psd/3d-rendering-hair-style-avatar-design_23-2151869153.jpg?t=st=1743362336~exp=1743365936~hmac=2985dbdb11698bce84d2fd7b4c82fb69ee81a7f97efd546ba474b880350a58b7&w=740
    // https://img.freepik.com/free-psd/3d-rendering-hair-style-avatar-design_23-2151869121.jpg?t=st=1743362366~exp=1743365966~hmac=4bedb9435f0bb42676ae37065caea18ca315f4aa63cefeb7cf3593b4c4b1d7de&w=740

    @GetMapping("/links/{pesId}")
    public ResponseEntity<List<LinkFotoDTO>> getFotoLinks(@PathVariable Long pesId) {
        List<String> links = minIOService.getFotoLinks(pesId);
        List<LinkFotoDTO> linkFotoDTOs = links.stream()
                .map(link -> new LinkFotoDTO(link))
                .collect(Collectors.toList());
        return ResponseEntity.ok(linkFotoDTOs);
    }
}
