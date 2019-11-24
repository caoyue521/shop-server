package com.github.caoyue521.shopserver.model;

import lombok.Getter;

/**
 * 错误码枚举
 *
 * @author caoyue
 */
public enum Errno {

  /* 基础错误码 */
  OK(0, "操作成功"),
  BAD_REQUEST(400, "参数错误"),
  UNAUTHORIZED(401, "您没有登录"),
  FORBIDDEN(403, "您没有权限"),
  NOT_FOUND(404, "您请求的资源不存在"),
  NOT_ACCEPT(406, "请求错误"),
  UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
  INTERNAL_ERROR(500, "服务异常，请联系管理员"),
  SERVICE_UNAVAILABLE(503, "服务异常，请联系管理员"),
  ;

  @Getter
  private int    code;
  @Getter
  private String message;

  Errno(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
