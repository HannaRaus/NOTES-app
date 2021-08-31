CREATE TABLE users
(
    id UUID PRIMARY KEY,
    name varchar(100) UNIQUE NOT NULL,
    password varchar(200) NOT NULL,
    role varchar(100) NOT NULL
);

CREATE TABLE notes
(
    id UUID PRIMARY KEY,
    title varchar(200) UNIQUE NOT NULL,
    content varchar(10100)      NOT NULL,
    access_type varchar(100) NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
/*



CREATE TABLE users
(
    id BINARY(16) PRIMARY KEY,
    name varchar(100) UNIQUE NOT NULL,
    password varchar(200) NOT NULL,
    role varchar(100) NOT NULL
);

CREATE TABLE notes
(
    id BINARY(16) PRIMARY KEY,
    title varchar(200) UNIQUE NOT NULL,
    content varchar(10100)      NOT NULL,
    access_type varchar(100) NOT NULL,
    user_id BINARY(16) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
*/
