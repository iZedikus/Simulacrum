CREATE TABLE IF NOT EXISTS simulacrum.account_status (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(32) UNIQUE NOT NULL
);
INSERT INTO simulacrum.account_status (id, code) VALUES
  (1, 'Enabled'), (2, 'Disabled'), (3, 'Deleted')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.account_type (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(32) UNIQUE NOT NULL
);
INSERT INTO simulacrum.account_type (id, code) VALUES
  (1, 'Personal'), (2, 'Business')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.financial_institution_scheme (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(16) UNIQUE NOT NULL
);
INSERT INTO simulacrum.financial_institution_scheme (id, code) VALUES
  (1, 'BIC'), (2, 'LEI')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.account_identification_scheme (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(16) UNIQUE NOT NULL
);
INSERT INTO simulacrum.account_identification_scheme (id, code) VALUES
  (1, 'BBAN'), (2, 'IBAN'), (3, 'PAN'), (4, 'Paym')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.credit_debit_indicator (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(16) UNIQUE NOT NULL
);
INSERT INTO simulacrum.credit_debit_indicator (id, code) VALUES
  (1, 'Credit'), (2, 'Debit')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.transaction_status (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(64) UNIQUE NOT NULL
);
INSERT INTO simulacrum.transaction_status (id, code) VALUES
  (1, 'AcceptedSettlementCompleted'),
  (2, 'AcceptedSettlementInProcess'),
  (3, 'AcceptedWithoutPosting'),
  (4, 'Pending'),
  (5, 'Rejected')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.bank_transaction_code (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(32) UNIQUE NOT NULL
);
INSERT INTO simulacrum.bank_transaction_code (id, code) VALUES
  (1, 'ObPayment')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.address_type (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(16) UNIQUE NOT NULL
);
INSERT INTO simulacrum.address_type (id, code) VALUES
  (1, 'Business'), (2, 'Residential')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.card_status (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(16) UNIQUE NOT NULL
);
INSERT INTO simulacrum.card_status (id, code) VALUES
  (1, 'Active'), (2, 'Expired'), (3, 'Blocked')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.card_scheme (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(16) UNIQUE NOT NULL
);
INSERT INTO simulacrum.card_scheme (id, code) VALUES
  (1, 'VISA'), (2, 'MasterCard'), (3, 'MIR')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.currency (
  currency_id SMALLINT PRIMARY KEY,
  code CHAR(3) UNIQUE NOT NULL
);
INSERT INTO simulacrum.currency (currency_id, code) VALUES
  (643, 'RUB'), (840, 'USD'), (978, 'EUR')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.participant_identification_scheme (
  id SMALLINT PRIMARY KEY,
  code VARCHAR(16) UNIQUE NOT NULL
);
INSERT INTO simulacrum.participant_identification_scheme (id, code) VALUES
  (1, 'BBAN'), (2, 'IBAN'), (3, 'PAN'), (4, 'BIC')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS simulacrum.merchant_category_code (
  id SMALLINT PRIMARY KEY,
  code INTEGER UNIQUE NOT NULL
);
INSERT INTO simulacrum.merchant_category_code (id, code) VALUES
  (1, 5912), (2, 5813), (3, 5993)
ON CONFLICT DO NOTHING;
