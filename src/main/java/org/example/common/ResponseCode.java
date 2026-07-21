package org.example.common;

/**
 * 统一响应状态码枚举
 * <p>
 * 所有接口的响应码应使用此枚举，避免硬编码魔法数字。
 * <p>
 * 编码规则：
 * <ul>
 *   <li>2xx — 成功</li>
 *   <li>4xx — 客户端错误</li>
 *   <li>5xx — 服务端错误</li>
 * </ul>
 */
public enum ResponseCode {

    // ========== 成功 ==========
    SUCCESS(200, "操作成功"),

    // ========== 客户端错误 ==========
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    FORBIDDEN(403, "没有操作权限"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    PARAM_ERROR(422, "参数校验失败"),

    // ========== 服务端错误 ==========
    INTERNAL_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
