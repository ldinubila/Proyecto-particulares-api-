CREATE TABLE Token(
    tokenID serial NOT NULL PRIMARY KEY UNIQUE,
    authenticationToken varchar(255) NULL,
    emailId varchar(255) NULL,
    secretKey varchar(255) NULL,
    userID bigint NULL
);