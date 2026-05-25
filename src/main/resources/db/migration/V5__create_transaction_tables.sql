CREATE TABLE simulacrum.card_transaction (
  card_transaction_id VARCHAR(255) PRIMARY KEY,
  authorization_code VARCHAR(35),
  card_scheme_id SMALLINT NOT NULL REFERENCES simulacrum.card_scheme(id),
  masked_pan CHAR(4),
  expiry_date VARCHAR(7),
  additional_card_data VARCHAR(70),
  card_status_id SMALLINT NOT NULL REFERENCES simulacrum.card_status(id)
);

CREATE TABLE simulacrum.merchant (
  merchant_id VARCHAR(255) PRIMARY KEY,
  merchant_name VARCHAR(70)
);

CREATE TABLE simulacrum.transaction_history (
  transaction_id VARCHAR(255) PRIMARY KEY,
  account_id VARCHAR(255) NOT NULL REFERENCES simulacrum.account(account_id),
  status_id SMALLINT NOT NULL REFERENCES simulacrum.transaction_status(id),
  bank_transaction_code_id SMALLINT NOT NULL REFERENCES simulacrum.bank_transaction_code(id),
  booking_date_time TIMESTAMP NOT NULL,
  value_date_time TIMESTAMP NOT NULL,
  charge_amount NUMERIC(15,2),
  charge_currency_id SMALLINT REFERENCES simulacrum.currency(currency_id),
  debtor_name VARCHAR(140),
  debtor_street VARCHAR(70),
  debtor_building VARCHAR(16),
  debtor_post_code VARCHAR(16),
  debtor_town VARCHAR(35),
  debtor_country CHAR(2),
  creditor_name VARCHAR(140),
  creditor_street VARCHAR(70),
  creditor_building VARCHAR(16),
  creditor_post_code VARCHAR(16),
  creditor_town VARCHAR(35),
  creditor_country CHAR(2),
  merchant_name VARCHAR(70),
  merchant_id VARCHAR(35),
  merchant_category_code_id SMALLINT REFERENCES simulacrum.merchant_category_code(id),
  debtor_account_name VARCHAR(70),
  debtor_account_scheme_id SMALLINT REFERENCES simulacrum.account_identification_code(id),
  debtor_account_ident VARCHAR(35),
  creditor_account_name VARCHAR(70),
  creditor_account_scheme_id SMALLINT REFERENCES simulacrum.account_identification_code(id),
  creditor_account_ident VARCHAR(35),
  remittance_unstructured VARCHAR(140),
  card_transaction_id VARCHAR(255) REFERENCES simulacrum.card_transaction(card_transaction_id)
);

CREATE TABLE simulacrum.terminal_merchant (
  terminal_id VARCHAR(255) NOT NULL,
  merchant_id VARCHAR(255) NOT NULL REFERENCES simulacrum.merchant(merchant_id),
  PRIMARY KEY (terminal_id, merchant_id)
);
