CREATE TABLE simulacrum.api_request_log (
  id BIGSERIAL PRIMARY KEY,
  request_timestamp TIMESTAMPTZ NOT NULL,
  method VARCHAR(16) NOT NULL,
  path VARCHAR(512) NOT NULL,
  query_string TEXT,
  status INTEGER NOT NULL,
  duration_ms BIGINT NOT NULL,
  remote_address VARCHAR(128),
  user_agent VARCHAR(512),
  correlation_id VARCHAR(128),
  system_endpoint BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_api_request_log_timestamp
  ON simulacrum.api_request_log(request_timestamp DESC);
CREATE INDEX idx_api_request_log_method
  ON simulacrum.api_request_log(method);
CREATE INDEX idx_api_request_log_path
  ON simulacrum.api_request_log(path);
