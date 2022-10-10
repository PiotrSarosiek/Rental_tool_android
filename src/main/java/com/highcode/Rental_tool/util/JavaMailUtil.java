package com.highcode.Rental_tool.util;

import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.web.exception.ExceptionType;
import com.highcode.Rental_tool.web.exception.RentalToolException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JavaMailUtil {

    private final JavaMailSender javaMailSender;

    public void sendSummaryMail(User user, String fileName, byte[] bytes){
        ByteArrayResource bar = new ByteArrayResource(bytes);
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject("Rent summary");
            mimeMessageHelper.setText(prepareMessageText(user));
            mimeMessageHelper.addAttachment(fileName, bar);
            javaMailSender.send(message);
        }
        catch (Exception e){
            log.info(String.format("Sending summary mail to %s failed", user.getEmail()), e);
            throw new RentalToolException(ExceptionType.SENDING_MAIL_FAILED, HttpStatus.I_AM_A_TEAPOT);
        }
    }

    private String prepareMessageText(User user){
        return "Hi " + user.getName() + " " + user.getSurname() + ".\n" +
                "In the attachment you will find a summary for " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".";
    }
}
