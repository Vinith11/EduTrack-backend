package com.vini.backend.service.email;

import com.vini.backend.models.project.Project;

public interface EmailService {
    void sendApprovalRequestEmail(String toEmail, Project project);
    void sendRejectionEmail(String toEmail, Project project);
    void sendAcceptEmail(String toEmail, Project project);

}

