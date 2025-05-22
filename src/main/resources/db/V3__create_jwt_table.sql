CREATE TABLE jwt_token (
                           id INT PRIMARY KEY,
                           jwt VARCHAR(600),
                           expires_at BIGINT
);