CREATE TABLE INTEREST_RATE (
                               id BIGSERIAL PRIMARY KEY,
                               regular DECIMAL(10,2) NOT NULL,
                               eco DECIMAL(10,2) NOT NULL
);

INSERT INTO INTEREST_RATE (regular, eco) VALUES (4.0, 3.2);