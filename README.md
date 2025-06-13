# Spring Boot 2.x 项目模板

基于 Spring Boot 2.7.18 的企业级应用模板，包含生产就绪配置和最佳实践。

## 环境要求

- **JDK**: 8-17 (推荐 JDK 17)
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Docker**: 20.10+ (测试容器支持)

## 项目结构

```plaintext
src/
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           ├── Application.java        # 主启动类
│   │           ├── config/                # 配置类
│   │           ├── controller/            # REST控制器
│   │           ├── service/               # 业务服务
│   │           ├── repository/            # 数据访问层
│   │           └── model/                 # 数据模型
│   └── resources
│       ├── application.yml                # 主配置文件
│       ├── banner.txt                     # 自定义启动banner
│       └── static/                        # 静态资源
└── test
    └── java                               # 测试代码

```

## 快速启动

```bash
 # 构建项目
mvn clean package

# 运行应用
java -jar target/SpringBoot2.x-1.0.0-SNAPSHOT.jar

# 开发模式
mvn spring-boot:run
```

## 关键特性

* ✅ 内嵌 Tomcat 服务器
* ✅ Actuator 健康监控端点
* ✅ Lombok 简化代码
* ✅ Testcontainers 集成测试
* ✅ Jacoco 代码覆盖率
* ✅ MySQL 8 驱动支持

## 提交信息规范

### 格式

```text
<类型>: <简短描述>
[可选正文]
```

### 示例

```text
chore: initial project setup

- Initialize Spring Boot 2.7.18 project
- Configure Maven with essential dependencies
- Add .gitignore with comprehensive exclusion rules
- Set up project structure and basic configuration
```

### 提交类型说明
| 类型         | 使用场景        |
|------------|-------------|
| `feat`     | 新增功能        |
| `fix`      | 修复缺陷        |
| `docs`     | 文档更新        |
| `style`    | 代码格式/样式调整   |
| `refactor` | 重构代码（不改变功能） |
| `test`     | 添加/修改测试用例   |
| `chore`    | 构建/配置变更     |
| `ci`       | CI/CD 流水线变更 |
| `perf`     | 性能优化        |
| `build`    | 影响构建系统的变更   |
| `revert`   | 撤销之前的提交     |

### 规则要求

1. 标题规范
    * 使用命令式现在时态 ("add" 而非 "added")
    * 首字母小写，无结尾标点
    * 长度不超过50字符
2. 正文格式
    * 使用项目符号 (-) 列出变更点
    * 每行描述一个独立变更
    * 按重要性降序排列

#### 对特定模块的修改可添加范围标识：

  ```plaintext
  feat(security): add OAuth2 support 
  ```

## 开发建议

### 热部署

```bash
   mvn spring-boot:run -Dspring-boot.run.fork=false
```

#### 测试容器配置

```yaml
# 文件位置: src/test/resources/application-test.yml
spring:
datasource:
url: jdbc:tc:mysql:8.0.33:///testdb
driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
```

### 健康检查

``` bash
# 访问端点:
curl http://localhost:8080/actuator/health

# 健康检查
curl http://localhost:8080/actuator/health

# 指标监控
curl http://localhost:8080/actuator/metrics
```

## 技术栈版本

| 组件                | 版本      |
|-------------------|---------|
| Spring Boot       | 2.7.18  |
| MySQL Connector/J | 8.0.33  |
| Lombok            | 1.18.30 |
| Testcontainers    | 1.19.3  |
| JaCoCo            | 0.8.10  |

许可证

``` plaintext
Apache License 2.0
```