-- 初始化数据库表结构
CREATE TABLE IF NOT EXISTS metrics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    host_id VARCHAR(255) NOT NULL,
    timestamp DATETIME NOT NULL,
    metric_type VARCHAR(50) NOT NULL,
    INDEX idx_host_timestamp (host_id, timestamp),
    INDEX idx_metric_type (metric_type)
);

CREATE TABLE IF NOT EXISTS metric_data (
    metric_id BIGINT NOT NULL,
    `key` VARCHAR(255) NOT NULL,
    `value` TEXT,
    FOREIGN KEY (metric_id) REFERENCES metrics(id) ON DELETE CASCADE,
    INDEX idx_metric_id (metric_id)
);