
-- Using PostgreSQL 9.3

CREATE TABLE gateway
(
    id VARCHAR(64) PRIMARY KEY NOT NULL,
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
    url VARCHAR(200),
    accesstoken VARCHAR(96) NOT NULL,
    accesstokensecret VARCHAR(96) NOT NULL
);
CREATE UNIQUE INDEX gw_unique_id ON gateway ( id );
CREATE INDEX gateway_owner_index ON gateway ( owner );
CREATE UNIQUE INDEX unique_id ON userprofile ( id );
