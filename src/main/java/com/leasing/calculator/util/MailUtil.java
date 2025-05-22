package com.leasing.calculator.util;

import com.leasing.calculator.api.model.request.primitives.enums.MailTemplateTypeRequest;
import com.leasing.calculator.api.model.response.MailTemplateResponse;
import com.leasing.calculator.domain.aggregates.response.LeaseAndRatesResponseDO;
import com.leasing.calculator.domain.aggregates.response.PersonalInformationResponseDO;

public class MailUtil {

    private static final String COMPANY_NAME = "Car Leasing";
    private static final String PHONE_NUMBER = "+37064206969";
    private static final String EMAIL = "autolizingas@gmail.com";

    private MailUtil() {}

    public static MailTemplateResponse getMailBody(
            LeaseAndRatesResponseDO leaseAndRatesResponse,
            PersonalInformationResponseDO personalInformationResponse,
            MailTemplateTypeRequest templateType,
            String applicationId
    ) {
        return switch (templateType) {
            case ACCEPTED -> getAcceptedTemplate(leaseAndRatesResponse, personalInformationResponse, applicationId);
            case REJECTED -> getRejectedTemplate(leaseAndRatesResponse, personalInformationResponse, applicationId);
            case PENDING  -> getPendingTemplate(leaseAndRatesResponse, personalInformationResponse, applicationId);
            case CANCELLED -> getCancelledTemplate(leaseAndRatesResponse, personalInformationResponse, applicationId);
        };
    }

    private static MailTemplateResponse getAcceptedTemplate(
            LeaseAndRatesResponseDO lease,
            PersonalInformationResponseDO person,
            String applicationId
    ) {
        String applicantName = safe(person.firstName());
        String carMake = safe(lease.make());
        String carModel = safe(lease.model());
        String carYear = safe(lease.year());
        String carPrice = safe(lease.carValue().toString());

        String body = """
    <p>Car Leasing Application #%s</p>
    <p></p>
    <p>Dear %s,</p>
    <p></p>
    <p>We are pleased to inform you that your car leasing application #%s has been confirmed!</p>
    <p></p>
    <p><strong>Vehicle details:</strong> <em>%s %s (%s)</em><br>
    Price: %s Eur</p>
    <p></p>
    <p>We understand the importance of selecting the right vehicle and leasing terms, and we are committed to providing you with the best possible leasing options tailored to your needs.</p>
    <p></p>
    <p>Please find attached the payment plan and contract for car leasing. The contract can be signed by digital signing methods only (e.g., mobile signature or Smart-ID). Make sure to read it carefully, sign, and send the signed agreement to us by replying to this email.</p>
    <p></p>
    <p>In the meantime, if you need any assistance or have any concerns, please feel free to contact us at %s or %s.</p>
    <p></p>
    <p>Thank you for choosing %s for your car leasing needs. We look forward to helping you get behind the wheel of your dream car!</p>
    <p></p>
    <p>Best regards,<br>
    %s<br>
    %s<br>
    %s</p>
    <hr>
    <p>Gerb. %s,</p>
    <p></p>
    <p>Džiaugiamės galėdami pranešti, kad Jūsų pateikta automobilio lizingo paraiška #%s patvirtinta!</p>
    <p></p>
    <p><strong>Automobilio duomenys:</strong> <em>%s %s (%s)</em><br>
    Kaina: %s Eur</p>
    <p></p>
    <p>Suprantame tinkamo automobilio ir lizingavimo sąlygų pasirinkimo svarbą, todėl norime pateikti geriausias, Jūsų poreikius atitinkančias lizingo sąlygas.</p>
    <p></p>
    <p>Netrukus gausite ir automobilio lizingo sutartį. Sutartį galima pasirašyti tik elektroninėmis priemonėmis (m. parašu, Smart-ID). Atidžiai susipažinkite su sutarties sąlygomis, pasirašykite ją ir atsiųskite atsakyme į šį el. laišką.</p>
    <p></p>
    <p>Jei kiltų klausimų ar reikėtų pagalbos, mielai jums padėsime! Susisiekite su mumis telefonu %s ar el.paštu %s. </p>
    <p></p>
    <p>Dėkojame, kad renkatės „%s“ ir tikimės, kad padėsime Jums jau greitai atsidurti už savo svajonių automobilio vairo!</p>
    <p></p>
    <p>Pagarbiai<br>
    „%s“ <br>
    %s <br>
    %s</p>
    """.formatted(
                applicationId, applicantName, applicationId, carMake, carModel, carYear, carPrice,
                PHONE_NUMBER, EMAIL, COMPANY_NAME,
                COMPANY_NAME, PHONE_NUMBER, EMAIL,
                applicantName, applicationId, carMake, carModel, carYear, carPrice,
                PHONE_NUMBER, EMAIL, COMPANY_NAME,
                COMPANY_NAME, PHONE_NUMBER, EMAIL
        );
        return new MailTemplateResponse(body);
    }

    private static MailTemplateResponse getRejectedTemplate(
            LeaseAndRatesResponseDO lease,
            PersonalInformationResponseDO person,
            String applicationId
    ) {
        String applicantName = safe(person.firstName());
        String carMake = safe(lease.make());
        String carModel = safe(lease.model());
        String carYear = safe(lease.year());
        String carPrice = safe(lease.carValue().toString());

        String body = """
    <p>Car Leasing Application #%s</p>
    <p></p>
    <p>Dear %s,</p>
    <p></p>
    <p>We regret to inform you that after careful review, your car leasing application #%s has been rejected.</p>
    <p></p>
    <p><strong>Vehicle details:</strong> <em>%s %s (%s)</em><br>
    Price: %s Eur</p>
    <p></p>
    <p>We understand that this news may be disappointing, and we sincerely apologize for any inconvenience caused. Unfortunately, based on our current criteria and availability, we are unable to proceed with your application at this time.</p>
    <p></p>
    <p>If you have any questions regarding the decision or would like further clarification, please do not hesitate to reach out to us at %s or %s. We are here to assist you in any way we can.</p>
    <p></p>
    <p>We appreciate your interest in leasing with %s, and we encourage you to consider us for any future leasing needs you may have.</p>
    <p></p>
    <p>Thank you for your understanding.</p>
    <p></p>
    <p>Best regards,<br>
    %s <br>
    %s <br>
    %s</p>
    <hr>
    <p>Gerb. %s,</p>
    <p></p>
    <p>Apgailestaujame turėdami pranešti, kad išsamiai apsvarstę Jūsų pateiktą automobilio lizingo paraišką #%s nusprendėme ją atmesti.</p>
    <p></p>
    <p><strong>Automobilio duomenys:</strong> <em>%s %s (%s)</em><br>
    Kaina: %s Eur</p>
    <p></p>
    <p>Suprantame, kad ši žinia gali nuvilti ir atsiprašome už galimus nepatogumus. Deja, pagal dabartinius kriterijus ir galimybes negalime patenkinti Jūsų paraiškos.</p>
    <p></p>
    <p>Jei dėl šio sprendimo turite klausimų ar norėtumėte išsamesnio paaiškinimo, prašome su mumis susisiekti telefonu %s ar el. paštu %s. Mielai Jums padėsime! </p>
    <p></p>
    <p>Dėkojame, kad domitės „%s“ paslaugomis ir tikimės, kad jei ateityje vėl iškiltų automobilio lizingavimo poreikis, kreipsitės į mus.</p>
    <p></p>
    <p>Ačiū už Jūsų supratingumą!</p>
    <p></p>
    <p>Pagarbiai<br>
    „%s“ <br>
    %s <br>
    %s</p>
    """.formatted(
                applicationId, applicantName, applicationId, carMake, carModel, carYear, carPrice,
                PHONE_NUMBER, EMAIL, COMPANY_NAME,
                COMPANY_NAME, PHONE_NUMBER, EMAIL,
                applicantName, applicationId, carMake, carModel, carYear, carPrice,
                PHONE_NUMBER, EMAIL, COMPANY_NAME,
                COMPANY_NAME, PHONE_NUMBER, EMAIL
        );
        return new MailTemplateResponse(body);
    }

    private static MailTemplateResponse getPendingTemplate(
            LeaseAndRatesResponseDO lease,
            PersonalInformationResponseDO person,
            String applicationId
    ) {
        String applicantName = safe(person.firstName());

        String body = """
    <p>Car Leasing Application #%s</p>
    <p></p>
    <p>Dear %s,</p>
    <p></p>
    <p>We hope this email finds you well.</p>
    <p></p>
    <p>We are currently in the process of reviewing your car leasing application #%s, and we require some clarification on certain details to proceed further.</p>
    <p></p>
    <p>Could you please provide additional information or clarify the following details:<br>
    <strong>*please write your question here *</strong></p>
    <p></p>
    <p>Your prompt response would greatly assist us in expediting the processing of your application. If you have any questions or need further clarification on the requested details, please do not hesitate to reach out to us at %s or %s.</p>
    <p></p>
    <p>Thank you for your cooperation and understanding. We look forward to hearing back from you soon.</p>
    <p></p>
    <p>Best regards,<br>
    %s<br>
    %s<br>
    %s</p>
    <hr>
    <p>Gerb. %s,</p>
    <p></p>
    <p>Dėkojame, kad kreipiatės į „%s“ dėl automobilio lizingo!</p>
    <p></p>
    <p>Šiuo metu peržiūrime Jūsų paraišką #%s ir, kad tęstume paraiškos svarstymą, mums reikia papildomos informacijos.</p>
    <p></p>
    <p>Prašome pateikti informaciją apie toliau išvardintus klausimus:</p>
    <p></p>
    <p>*Įrašyti klausimus*</p>
    <p></p>
    <p>Greitas atsakymas labai padėtų mums paspartinti Jūsų paraiškos svarstymą! Jei turite klausimų ar norėtumėte aptarti informaciją, kurios prašome, susisiekite su mumis telefonu %s ar el. paštu %s.</p>
    <p></p>
    <p>Dėkojame už bendradarbiavimą!</p>
    <p></p>
    <p>Pagarbiai<br>
    „%s“ <br>
    %s <br>
    %s</p>
    """.formatted(
                applicationId, applicantName, applicationId,
                PHONE_NUMBER, EMAIL, COMPANY_NAME, PHONE_NUMBER, EMAIL,
                applicantName, COMPANY_NAME, applicationId,
                PHONE_NUMBER, EMAIL, COMPANY_NAME, PHONE_NUMBER, EMAIL
        );
        return new MailTemplateResponse(body);
    }

    private static MailTemplateResponse getCancelledTemplate(
            LeaseAndRatesResponseDO lease,
            PersonalInformationResponseDO person,
            String applicationId
    ) {
        String applicantName = safe(person.firstName());
        String carMake = safe(lease.make());
        String carModel = safe(lease.model());
        String carYear = safe(lease.year());
        String carPrice = safe(lease.carValue().toString());

        String body = """
    <p>Car Leasing Application #%s</p>
    <p></p>
    <p>Dear %s,</p>
    <p></p>
    <p>We hope this email finds you well.</p>
    <p></p>
    <p>We regret to inform you that your car leasing application #%s has been canceled.</p>
    <p></p>
    <p><strong>Vehicle details:</strong> <em>%s %s (%s)</em><br>
    Price: %s Eur</p>
    <p></p>
    <p>Unfortunately, due to unforeseen circumstances, we have had to cancel your application. We understand that this may be disappointing news, and we apologize for any inconvenience this may cause.</p>
    <p></p>
    <p>If you have any questions about the cancellation or would like further information, please feel free to reach out to us at %s or %s. Our team is here to assist you and address any concerns you may have.</p>
    <p></p>
    <p>We appreciate your understanding and patience in this matter. Should you wish to reapply in the future, we would be more than happy to assist you with your car leasing needs.</p>
    <p></p>
    <p>Thank you for considering %s.</p>
    <p></p>
    <p>Best regards,<br>
    %s<br>
    %s<br>
    %s</p>
    <hr>
    <p>Gerb. %s,</p>
    <p></p>
    <p>Dėkojame, kad kreipiatės į „%s“</p>
    <p></p>
    <p>Apgailestaujame turėdami pranešti, kad Jūsų automobilio lizingo paraiška #%s atšaukta.</p>
    <p></p>
    <p><strong>Automobilio informacija:</strong> <em>%s %s (%s)</em><br>
    Kaina: %s Eur</p>
    <p></p>
    <p>Dėja dėl nenumatytų aplinkybių turėjome atšaukti Jūsų paraišką. Suprantame, kad ši žinia gali nuvilti ir atsiprašome dėl galimų nepatogumų</p>
    <p></p>
    <p>Jei turite klausimų dėl atšaukimo ar norėtumėte gauti daugiau informacijos, prašome susisiekti su mumis telefonu %s ar el. paštu %s. Mūsų komanda mielai atsakys į Jums rūpimus klausimus.</p>
    <p></p>
    <p>Dėkojame už Jūsų supratingumą! Jei ateityje nuspręstumėte vėl kreiptis dėl automobilio lizingo, mielai padėsime Jums surasti geriausią sprendimą.</p>
    <p></p>
    <p>Pagarbiai</p>
    <p></p>
    „%s“<br>
    %s<br>
    %s</p>
    """.formatted(
                applicationId, applicantName, applicationId, carMake, carModel, carYear, carPrice,
                PHONE_NUMBER, EMAIL, COMPANY_NAME,
                COMPANY_NAME, PHONE_NUMBER, EMAIL,
                applicantName, COMPANY_NAME, applicationId, carMake, carModel, carYear, carPrice,
                PHONE_NUMBER, EMAIL, COMPANY_NAME, PHONE_NUMBER, EMAIL
        );
        return new MailTemplateResponse(body);
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }
}