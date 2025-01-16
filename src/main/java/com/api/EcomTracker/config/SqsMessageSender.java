package com.api.EcomTracker.config;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class SqsMessageSender {

    private final SqsClient sqsClient;
    private final String queueUrl = "https://sqs.us-east-1.amazonaws.com/851725458487/orders-queue";

    public SqsMessageSender(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage(String messageBody) {
        try {
            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .delaySeconds(0)
                    .build();

            sqsClient.sendMessage(sendMsgRequest);

            System.out.println("Mensagem enviada ao SQS com sucesso: " + messageBody);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Falha ao enviar mensagem para o SQS: " + e.getMessage());
        }
    }
}
