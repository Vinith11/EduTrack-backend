package com.vini.backend.service.email;

import com.vini.backend.models.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    @Async
    public CompletableFuture<Void> sendApprovalRequestEmail(String toEmail, Project project) {

        return CompletableFuture.runAsync(() -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(toEmail);
                message.setSubject("Project Approval Request: " + project.getStudentProjectName());
                message.setText("Dear Faculty,\n\n" +
                        "You have been assigned as a guide for the following project.\n\n" +
                        "Project Name: " + project.getStudentProjectName() + "\n" +
                        "Project Leader: " + project.getStudentProjectLeaderId() + "\n" +
                        "Team Members: " + String.join(", ", project.getTeamMembers()) + "\n" +
                        "Project Description: " + project.getStudentProjectDescription() + "\n\n" +
                        "Please visit the project approval section on the website to approve or reject this project.\n\n" +
                        "Thank you!");

                mailSender.send(message);
            } catch (MailException e) {
                // Log the error and handle appropriately
                throw new RuntimeException("Failed to send email", e);
            }
        });

    }

    @Override
    @Async
    public CompletableFuture<Void> sendRejectionEmail(String toEmail, Project project) {
        return CompletableFuture.runAsync(() -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(toEmail);
                message.setSubject("Project Rejected: " + project.getStudentProjectName());
                message.setText("Dear Student,\n\n" +
                        "Your project has been rejected by the faculty.\n\n" +
                        "Project Name: " + project.getStudentProjectName() + "\n" +
                        "Project Leader: " + project.getStudentProjectLeaderId() + "\n" +
                        "Team Members: " + String.join(", ", project.getTeamMembers()) + "\n" +
                        "Project Description: " + project.getStudentProjectDescription() + "\n\n" +
                        "Please contact your faculty for more details.\n\n" +
                        "Thank you!");

                mailSender.send(message);
            } catch (MailException e) {
                // Log the error and handle appropriately
                throw new RuntimeException("Failed to send email", e);
            }
        });
    }

    @Override
    @Async
    public CompletableFuture<Void> sendAcceptEmail(String toEmail, Project project) {
        return CompletableFuture.runAsync(() -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(toEmail);
                message.setSubject("Project Approved: " + project.getStudentProjectName());
                message.setText("Dear Student,\n\n" +
                        "Your project has been approved by the faculty.\n\n" +
                        "Project Name: " + project.getStudentProjectName() + "\n" +
                        "Project Leader: " + project.getStudentProjectLeaderId() + "\n" +
                        "Team Members: " + String.join(", ", project.getTeamMembers()) + "\n" +
                        "Project Description: " + project.getStudentProjectDescription() + "\n\n" +
                        "You can now start working on your project.\n\n" +
                        "Thank you!");

                mailSender.send(message);
            } catch (MailException e) {
                // Log the error and handle appropriately
                throw new RuntimeException("Failed to send email", e);
            }
        });

    }
}



