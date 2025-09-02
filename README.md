# Flexi Admin

一个基于 Spring Boot 3.x 的现代化企业级管理系统框架，采用模块化架构设计，提供灵活可扩展的管理后台解决方案。

## 🚀 项目特性

- **现代化技术栈**: 基于 Spring Boot 3.5.3 + Java 21
- **模块化架构**: 清晰的模块分离，便于维护和扩展
- **权限管理**: 集成 Sa-Token 框架，提供完善的权限控制
- **数据持久化**: 使用 MyBatis-Plus 3.5.12，支持多种数据库
- **缓存支持**: 内置缓存机制，提升系统性能
- **API 文档**: 集成 SpringDoc，自动生成 API 文档
- **容器化支持**: 提供 Docker 部署配置

## 🏗️ 项目架构

```
flexi-admin/
├── boot/                    # 启动模块 - 应用入口
├── commons/                 # 公共模块
│   ├── common-api/         # API 接口定义
│   ├── common-base/        # 基础工具类
│   ├── common-cache/       # 缓存管理
│   ├── common-mybatis/     # 数据访问层
│   ├── common-query/       # 查询构建器
│   └── common-sa-token/    # 权限认证
├── modules/                 # 业务模块
│   ├── sys-modules/        # 系统管理模块
│   └── biz-modules/        # 业务功能模块
├── docs/                   # 项目文档
│   └── sql/               # 数据库脚本
├── docker/                 # Docker 配置
└── pom.xml                 # Maven 配置
```

## 🛠️ 技术栈

### 核心框架

- **Spring Boot**: 3.5.3
- **Java**: 21
- **Maven**: 3.x

### 数据层

- **MyBatis-Plus**: 3.5.12
- **MySQL**: 9.3.0
- **连接池**: HikariCP

### 安全认证

- **Sa-Token**: 1.44.0
- **JWT**: 4.4.0

### 工具库

- **Hutool**: 5.8.27
- **Guava**: 33.4.8-jre
- **Lombok**: 1.18.38
- **MapStruct**: 1.5.5.Final

### 文档与测试

- **SpringDoc**: 2.5.0
- **JUnit 5**: 5.10.3

## 📋 环境要求

- **JDK**: 21 或更高版本
- **Maven**: 3.6 或更高版本
- **MySQL**: 8.0 或更高版本
- **内存**: 建议 4GB 以上

## 🚀 快速开始

### 1. 克隆项目

```bash
git clone <repository-url>
cd flexi-admin
```

### 2. 配置数据库

1. 创建 MySQL 数据库
2. 执行 `docs/sql/` 目录下的数据库脚本
3. 修改 `boot/src/main/resources/application.yml` 中的数据库连接配置

### 3. 编译项目

```bash
mvn clean compile
```

### 4. 启动应用

```bash
cd boot
mvn spring-boot:run
```

应用将在 `http://localhost:8080` 启动

### 5. 访问系统

- **API 文档**: `http://localhost:8080/swagger-ui.html`
- **健康检查**: `http://localhost:8080/actuator/health`

## 📦 模块说明

### Boot 模块

系统启动入口，包含 Web 配置、数据库连接、模块依赖等。

### Commons 模块

- **common-api**: 定义系统 API 接口和数据结构
- **common-base**: 提供基础工具类和通用功能
- **common-cache**: 缓存管理，支持多种缓存策略
- **common-mybatis**: MyBatis-Plus 配置和扩展
- **common-query**: 动态查询构建器
- **common-sa-token**: 权限认证和会话管理

### Modules 模块

- **sys-modules**: 系统管理功能，如用户、角色、权限等
- **biz-modules**: 业务功能模块，根据具体业务需求扩展

## 🔧 配置说明

### 数据库配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/flexi_admin?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 缓存配置

```yaml
spring:
  cache:
    type: redis
    redis:
      host: localhost
      port: 6379
      database: 0
```

## 🐳 Docker 部署

项目提供 Docker 支持，可以使用以下命令构建和运行：

```bash
# 构建镜像
docker build -t flexi-admin .

# 运行容器
docker run -d -p 8080:8080 --name flexi-admin flexi-admin
```

## 📚 开发指南

### 添加新模块

1. 在 `modules/` 目录下创建新的模块目录
2. 在模块目录下创建标准的 Maven 项目结构
3. 在根 `pom.xml` 中添加模块依赖
4. 在 `boot/pom.xml` 中添加模块依赖

### 代码规范

- 遵循 Java 编码规范
- 使用 Lombok 减少样板代码
- 使用 MapStruct 进行对象映射
- 添加必要的注释和文档

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 [MIT License](LICENSE) 许可证。

## 📞 联系方式

- **项目维护者**: [Your Name]
- **邮箱**: [your.email@example.com]
- **项目地址**: [GitHub Repository URL]

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者和开源社区。

---

**注意**: 这是一个开发中的项目，API 和功能可能会发生变化。建议在生产环境使用前进行充分测试。
