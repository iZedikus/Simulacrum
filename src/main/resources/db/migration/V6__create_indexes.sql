CREATE INDEX idx_transaction_account_id
  ON simulacrum."transaction"(account_id);
CREATE INDEX idx_transaction_booking_dt
  ON simulacrum."transaction"(booking_date_time DESC);
CREATE INDEX idx_transaction_status
  ON simulacrum."transaction"(status_id);
CREATE INDEX idx_consent_account_id
  ON simulacrum.consent(account_id);
