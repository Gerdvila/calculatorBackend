package com.leasing.calculator.api.controller;

import com.leasing.calculator.api.model.request.aggregates.MailRequest;
import com.leasing.calculator.api.model.request.primitives.enums.MailTemplateTypeRequest;
import com.leasing.calculator.api.model.response.MailTemplateResponse;
import com.leasing.calculator.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public String sendEmail(@PathVariable String email) {
        try {
            mailService.sendMail(email, "TLizingas Loan", """
                    Hey there!,

                    Thank you for using the TLizingas loan calculator!
                    We've successfully received your application.

                    TLizingas staff will get in touch with you shortly!

                    Have a great day!
                    TLizingas Team
                    """);
            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }

    @PostMapping("/mail/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveEmail(@RequestBody MailRequest email){
        mailService.saveMailHistory(email);
    }

    @GetMapping("/mail/{applicationId}")
    @ResponseStatus(HttpStatus.OK)
    public String getMailByApplicationId(@PathVariable String applicationId) {
        return mailService.getMailByApplicationId(applicationId).toString();
    }

    @GetMapping("/mail/{applicationId}/template/")
    @ResponseStatus(HttpStatus.OK)
    public MailTemplateResponse getMailTemplate(@PathVariable String applicationId,
                                                @RequestParam(value = "mailTemplateType") MailTemplateTypeRequest mailTemplateTypeRequest) {
        return mailService.getMailTemplate(applicationId, mailTemplateTypeRequest);
    }
}