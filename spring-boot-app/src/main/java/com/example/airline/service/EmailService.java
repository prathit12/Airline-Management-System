package com.example.airline.service;

import com.example.airline.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Value("${app.name:Airline Management System}")
    private String appName;

    public void sendEmailVerification(User user) {
        // In a real implementation, you would use a mail service like JavaMail, SendGrid, etc.
        // For now, we'll just log the email content
        
        String verificationUrl = frontendUrl + "/verify-email?token=" + generateVerificationToken(user);
        String emailContent = buildEmailVerificationContent(user, verificationUrl);
        
        // Log for demonstration purposes
        System.out.println("=== EMAIL VERIFICATION ===");
        System.out.println("To: " + user.getEmail());
        System.out.println("Subject: Verify Your Email - " + appName);
        System.out.println("Content:\n" + emailContent);
        System.out.println("=== END EMAIL ===");
        
        // TODO: Integrate with actual email service (SendGrid, AWS SES, etc.)
        // sendActualEmail(user.getEmail(), "Verify Your Email - " + appName, emailContent);
    }

    public void sendPasswordResetEmail(String email, String resetToken) {
        String resetUrl = frontendUrl + "/reset-password?token=" + resetToken;
        String emailContent = buildPasswordResetContent(email, resetUrl);
        
        // Log for demonstration purposes
        System.out.println("=== PASSWORD RESET EMAIL ===");
        System.out.println("To: " + email);
        System.out.println("Subject: Password Reset Request - " + appName);
        System.out.println("Content:\n" + emailContent);
        System.out.println("=== END EMAIL ===");
        
        // TODO: Integrate with actual email service
        // sendActualEmail(email, "Password Reset Request - " + appName, emailContent);
    }

    public void sendWelcomeEmail(User user) {
        String emailContent = buildWelcomeContent(user);
        
        // Log for demonstration purposes
        System.out.println("=== WELCOME EMAIL ===");
        System.out.println("To: " + user.getEmail());
        System.out.println("Subject: Welcome to " + appName);
        System.out.println("Content:\n" + emailContent);
        System.out.println("=== END EMAIL ===");
        
        // TODO: Integrate with actual email service
        // sendActualEmail(user.getEmail(), "Welcome to " + appName, emailContent);
    }

    private String generateVerificationToken(User user) {
        // In a real implementation, you would generate a secure token
        // and store it in the database with expiration time
        return "verification_token_" + user.getId() + "_" + System.currentTimeMillis();
    }

    private String buildEmailVerificationContent(User user, String verificationUrl) {
        return String.format("""
            Hello %s,
            
            Welcome to %s! To complete your registration, please verify your email address by clicking the link below:
            
            %s
            
            This link will expire in 24 hours. If you did not create an account, please ignore this email.
            
            Best regards,
            The %s Team
            """, 
            user.getFirstName() != null ? user.getFirstName() : user.getUsername(),
            appName,
            verificationUrl,
            appName
        );
    }

    private String buildPasswordResetContent(String email, String resetUrl) {
        return String.format("""
            Hello,
            
            You have requested to reset your password for your %s account.
            
            Please click the link below to reset your password:
            
            %s
            
            This link will expire in 24 hours. If you did not request a password reset, please ignore this email.
            
            Best regards,
            The %s Team
            """,
            appName,
            resetUrl,
            appName
        );
    }

    private String buildWelcomeContent(User user) {
        return String.format("""
            Hello %s,
            
            Welcome to %s! Your account has been successfully created.
            
            You can now log in to your account and start using our services.
            
            If you have any questions or need assistance, please don't hesitate to contact our support team.
            
            Best regards,
            The %s Team
            """,
            user.getFirstName() != null ? user.getFirstName() : user.getUsername(),
            appName,
            appName
        );
    }

    // TODO: Implement actual email sending
    /*
    private void sendActualEmail(String to, String subject, String content) {
        // Example with JavaMail
        // MimeMessage message = mailSender.createMimeMessage();
        // MimeMessageHelper helper = new MimeMessageHelper(message, true);
        // helper.setFrom(fromEmail);
        // helper.setTo(to);
        // helper.setSubject(subject);
        // helper.setText(content, true);
        // mailSender.send(message);
    }
    */
}