CREATE TABLE simulacrum.card_transaction (
  card_transaction_id VARCHAR(255) PRIMARY KEY,
  authorization_code VARCHAR(35),
  card_scheme_id SMALLINT NOT NULL REFERENCES simulacrum.card_scheme(id),
  masked_pan CHAR(4),
  expiry_date VARCHAR(7),
  additional_card_data VARCHAR(70),
  card_status_id SMALLINT NOT NULL REFERENCES simulacrum.card_status(id)
);

CREATE TABLE simulacrum.address (
  adress_id VARCHAR(255) PRIMARY KEY,
  adress_type_id SMALLINT NOT NULL REFERENCES simulacrum.address_type(id),
  street_name VARCHAR(70),
  building_number VARCHAR(16),
  post_code VARCHAR(16),
  town_name VARCHAR(35),
  country CHAR(2)
);

CREATE TABLE simulacrum.finansial_institution (
  financial_institution_id VARCHAR(255) PRIMARY KEY,
  scheme_name_id SMALLINT NOT NULL REFERENCES simulacrum.financial_institution_scheme(id),
  identification VARCHAR(35),
  name VARCHAR(140)
);

CREATE TABLE simulacrum.bank_account (
  bank_account_id VARCHAR(255) PRIMARY KEY,
  scheme_name_id SMALLINT NOT NULL REFERENCES simulacrum.account_identification_scheme(id),
  identification VARCHAR(35),
  account_name VARCHAR(70)
);

CREATE TABLE simulacrum.merchant (
  merchant_id VARCHAR(255) PRIMARY KEY,
  merchant_name VARCHAR(70),
  merchant_category_code SMALLINT REFERENCES simulacrum.merchant_category_code(id),
  acquirer_id VARCHAR(255)
);

CREATE TABLE simulacrum.participant (
  participant_id VARCHAR(255) PRIMARY KEY,
  adress_id VARCHAR(255) REFERENCES simulacrum.address(adress_id),
  merchant_id VARCHAR(255) REFERENCES simulacrum.merchant(merchant_id),
  name VARCHAR(140)
);

CREATE TABLE simulacrum.participant_identification (
  participant_identification_id VARCHAR(255) PRIMARY KEY,
  participant_id VARCHAR(255) NOT NULL REFERENCES simulacrum.participant(participant_id),
  scheme_name_id SMALLINT NOT NULL REFERENCES simulacrum.participant_identification_scheme(id),
  identification VARCHAR(35)
);

CREATE TABLE simulacrum."transaction" (
  transaction_id VARCHAR(255) PRIMARY KEY,
  account_id VARCHAR(255) NOT NULL REFERENCES simulacrum.account(account_id),
  currency_id SMALLINT REFERENCES simulacrum.currency(currency_id),
  charge_currency_id SMALLINT REFERENCES simulacrum.currency(currency_id),
  instructed_currency_id SMALLINT REFERENCES simulacrum.currency(currency_id),
  debit_credit_indicator_id SMALLINT REFERENCES simulacrum.credit_debit_indicator(id),
  status_id SMALLINT REFERENCES simulacrum.transaction_status(id),
  bank_transaction_code_id SMALLINT REFERENCES simulacrum.bank_transaction_code(id),
  card_transaction_id VARCHAR(255) REFERENCES simulacrum.card_transaction(card_transaction_id),
  debtor_agent_id VARCHAR(255) REFERENCES simulacrum.finansial_institution(financial_institution_id),
  creditor_agent_id VARCHAR(255) REFERENCES simulacrum.finansial_institution(financial_institution_id),
  debtor_id VARCHAR(255) REFERENCES simulacrum.participant(participant_id),
  creditor_id VARCHAR(255) REFERENCES simulacrum.participant(participant_id),
  debtor_account_id VARCHAR(255) REFERENCES simulacrum.bank_account(bank_account_id),
  creditor_account_id VARCHAR(255) REFERENCES simulacrum.bank_account(bank_account_id),
  status VARCHAR(64) NOT NULL,
  bank_transaction_code VARCHAR(32) NOT NULL,
  booking_date_time TIMESTAMP NOT NULL,
  value_date_time TIMESTAMP NOT NULL,
  charge_amount NUMERIC(15,2),
  charge_currency CHAR(3),
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
  debtor_account_name VARCHAR(70),
  debtor_account_scheme VARCHAR(16),
  debtor_account_identification VARCHAR(35),
  creditor_account_name VARCHAR(70),
  creditor_account_scheme VARCHAR(16),
  creditor_account_identification VARCHAR(35),
  remittance_unstructured VARCHAR(140)
);

CREATE TABLE simulacrum.terminal_merchant (
  merchantmerchant_id VARCHAR(255) NOT NULL REFERENCES simulacrum.merchant(merchant_id),
  terminalterminal_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (merchantmerchant_id, terminalterminal_id)
);
