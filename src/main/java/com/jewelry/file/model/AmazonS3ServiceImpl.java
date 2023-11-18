package com.jewelry.file.model;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.jewelry.file.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {
  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket.name}")
  private String bucketName;

  @Override
  public FileDto uploadFile(MultipartFile file, String path, String refInfo) throws IOException, SdkClientException, AmazonServiceException {
    FileDto fileto = new FileDto();
    if(file != null) {
      ObjectMetadata objectMetadata = new ObjectMetadata();

      Map<String, String> metadata = new HashMap<>();
      metadata.put("Content-Type", file.getContentType());
      metadata.put("Content-Length", String.valueOf(file.getSize()));

      String bucketpath = String.format("%s/%s", bucketName, path);
      String originalname = String.format("%s", file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
      String filename = String.format("%s", UUID.randomUUID());
      String fileext = originalname.substring(originalname.lastIndexOf(".") + 1);

      if(!ObjectUtils.isEmpty(originalname)) {
        Optional.of(metadata).ifPresent(map -> {
          if (!map.isEmpty()) {
            map.forEach(objectMetadata::addUserMetadata);
          }
        });

        // Uploading file to s3
        PutObjectResult putObjectResult = amazonS3Client.putObject(bucketpath, filename, file.getInputStream(), objectMetadata);

        fileto.setRefInfo(refInfo);
        fileto.setFilePath(path);
        fileto.setOriginNm(originalname);
        fileto.setFileNm(filename);
        fileto.setFileExt(fileext);
        fileto.setFileOrd(1);
        fileto.setFileSize(file.getSize());
        if(putObjectResult != null && putObjectResult.getMetadata() != null)
          fileto.setVersionId(putObjectResult.getMetadata().getVersionId());
      }
    }
    return fileto;
  }

  @Override
  public S3Object download(String path, String fileName) {
    String bucketpath = String.format("%s/%s", bucketName, path);
    return amazonS3Client.getObject(bucketpath, fileName);
  }
}
