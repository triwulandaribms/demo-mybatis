
CREATE SEQUENCE post_number_seq START 1;


CREATE TABLE posts (

    id SERIAL PRIMARY KEY,
    number_post INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(10) NOT NULL,
    password VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    views INT NOT NULL DEFAULT 0,
    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NULL,
    deletedAt TIMESTAMP NULL
    
);



