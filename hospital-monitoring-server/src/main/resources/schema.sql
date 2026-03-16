-- 初始化数据库表结构

-- 监控指标主表
-- 存储所有监控指标的基本信息
CREATE TABLE IF NOT EXISTS metrics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    host_id VARCHAR(255) NOT NULL COMMENT '主机标识符',
    timestamp DATETIME NOT NULL COMMENT '指标采集时间戳',
    metric_type VARCHAR(50) NOT NULL COMMENT '指标类型: system(系统指标), mysql(MySQL指标), tomcat(Tomcat指标), disk(磁盘指标), network(网络指标)',
    INDEX idx_host_timestamp (host_id, timestamp),
    INDEX idx_metric_type (metric_type)
) COMMENT='监控指标主表';

-- 监控指标数据表
-- 存储具体的指标键值对数据
CREATE TABLE IF NOT EXISTS metric_data (
    metric_id BIGINT NOT NULL COMMENT '关联metrics表的id',
    `key` VARCHAR(255) NOT NULL COMMENT '指标键名',
    `value` TEXT COMMENT '指标值',
    FOREIGN KEY (metric_id) REFERENCES metrics(id) ON DELETE CASCADE,
    INDEX idx_metric_id (metric_id)
) COMMENT='监控指标数据表';

-- 指标类型说明：
-- system: 系统指标 (cpu.usage, memory.total, memory.available, memory.usage.percent)
-- mysql: MySQL指标 (mysql.threads.connected, mysql.threads.running, mysql.questions.total, mysql.slow.queries, mysql.innodb.buffer.pool.read.requests, mysql.innodb.buffer.pool.hits, mysql.innodb.buffer.pool.hit.rate)
-- tomcat: Tomcat指标 (tomcat.threads.current, tomcat.threads.max, tomcat.threads.usage.percent, tomcat.requests.total, tomcat.requests.errors, tomcat.requests.processing.time, tomcat.requests.error.rate, tomcat.sessions.active)
-- disk: 磁盘指标 (disk.read.bytes, disk.write.bytes, disk.read.ops, disk.write.ops)
-- network: 网络指标 (network.received.bytes, network.sent.bytes, network.packets.received, network.packets.sent)