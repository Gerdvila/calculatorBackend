CREATE TABLE MAIL
(
    id VARCHAR(100) PRIMARY KEY,
    application_id VARCHAR(100) NOT NULL,
    mail_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_application_id FOREIGN KEY (application_id) REFERENCES personal_information(id)
);