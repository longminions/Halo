CREATE TABLE IF NOT EXISTS system_log (
      id SERIAL PRIMARY KEY,
      service_name VARCHAR(100) NOT NULL,
      step_name VARCHAR(100) NOT NULL,
      status VARCHAR(50) NOT NULL,
      write_count INT DEFAULT 0,
      start_time TIMESTAMP NOT NULL,
      end_time TIMESTAMP NOT NULL,
      message TEXT,

      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE system_log ADD COLUMN exception TEXT;