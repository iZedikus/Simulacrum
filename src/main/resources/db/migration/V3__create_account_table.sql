CREATE TABLE simulacrum.account (
  account_id VARCHAR(255) PRIMARY KEY,
  currency_id SMALLINT NOT NULL REFERENCES simulacrum.currency(currency_id),
  status_id SMALLINT NOT NULL REFERENCES simulacrum.account_status(id),
  account_type_id SMALLINT NOT NULL REFERENCES simulacrum.account_type(id),
  status_update_datetime TIMESTAMP NOT NULL,
  account_description VARCHAR(255)
);
