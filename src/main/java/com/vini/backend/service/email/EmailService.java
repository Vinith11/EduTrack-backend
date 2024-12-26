package com.vini.backend.service.email;

import com.vini.backend.models.project.Project;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<Void> sendApprovalRequestEmail(String toEmail, Project project);
    CompletableFuture<Void> sendRejectionEmail(String toEmail, Project project);
    CompletableFuture<Void> sendAcceptEmail(String toEmail, Project project);

}

