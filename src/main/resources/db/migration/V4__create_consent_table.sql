CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE simulacrum.consent (
  consent_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  account_id VARCHAR(255) NOT NULL REFERENCES simulacrum.account(account_id),
  status VARCHAR(30) NOT NULL,
  total_debit_limit NUMERIC(15,2),
  max_single_debit NUMERIC(15,2),
  currency CHAR(3) NOT NULL,
  purpose_code VARCHAR(35),
  creditor_system_id VARCHAR(255),
  granted_at TIMESTAMP NOT NULL,
  expires_at TIMESTAMP,
  revoked_at TIMESTAMP
);
