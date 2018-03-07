package com.tmser.tr.bjysdk.exception;

/**
 * <p>
 * 表示视频服务返回的错误消息。
 * </p>
 * 
 * <p>
 * {@link ServiceException}用于处理视频服务返回的错误消息。比如，用于身份验证的Access ID不存在， 则会抛出
 * {@link ServiceException}（严格上讲，会是该类的一个继承类。比如，OSSClient会抛出OSSException）。
 * 异常中包含了错误代码，用于让调用者进行特定的处理。
 * </p>
 * 
 * <p>
 * {@link ClientException}表示的则是在向视频服务发送请求时出现的错误，以及客户端无法处理返回结果。
 * 例如，在发送请求时网络连接不可用，则会抛出{@link ClientException}的异常。
 * </p>
 * 
 * <p>
 * 通常来讲，调用者只需要处理{@link ServiceException}。因为该异常表明请求被服务处理，但处理的结果表明
 * 存在错误。异常中包含了细节的信息，特别是错误代码，可以帮助调用者进行处理。
 * </p>
 */
public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = 430933593095358673L;

  private String errorMessage;
  private String errorCode;
  private String hostId;

  private String rawResponseError;

  /**
   * 构造新实例。
   */
  public ServiceException() {
    super();
  }

  /**
   * 用给定的异常信息构造新实例。
   * 
   * @param errorMessage
   *          异常信息。
   */
  public ServiceException(String errorCode) {
    super((String) null);
    this.errorCode = errorCode;
  }

  /**
   * 用表示异常原因的对象构造新实例。
   * 
   * @param cause
   *          异常原因。
   */
  public ServiceException(Throwable cause) {
    super(null, cause);
  }

  /**
   * 用异常消息和表示异常原因的对象构造新实例。
   * 
   * @param errorMessage
   *          异常信息。
   * @param cause
   *          异常原因。
   */
  public ServiceException(String errorCode, Throwable cause) {
    super(null, cause);
    this.errorCode = errorCode;
  }

  /**
   * 用异常消息和表示异常原因及其他信息的对象构造新实例。
   * 
   * @param errorMessage
   *          异常信息。
   * @param errorCode
   *          错误代码。
   * @param requestId
   *          Request ID。
   * @param hostId
   *          Host ID。
   */
  public ServiceException(String errorMessage, String errorCode, String requestId, String hostId) {
    this(errorMessage, errorCode, requestId, hostId, null);
  }

  /**
   * 用异常消息和表示异常原因及其他信息的对象构造新实例。
   * 
   * @param errorMessage
   *          异常信息。
   * @param errorCode
   *          错误代码。
   * @param requestId
   *          Request ID。
   * @param hostId
   *          Host ID。
   * @param cause
   *          异常原因。
   */
  public ServiceException(String errorMessage, String errorCode, String hostId,
      Throwable cause) {
    this(errorMessage, errorCode, hostId, null, cause);
  }

  /**
   * 用异常消息和表示异常原因及其他信息的对象构造新实例。
   * 
   * @param errorMessage
   *          异常信息。
   * @param errorCode
   *          错误代码。
   * @param requestId
   *          Request ID。
   * @param hostId
   *          Host ID。
   * @param rawResponseError
   *          OSS错误响应体。
   * @param cause
   *          异常原因。
   */
  public ServiceException(String errorMessage, String errorCode, String hostId,
      String rawResponseError, Throwable cause) {
    this(errorCode, cause);
    this.errorMessage = errorMessage;
    this.hostId = hostId;
    this.rawResponseError = rawResponseError;
  }

  /**
   * 返回异常信息。
   * 
   * @return 异常信息。
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * 返回错误代码的字符串表示。
   * 
   * @return 错误代码的字符串表示。
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * 返回Host标识。
   * 
   * @return Host标识。
   */
  public String getHostId() {
    return hostId;
  }

  /**
   * 返回OSS错误响应体的字符串表示。
   * 
   * @return OSS错误响应体的字符串表示
   */
  public String getRawResponseError() {
    return rawResponseError;
  }

  /**
   * 设置OSS错误响应体的字符串表示。
   * 
   * @param rawResponseError
   *          OSS错误响应体的字符串表示
   */
  public void setRawResponseError(String rawResponseError) {
    this.rawResponseError = rawResponseError;
  }

  private String formatRawResponseError() {
    if (rawResponseError == null || rawResponseError.equals("")) {
      return "";
    }
    return String.format("\n[ResponseError]:\n%s", this.rawResponseError);
  }

  @Override
  public String getMessage() {
    return getErrorMessage()
        + "\n[ErrorCode]: " + getErrorCode()
        + "\n[HostId]: " + getHostId()
        + formatRawResponseError();
  }
}
