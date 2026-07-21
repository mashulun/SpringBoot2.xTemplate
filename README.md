# Spring Boot 2.x 项目模板

基于 Spring Boot 2.7.18（2.x 系列最终稳定版）的企业级项目起步模板，预置了生产环境所需的基础配置、统一响应封装和全局异常处理。

## 环境要求

| 工具 | 最低版本 | 推荐版本 |
|------|----------|----------|
| JDK | 8 | 17 |
| Maven | 3.6 | 3.9+ |
| MySQL | 8.0 | 8.0+ |
| Redis | 6.0 | 7.0+（可选） |

## 快速开始

### 1. 克隆或下载模板

```bash
git clone https://github.com/mashulun/SpringBoot2.xTemplate.git my-project
cd my-project
```

### 2. 修改项目信息

编辑 `pom.xml`，替换为你的项目坐标：

```xml
<groupId>com.yourcompany</groupId>
<artifactId>your-project-name</artifactId>
<version>1.0.0-SNAPSHOT</version>
<name>Your Project Name</name>
<description>Your project description</description>
```

将 `org.example` 包名替换为你的包名（需同步修改 `src/` 目录结构和配置文件中的日志包路径）。

### 3. 配置环境变量

复制 `.env.example` 为 `.env`，填入实际值：

```bash
cp .env.example .env
```

```properties
DB_USERNAME=root
DB_PASSWORD=your_actual_password
JWT_SECRET=your_random_secret_key
```

### 4. 初始化数据库

```bash
mysql -u root -p -e "CREATE DATABASE your_db_name CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

修改 `application.yml` 中的数据库连接地址为你的库名。

### 5. 启动

```bash
# 构建
mvn clean package -DskipTests

# 运行
java -jar target/SpringBoot2.x-1.0.0-SNAPSHOT.jar

# 开发模式（热部署）
mvn spring-boot:run
```

## 项目结构

```plaintext
src/
├── main/java/org/example/
│   ├── Application.java                     # 启动类
│   ├── common/
│   │   └── ApiResponse.java                 # 统一 API 响应封装
│   ├── config/
│   │   └── GlobalExceptionHandler.java      # 全局异常处理器
│   ├── controller/                          # TODO: REST 控制器
│   ├── service/                             # TODO: 业务服务层
│   ├── repository/                          # TODO: 数据访问层
│   └── model/                               # TODO: 数据模型/实体
├── main/resources/
│   └── application.yml                      # 主配置文件
└── test/
    ├── java/org/example/
    │   └── ApplicationTests.java            # 测试基类
    └── resources/
        └── application-test.yml             # 测试环境配置（Testcontainers）
```

## 已包含的模板功能

### 统一响应封装 (`ApiResponse<T>`)

所有 API 返回统一格式，包含 `code`、`message`、`data` 三个字段：

```java
// 成功
return ApiResponse.success(data);
return ApiResponse.success();

// 失败
return ApiResponse.error(404, "资源不存在");
return ApiResponse.error(500, "服务器内部错误");
```

响应示例：

```json
{
  "code": 200,
  "message": "success",
  "data": { ... }
}
```

### 全局异常处理 (`GlobalExceptionHandler`)

基于 `@RestControllerAdvice`，自动捕获并格式化异常：

- 通用 `Exception` → 500 响应
- `MethodArgumentNotValidException` → 400 参数校验错误（返回具体字段错误信息）

错误响应示例：

```json
{
  "timestamp": "2026-07-20T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "参数校验失败",
  "path": "/api/users",
  "fieldErrors": [
    { "field": "email", "message": "邮箱格式不正确" }
  ]
}
```

### Actuator 健康监控

仅暴露安全端点，health 详情需认证后查看：

```bash
# 健康检查
curl http://localhost:8080/api/actuator/health

# 应用信息
curl http://localhost:8080/api/actuator/info

# 性能指标
curl http://localhost:8080/api/actuator/metrics
```

## 预置依赖

| 依赖 | 用途 |
|------|------|
| spring-boot-starter-web | Web 服务（内嵌 Tomcat） |
| spring-boot-starter-data-jpa | JPA/Hibernate 数据访问 |
| spring-boot-starter-data-redis | Redis 缓存 |
| spring-boot-starter-actuator | 健康监控端点 |
| spring-boot-starter-validation | 参数校验 (Bean Validation) |
| spring-boot-devtools | 开发热部署 |
| MySQL Connector/J 8.0.33 | MySQL 驱动 |
| Lombok 1.18.30 | 代码简化 |
| JUnit 5 + Testcontainers 1.19.3 | 集成测试 |
| AssertJ 3.24.2 | 流式断言 |
| DataFaker 2.0.2 | 测试数据生成 |
| JaCoCo 0.8.10 | 代码覆盖率 |

## 配置参考

### application.yml 关键配置

```yaml
spring:
  server:
    port: 8080                    # 服务端口
    servlet:
      context-path: /api          # API 基础路径
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: ${DB_USERNAME:root}        # 环境变量覆盖
    password: ${DB_PASSWORD:password}    # 环境变量覆盖
  jpa:
    hibernate:
      ddl-auto: validate          # 生产环境推荐 validate 或 none
  redis:
    host: localhost
    port: 6379

app:
  security:
    jwt-secret: ${JWT_SECRET:mySecretKey}  # 生产环境务必修改
```

### Hibernate ddl-auto 选项

| 值 | 说明 | 适用环境 |
|----|------|----------|
| `none` | 不做任何操作 | 生产（推荐） |
| `validate` | 仅校验 schema 一致性 | 生产/测试（推荐） |
| `update` | 自动更新 schema | 仅开发环境 |
| `create` | 每次启动重建 schema | 仅测试 |
| `create-drop` | 启动创建，关闭删除 | 仅单元测试 |

### 测试环境

测试配置 `application-test.yml` 使用 Testcontainers 自动启动 MySQL 容器，无需本地安装数据库：

```yaml
spring:
  datasource:
    url: jdbc:tc:mysql:8.0.33:///testdb
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
```

运行测试：

```bash
mvn test
```

## 提交规范

遵循 [Conventional Commits](https://www.conventionalcommits.org/) 规范：

```text
<type>(<scope>): <description>

[optional body]
```

### 类型

| 类型 | 说明 |
|------|------|
| `feat` | 新功能 |
| `fix` | 修复缺陷 |
| `docs` | 文档更新 |
| `style` | 代码格式调整 |
| `refactor` | 重构（不改变功能） |
| `test` | 测试相关 |
| `chore` | 构建/配置变更 |
| `perf` | 性能优化 |

### 示例

```text
feat(user): add user registration endpoint
fix(auth): resolve JWT token expiration issue
docs: update API documentation for v1.1
```

## Git 分支工作流

### 日常开发流程

```bash
# 1. 从主干切出功能分支
git checkout main
git pull
git checkout -b feat/模块名-功能描述

# 2. 开发并提交（可多次提交）
git add <修改的文件>
git commit -m "feat(user): add registration API"

# 3. 切回主干，快进合并（不产生额外 merge commit）
git checkout main
git merge --ff-only feat/模块名-功能描述

# 4. 删除已合并的分支
git branch -d feat/模块名-功能描述
```

### 修复缺陷

```bash
# 切出修复分支
git checkout -b fix/问题描述

# 修复并提交
git add <修改的文件>
git commit -m "fix(jpa): resolve lazy loading exception"

# 合并到主干
git checkout main
git merge --ff-only fix/问题描述

# 删除分支
git branch -d fix/问题描述
```

### 分支命名约定

| 前缀 | 用途 | 示例 |
|------|------|------|
| `feat/` | 新功能开发 | `feat/user-registration` |
| `fix/` | 缺陷修复 | `fix/jwt-expiration-check` |
| `refactor/` | 代码重构 | `refactor/api-response-wrapper` |
| `docs/` | 文档变更 | `docs/update-readme` |
| `test/` | 测试相关 | `test/global-exception-handler` |

### 注意事项

- 合并使用 `--ff-only` 快进模式，保持 git 历史线性整洁
- 功能分支完成后再合并，避免半成品代码进入主干
- 推送前确保本地主干已更新：`git pull --ff-only`

```bash
# 推送到远程
git push origin main

# 如果分支也需要推送到远程协作
git push -u origin feat/模块名-功能描述
```

## 扩展建议

基于此模板开发时，建议按以下顺序补充：

1. **Spring Security / JWT 认证** — 配置安全框架，保护 API 端点
2. **数据模型 + Repository** — 定义实体和 JPA Repository
3. **Service 层** — 实现业务逻辑，一个完整的 CRUD 示例
4. **API 文档** — 集成 SpringDoc (Swagger/OpenAPI)
5. **多环境配置** — 添加 `application-dev.yml` / `application-prod.yml`
6. **Docker 支持** — 添加 `Dockerfile` + `docker-compose.yml`
7. **CI/CD** — GitHub Actions 或 Jenkins Pipeline

## 技术栈版本

| 组件 | 版本 |
|------|------|
| Spring Boot | 2.7.18 |
| MySQL Connector/J | 8.0.33 |
| Lombok | 1.18.30 |
| Testcontainers | 1.19.3 |
| AssertJ | 3.24.2 |
| DataFaker | 2.0.2 |
| JaCoCo | 0.8.10 |

## 许可证

Apache License 2.0
