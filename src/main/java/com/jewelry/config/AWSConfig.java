package com.jewelry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@PropertySource("classpath:/application.yml") // 해당 클래스에서 참조할 properties 파일의 위치를 지정
public class AWSConfig {

	/**
	 * Key는 중요정보이기 때문에 properties 파일에 저장한 뒤 가져와 사용하는 방법이 좋습니다.
	 */
	
//	private String iamAccessKey = "IAM 생성할 때 확인했던 AccessKey 입력"; // IAM Access Key
//	private String iamSecretKey = "IAM 생성할 때 확인했던 SecretKey 입력"; // IAM Secret Key
	
	@Value("${cloud.aws.s3.iam.accesskey}")
	private String iamAccessKey;
	@Value("${cloud.aws.s3.iam.secretkey}")
	private String iamSecretKey;
	@Value("${cloud.aws.s3.bucket.region}")
	private String region; 
	
	@Bean
	public AmazonS3Client amazonS3Client() {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(iamAccessKey, iamSecretKey);
		return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                                                             .withRegion(region)
                                                             .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                                                             .build();
	}
}
