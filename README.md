# Hospital Monitoring System (Java/Spring Boot)

统一的医院内网监控平台，基于Spring Boot + MyBatis + MySQL + Redis实现。

## 🏗️ 系统架构

```
云服务器 (Spring Boot API)
    ↑↓ HTTPS/HTTP
医院内网代理 (Spring Boot Agent)
    ↓
内网监控目标 (Tomcat/MySQL/服务器)
```

## 🔧 技术栈

- **后端框架**: Spring Boot 3.x
- **ORM框架**: MyBatis-Plus
- **数据库**: MySQL (关系数据) + Redis (缓存/队列)
- **通信协议**: RESTful HTTP/HTTPS
- **安全认证**: JWT + Spring Security
- **部署方式**: Docker容器化

## 🚀 快速开始

### 云服务器端部署

```bash
# 克隆项目
git clone https://github.com/your-username/hospital-monitoring-java.git
cd hospital-monitoring-java

# 启动数据库和缓存
docker-compose up -d mysql redis

# 构建并运行应用
./mvnw spring-boot:run
# 或者构建jar包
./mvnw clean package
java -jar target/hospital-monitoring-1.0.0.jar
```

### 内网代理端部署

```bash
# 在内网可外连的服务器上
cd hospital-monitoring-agent
./mvnw spring-boot:run

# 配置环境变量
export SERVER_URL=https://your-cloud-server.com
export AGENT_NAME=hospital-ward-1
```

## 📊 监控指标

### 系统指标
- CPU使用率、内存使用率、磁盘使用率
- 网络I/O、系统负载、进程状态

### Tomcat指标  
- JVM堆内存、非堆内存使用
- 线程数、活跃会话数
- 请求统计、错误计数、处理时间

### MySQL指标
- 连接数、线程状态
- 查询统计、慢查询数
- 缓冲池命中率、运行时间

## 🔐 安全特性

- **JWT认证**: 代理注册获取访问令牌
- **HTTPS传输**: 数据加密传输
- **速率限制**: 防止API滥用
- **CORS配置**: 严格的跨域策略

## 📁 项目结构

```
hospital-monitoring-java/
├── hospital-monitoring-server/     # 云服务器监控中心
├── hospital-monitoring-agent/      # 内网代理节点  
├── docker-compose.yml             # 容器编排
└── README.md                      # 项目文档
```