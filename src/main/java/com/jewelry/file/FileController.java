package com.jewelry.file;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.jewelry.file.model.AmazonS3Service;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private AmazonS3Service amazonS3Service;

	@Value("${cloud.aws.s3.bucket.name}")
	private String bucket;

	@GetMapping("/image/display")
	public ResponseEntity<byte[]> displayImage(
			@RequestParam(value = "filePath", required = false) String filePath,
			@RequestParam(value = "fileName", required = false) String fileName) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		if(filePath == null || filePath.equals("") || fileName == null || fileName.equals("")) {
			return validDisplayImage("common" , "no-image.png", httpHeaders);
		}
		try {
			S3Object object = amazonS3Service.download(filePath, fileName);
			S3ObjectInputStream inputStream = object.getObjectContent();
			byte[] bytes = IOUtils.toByteArray(inputStream);

			httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			httpHeaders.setContentDispositionFormData("attachment", fileName);

			return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
		}
		catch (IOException ioe){
			return validDisplayImage("common" , "no-image.png", httpHeaders);
		}
		catch (Exception e){
			return validDisplayImage("common" , "no-image.png", httpHeaders);
		}
	}

	private ResponseEntity<byte[]> validDisplayImage(String filePath, String fileName, HttpHeaders httpHeaders) throws IOException, Exception {
		S3Object object = amazonS3Service.download(filePath, fileName);
		S3ObjectInputStream inputStream = object.getObjectContent();
		byte[] bytes = IOUtils.toByteArray(inputStream);

		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		httpHeaders.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}
}
