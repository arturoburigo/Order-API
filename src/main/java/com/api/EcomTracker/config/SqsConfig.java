package com.api.EcomTracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SqsConfig {
    Dotenv dotenv = Dotenv.configure().directory("/app").load();


    String accessKeyId = dotenv.get("AWS_ACCESS_KEY_ID");
    String secretAccessKey = dotenv.get("AWS_SECRET_ACCESS_KEY");

    @Bean
    public SqsClient sqsClient() {
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)
                ))
                .build();

        System.out.println("SQS Client created successfully");
        return sqsClient;
    }

}
