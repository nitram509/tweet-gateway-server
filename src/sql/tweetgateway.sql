
-- Using PostgreSQL 9.3

CREATE TABLE gateway
(
    id CHAR(16) PRIMARY KEY NOT NULL,
    owner BIGINT NOT NULL,
    activity INT NOT NULL,
    suffix VARCHAR(140)
);
CREATE TABLE userprofile
(
    id BIGINT PRIMARY KEY NOT NULL,
    name VARCHAR(128) NOT NULL,
    screenname VARCHAR(64) NOT NULL,
    profileimageurl VARCHAR(200) NOT NULL,
    profileimageurlhttps VARCHAR(200) NOT NULL,
    url VARCHAR(200)
);

CREATE UNIQUE INDEX gateway_unique_id ON gateway ( id );
CREATE INDEX gateway_owner ON gateway ( owner );
CREATE UNIQUE INDEX userprofile_unique_id ON userprofile ( id );
