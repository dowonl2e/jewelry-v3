package com.jewelry.file;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.jewelry.file.model.AmazonS3Service;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private AmazonS3Service amazonS3Service;

	@Value("${cloud.aws.s3.bucket.name}")
	private String bucket;

	@Value("${cloud.aws.s3.path}")
	private String s3Path;

	@GetMapping("/image/display")
	public ResponseEntity<byte[]> displayImage(
			@RequestParam(value = "filePath", required = false) String filePath,
			@RequestParam(value = "fileName", required = false) String fileName) throws IOException {
		
		if(filePath == null || filePath.equals("") || fileName == null || fileName.equals("")) {
			filePath = "common";
			fileName = "no-image.png";
		}

    	S3Object object = amazonS3Service.download(filePath, fileName);

    	S3ObjectInputStream inputStream = object.getObjectContent();
    	
    	byte[] bytes = IOUtils.toByteArray(inputStream);
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    	
    	httpHeaders.setContentDispositionFormData("attachment", fileName);
    	
    	return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }
	
}
