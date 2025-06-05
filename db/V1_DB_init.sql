--  schema: public
CREATE TABLE event (
    id BIGSERIAL PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL,
    event_number SMALLINT NOT NULL CHECK (event_number >= 0 AND event_number <= 255)
);


--  schema: test
CREATE SCHEMA IF NOT EXISTS test;
CREATE TABLE test.event (
    id BIGSERIAL PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL,
    event_number SMALLINT NOT NULL CHECK (event_number >= 0 AND event_number <= 255)
);