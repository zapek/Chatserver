--
-- Database creation
--
-- Do not touch this file if unnecessary (even comments) as this will trigger
-- a flyway migration.
--
-- See https://dev.mysql.com/doc/refman/8.0/en/data-types.html for the data types
--
-- Do not put indexes on identifiers and fingerprints as they have a random
-- distribution that don't play well with b-trees.
--
CREATE TABLE profiles
(
    id                  SERIAL,
    pgp_identifier      BIGINT    NOT NULL,
    location_identifier BINARY(16)                                                DEFAULT NULL,
    trust               ENUM ('UNKNOWN', 'NEVER', 'MARGINAL', 'FULL', 'ULTIMATE') DEFAULT 'UNKNOWN',
    created             TIMESTAMP NOT NULL
);

CREATE TABLE prefs
(
    id          SERIAL,
    identity    BINARY(16)       DEFAULT NULL,
    lobby_id    BIGINT           DEFAULT NULL,
    password    VARCHAR(12)      DEFAULT NULL
);