package com.jewelry.file.model;

import com.amazonaws.services.s3.model.S3Object;
import com.jewelry.file.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AmazonS3Service {

  FileDto uploadFile(MultipartFile files, String path, String refInfo) throws IOException;

  S3Object download(String path, String fileName);

}

