package com.projeto.springapi.service;

import com.projeto.springapi.exception.ResourceNotFoundException;
import com.projeto.springapi.model.FotoPessoa;
import com.projeto.springapi.model.Pessoa;
import com.projeto.springapi.repository.FotoPessoaRepository;
import com.projeto.springapi.repository.PessoaRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinIOService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Autowired
    private FotoPessoaRepository fotoPessoaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<String> uploadFiles(MultipartFile[] files, Long pesId) {
        List<String> uploadedFiles = new ArrayList<>();
        try {
            Pessoa pessoa = pessoaRepository.findById(pesId).orElseThrow(
                    () -> new ResourceNotFoundException("Pessoa não encontrada com id: " + pesId));

            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            for (MultipartFile file : files) {
                if (file.isEmpty() || file.getSize() == 0) {
                    throw new RuntimeException("Arquivo vazio não pode ser enviado.");
                }

                System.out.println("Tamanho do arquivo: " + file.getSize() + " bytes");

                String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
                InputStream inputStream = file.getInputStream();

                int availableBytes = inputStream.available();
                System.out.println("Bytes disponíveis no InputStream: " + availableBytes);

                PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName)
                        .object(fileName).stream(inputStream, file.getSize(), 5242880) // 5MB buffer
                        .contentType(file.getContentType()).build();
                minioClient.putObject(putObjectArgs);

                FotoPessoa fotoPessoa = new FotoPessoa();
                fotoPessoa.setPessoa(pessoa);
                fotoPessoa.setFpData(LocalDateTime.now().toLocalDate());
                fotoPessoa.setFpBucket(bucketName);
                fotoPessoa.setFpHash(fileName);
                fotoPessoaRepository.save(fotoPessoa);

                uploadedFiles.add(fileName);
                inputStream.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload dos arquivos para o MinIO", e);
        }
        return uploadedFiles;
    }

    public List<String> getFotoLinks(Long pesId) {
        List<FotoPessoa> fotos = fotoPessoaRepository.findByPessoaPesId(pesId);
        List<String> links = new ArrayList<>();
        try {
            for (FotoPessoa foto : fotos) {
                String url = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName)
                                .object(foto.getFpHash()).expiry(5, TimeUnit.MINUTES).build());
                links.add(url);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar links para o MinIO", e);
        }
        return links;
    }
}
