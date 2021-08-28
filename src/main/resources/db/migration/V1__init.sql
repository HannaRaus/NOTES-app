CREATE TABLE users
(
    user_id BINARY(16) PRIMARY KEY,
    user_name varchar(255) UNIQUE NOT NULL,
    user_password varchar(255) NOT NULL,
    first_name varchar(255),
    last_name varchar(255),
    user_status varchar(50) NOT NULL
);

CREATE TABLE notifications
(
    notification_id BINARY(16) PRIMARY KEY,
    title varchar(100) UNIQUE NOT NULL,
    description varchar(10000) NOT NULL,
    access_type ENUM ('public','private'),
    user_name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_name)
        REFERENCES users(user_name)
);