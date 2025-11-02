//package com.nisal.moneymanager.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//
//    @Value("${spring.mail.properties.mail.smtp.from}")
//    private String fromEmail;
//
//    public void sendEmail(String to, String subject, String body) {
//
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(fromEmail);
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(body);
//            mailSender.send(message);
//        }catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }
//
//}


package com.nisal.moneymanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final RestTemplate restTemplate;

    // Load environment variables
//    private final String apiKey = ${BREVO_API_KEY}
//    private final String fromEmail = System.getenv("BREVO_FROM_EMAIL");
//    private final String fromName = System.getenv("BREVO_FROM_NAME");

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    @Value("${BREVO_FROM_EMAIL}")
    private String fromEmail;

    @Value("${BREVO_FROM_NAME}")
    private String fromName;

    /**
     * Send email using Brevo API (HTTPS)
     * @param to recipient email
     * @param subject email subject
     * @param htmlContent HTML body
     */
    public void sendEmail(String to, String subject, String htmlContent) {
        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        String body = "{"
                + "\"sender\":{\"email\":\"" + fromEmail + "\",\"name\":\"" + fromName + "\"},"
                + "\"to\":[{\"email\":\"" + to + "\"}],"
                + "\"subject\":\"" + subject + "\","
                + "\"htmlContent\":\"" + htmlContent + "\""
                + "}";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Email sent! Status: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            // Optional: don't throw, allow registration to succeed
        }
    }


    /**
     * Send HTML email with an Excel file attachment
     */
    public void sendIncomeExcelWithAttachment(String to, String subject, String htmlContent, byte[] excelData) {
        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        String base64Excel = Base64.getEncoder().encodeToString(excelData);

        String body = "{"
                + "\"sender\":{\"email\":\"" + fromEmail + "\",\"name\":\"" + fromName + "\"},"
                + "\"to\":[{\"email\":\"" + to + "\"}],"
                + "\"subject\":\"" + subject + "\","
                + "\"htmlContent\":\"" + htmlContent + "\","
                + "\"attachment\":[{"
                + "\"content\":\"" + base64Excel + "\","
                + "\"name\":\"income_details.xlsx\""
                + "}]"
                + "}";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }

    /**
     * Send HTML email with an Excel file attachment
     */
    public void sendExpenseExcelWithAttachment(String to, String subject, String htmlContent, byte[] excelData) {
        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        String base64Excel = Base64.getEncoder().encodeToString(excelData);

        String body = "{"
                + "\"sender\":{\"email\":\"" + fromEmail + "\",\"name\":\"" + fromName + "\"},"
                + "\"to\":[{\"email\":\"" + to + "\"}],"
                + "\"subject\":\"" + subject + "\","
                + "\"htmlContent\":\"" + htmlContent + "\","
                + "\"attachment\":[{"
                + "\"content\":\"" + base64Excel + "\","
                + "\"name\":\"expense_details.xlsx\""
                + "}]"
                + "}";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}
