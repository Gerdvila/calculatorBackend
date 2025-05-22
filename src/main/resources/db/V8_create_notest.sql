CREATE TABLE notes
(
    id VARCHAR(100) PRIMARY KEY,
    application_id VARCHAR(100) NOT NULL,
    note_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_application_id FOREIGN KEY (application_id) REFERENCES personal_information(id)
);