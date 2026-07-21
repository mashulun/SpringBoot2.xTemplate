# Changelog

本项目的所有重要变更记录。格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/)，版本号遵循 [语义化版本](https://semver.org/lang/zh-CN/) 规范。

## [1.0.0] - 2026-07-21

### 新增
- 统一响应包装器 `ApiResponse`，所有接口返回格式一致
- 响应状态码枚举 `ResponseCode`，定义常用状态码（200/400/401/403/404/422/500/503）
- 全局异常处理器 `GlobalExceptionHandler`，统一捕获并格式化异常
- 参数校验集成，配合 `@Valid` 注解自动校验请求参数
- `.env.example` 环境配置模板
- `application.yml` 基础配置

### 文档
- 完整 README，包含模板使用说明、配置参考、项目结构
- MIT LICENSE

### 基础设施
- GitHub Actions CI 配置（自动编译验证）
- Issue 模板（Bug 报告 + 功能建议）
- PR 模板（改动说明 + 检查清单）
- README 版本/License/CI 状态徽章

[1.0.0]: https://github.com/mashulun/SpringBoot2.xTemplate/releases/tag/v1.0.0
