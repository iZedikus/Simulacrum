ALTER TABLE simulacrum."transaction"
    DROP CONSTRAINT IF EXISTS transaction_account_id_fkey,
    ALTER COLUMN account_id DROP NOT NULL,
    ADD COLUMN consent_id VARCHAR(255),
    ADD COLUMN failure_code VARCHAR(64),
    ADD COLUMN failure_message VARCHAR(255);

CREATE INDEX idx_transaction_consent_id
    ON simulacrum."transaction" (consent_id);
