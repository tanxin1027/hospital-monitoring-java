-- 创建监控数据库
CREATE DATABASE IF NOT EXISTS hospital_monitoring CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用监控数据库
USE hospital_monitoring;

-- 创建监控用户并授权
CREATE USER IF NOT EXISTS 'monitor'@'%' IDENTIFIED BY 'monitor123';
GRANT ALL PRIVILEGES ON hospital_monitoring.* TO 'monitor'@'%';
FLUSH PRIVILEGES;

-- 手动创建表结构（如果需要）
-- CREATE TABLE metrics (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     host_id VARCHAR(255) NOT NULL,
--     timestamp DATETIME NOT NULL,
--     metric_type VARCHAR(50) NOT NULL,
--     INDEX idx_host_timestamp (host_id, timestamp),
--     INDEX idx_metric_type (metric_type)
-- );

-- CREATE TABLE metric_data (
--     metric_id BIGINT NOT NULL,
--     `key` VARCHAR(255) NOT NULL,
--     `value` TEXT,
--     FOREIGN KEY (metric_id) REFERENCES metrics(id) ON DELETE CASCADE,
--     INDEX idx_metric_id (metric_id)
-- );