package com.github.caoyue521.shopserver.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.CacheControl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 接口返回 Result
 *
 * @author caoyue
 */
@JsonPropertyOrder({"code", "message", "data"})
public class ApiResult<Data> {

  private Errno errno;

  private String message;

  @JsonInclude(Include.NON_NULL)
  private Data data;


  public ApiResult() {
    this(Errno.OK);
  }

  public ApiResult(Errno errno) {
    this(errno, errno.getMessage());
  }

  public ApiResult(Errno errno, String message) {
    this(errno, message, null);
  }

  public ApiResult(Data data) {
    this(Errno.OK, Errno.OK.getMessage(), data);
  }

  public ApiResult(Errno errno, Data data) {
    this(errno, errno.getMessage(), data);
  }

  public ApiResult(Errno errno, String message, Data data) {
    this.errno = errno;
    this.message = message;
    this.data = data;

    if (this.errno != Errno.OK) {
      setErrorHint();
    } else {
      clearErrorHint();
    }
  }

  public static HttpServletResponse getHttpServletResponse() {
    if (RequestContextHolder.getRequestAttributes() != null) {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      return (HttpServletResponse) request.getAttribute("response__");
    }
    return null;
  }

  private void setErrorHint() {
    if (RequestContextHolder.getRequestAttributes() != null) {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      String error = errno.getCode() + ":" + message;
      request.setAttribute("ApiResultError", error);
    }
  }

  private void clearErrorHint() {
    if (RequestContextHolder.getRequestAttributes() != null) {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

      HttpServletResponse response = (HttpServletResponse) request.getAttribute("response__");
      if (response != null) {
        response.setHeader("ApiResultError", "");
      }
      request.removeAttribute("ApiResultError");
    }
  }

  public static ApiResult wrap(ApiResult apiResult) {
    if (apiResult.errno != Errno.OK) {
      apiResult.setErrorHint();
    } else {
      apiResult.clearErrorHint();
    }
    return apiResult;
  }

  public static class AsException extends RuntimeException {
    ApiResult result;

    public AsException(ApiResult result) {
      this.result = result;
    }

    public ApiResult get() {
      return result;
    }
  }

  public RuntimeException toException() {
    return new AsException(this);
  }

  public static ApiResult ok() {
    return new ApiResult();
  }

  public static ApiResult ok(String msg) {
    return new ApiResult<>(Errno.OK, msg);
  }

  public static ApiResult badRequest() {
    return new ApiResult(Errno.BAD_REQUEST);
  }

  public static ApiResult badRequest(String msg) {
    return new ApiResult(Errno.BAD_REQUEST, msg);
  }

  public static ApiResult unAuthorized() {
    return new ApiResult(Errno.UNAUTHORIZED);
  }

  public static ApiResult forbidden() {
    return new ApiResult(Errno.FORBIDDEN);
  }

  public static ApiResult forbidden(String msg) {
    return new ApiResult(Errno.FORBIDDEN, msg);
  }

  public static ApiResult notFound() {
    return new ApiResult(Errno.NOT_FOUND);
  }

  public static ApiResult notFound(String msg) {
    return new ApiResult(Errno.NOT_FOUND, msg);
  }

  public static ApiResult notAccept(String msg) {
    return new ApiResult(Errno.NOT_ACCEPT, msg);
  }

  public static ApiResult unsupportedMediaType(String msg) {
    return new ApiResult(Errno.UNSUPPORTED_MEDIA_TYPE, msg);
  }

  public static ApiResult internalError() {
    return new ApiResult(Errno.INTERNAL_ERROR);
  }

  public static ApiResult internalError(String msg) {
    return new ApiResult(Errno.INTERNAL_ERROR, msg);
  }

  public static ApiResult serviceUnavailable(String msg) {
    return new ApiResult(Errno.SERVICE_UNAVAILABLE, msg);
  }

  public static ApiResult bindingResult(BindingResult bindingResult) {
    List<BadRequestError> errorList = CollectionUtils.emptyIfNull(bindingResult.getFieldErrors())
        .stream()
        .map(BadRequestError::new)
        .collect(Collectors.toList());
    return new ApiResult<>(Errno.BAD_REQUEST, errorList);
  }

  @JsonIgnore
  public boolean isOk() {
    return this.errno == Errno.OK;
  }

  public Errno getCode() {
    return errno;
  }

  @JsonGetter("code")
  public int getCodeValue() {
    return errno.getCode();
  }

  public void setCode(Errno errno) {
    this.errno = errno;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }

  public void setCacheControl(int second) {
    HttpServletResponse response = getHttpServletResponse();
    if (response == null) {
      return;
    }

    CacheControl cacheControl = CacheControl.maxAge(second, TimeUnit.SECONDS).cachePrivate();
    response.setHeader("Cache-Control", cacheControl.getHeaderValue());
  }

  /**
   * 参数校验错误返回
   */
  @lombok.Data
  private static class BadRequestError {
    String field;
    String message;

    BadRequestError(FieldError fieldError) {
      this.field = fieldError.getField();
      this.message = fieldError.getDefaultMessage();
    }
  }

  @Override
  public String toString() {
    return "ApiResult{" +
        "errno=" + errno.getCode() +
        ", message='" + message + '\'' +
        ", data=" + data +
        '}';
  }
}
