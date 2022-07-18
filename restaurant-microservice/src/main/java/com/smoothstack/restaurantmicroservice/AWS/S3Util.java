package com.smoothstack.restaurantmicroservice.AWS;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.restaurantmicroservice.service.ConfigService;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;


// Adapted from https://www.codejava.net/aws/upload-file-to-s3-spring-boot
@Service
public class S3Util {

    @Autowired
    ConfigService config;

    AwsBasicCredentials credentials = AwsBasicCredentials.create(config.getAwsAccessKeyId(), config.getAwsSecretAccessKey());

    private static final String BUCKET = "restaurant-image-storage";
    private static final Region DEFAULT_REGION = Region.US_EAST_1;
     
    public void uploadFile(String fileName, InputStream inputStream) 
    throws S3Exception, AwsServiceException, SdkClientException, IOException {

        S3Client client = S3Client
                            .builder()
                            .region(DEFAULT_REGION)
                            .credentialsProvider(StaticCredentialsProvider.create(credentials))
                            .build();
         
        PutObjectRequest request = PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(fileName)
                            .build();
         
        PutObjectResponse response = client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));

        /*S3Waiter waiter = client.waiter();
        
        HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(fileName)
                            .build();
            
        WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);
            
        waitResponse.matched().response().ifPresent(x -> {
            // run custom code that should be executed after the upload file exists
            return waitResponse.
        });*/
    }
}
