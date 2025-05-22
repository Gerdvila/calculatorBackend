package com.leasing.calculator.service;

import com.leasing.calculator.api.model.exceptions.ApplicationNotFoundException;
import com.leasing.calculator.api.model.request.aggregates.MailRequest;
import com.leasing.calculator.api.model.request.primitives.enums.MailTemplateTypeRequest;
import com.leasing.calculator.api.model.response.MailResponse;
import com.leasing.calculator.api.model.response.MailTemplateResponse;
import com.leasing.calculator.domain.aggregates.request.MailRequestDO;
import com.leasing.calculator.domain.aggregates.response.LeaseAndRatesResponseDO;
import com.leasing.calculator.domain.aggregates.response.MailResponseDO;
import com.leasing.calculator.domain.aggregates.response.PersonalInformationResponseDO;
import com.leasing.calculator.repository.LeaseAndRatesRepositoryDAO;
import com.leasing.calculator.repository.MailRepositoryDAO;
import com.leasing.calculator.repository.PersonalInformationRepositoryDAO;
import com.leasing.calculator.util.MailUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender javaMailSender;
    private final LeaseAndRatesRepositoryDAO leaseAndRatesRepository;
    private final PersonalInformationRepositoryDAO personalInformationRepository;
    private final MailRepositoryDAO mailRepository;

    @Autowired
    public MailService(JavaMailSender javaMailSender, MailRepositoryDAO mailRepository, LeaseAndRatesRepositoryDAO leaseAndRatesRepository,
                       PersonalInformationRepositoryDAO personalInformationRepository) {
        this.javaMailSender = javaMailSender;
        this.mailRepository = mailRepository;
        this.leaseAndRatesRepository = leaseAndRatesRepository;
        this.personalInformationRepository = personalInformationRepository;
    }

    @Async
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    public MailTemplateResponse getMailTemplate(String applicationId, MailTemplateTypeRequest mailTemplateTypeRequest) {

        Optional<LeaseAndRatesResponseDO> tempLeaseAndRatesResponseDO = leaseAndRatesRepository.getLeaseAndRateById(applicationId);
        Optional<PersonalInformationResponseDO> tempPersonalInformationResponseDO = personalInformationRepository.getPersonalInformationById(applicationId);

        if (tempLeaseAndRatesResponseDO.isPresent() && tempPersonalInformationResponseDO.isPresent()) {
            LeaseAndRatesResponseDO leaseAndRatesResponseDO = tempLeaseAndRatesResponseDO.get();
            PersonalInformationResponseDO personalInformationResponseDO = tempPersonalInformationResponseDO.get();
            return MailUtil.getMailBody(leaseAndRatesResponseDO, personalInformationResponseDO, mailTemplateTypeRequest, applicationId);
        }
        throw new ApplicationNotFoundException(applicationId);
    }

    public void sendMailWithAttachment(String toEmail,
                                       String body,
                                       String subject,
                                       byte[] attachment) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        InputStreamSource attachmentSource = new ByteArrayResource(attachment);
        mimeMessageHelper.addAttachment("poster.pdf", attachmentSource);
        javaMailSender.send(mimeMessage);

    }

    public void saveMailHistory(MailRequest mailRequest) {
        if (mailRequest == null || mailRequest.mailText() == null || mailRequest.mailText().isEmpty()) {
            throw new IllegalArgumentException("Mail request must not be null");
        }
        try {
            sendMail(mailRequest.mailRecipient(), mailRequest.mailSubject(), mailRequest.mailText());
            mailRepository.createMail(convertMailRequestIntoMailDAORequest(mailRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MailResponse> getMailByApplicationId(String applicationId) {
        return mailRepository.selectMailByApplicationId(applicationId).stream()
                .map(this::convertMailDAOResponseIntoMailResponse)
                .toList();
    }

    private MailRequestDO convertMailRequestIntoMailDAORequest(MailRequest mailRequest) {
        String uuid = UUID.randomUUID().toString();
        return new MailRequestDO(
                uuid,
                mailRequest.applicationId(),
                mailRequest.mailText()
        );
    }

    private MailResponse convertMailDAOResponseIntoMailResponse(MailResponseDO mail) {
        return new MailResponse(
                mail.mailText()
        );
    }
}