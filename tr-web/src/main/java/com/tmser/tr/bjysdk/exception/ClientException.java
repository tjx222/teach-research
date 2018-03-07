package com.tmser.tr.bjysdk.exception;

/**
 * <p>
 * 表示尝试访问视频服务时遇到的异常。
 * </p>
 * 
 * <p>
 * {@link ClientException}表示的则是在向视频服务发送请求时出现的错误，以及客户端无法处理返回结果。
 * 例如，在发送请求时网络连接不可用，则会抛出{@link ClientException}的异常。
 * </p>
 * 
 * <p>
 * {@link com.mainbo.vsdk.ServiceException}用于处理视频服务返回的错误消息。比如，用于身份验证的Access
 * ID不存在， 则会抛出{@link com.mainbo.vsdk.ServiceException}
 * （严格上讲，会是该类的一个继承类。比如，OSSClient会抛出OSSException）。 异常中包含了错误代码，用于让调用者进行特定的处理。
 * </p>
 *
 * <p>
 * 通常来讲，调用者只需要处理{@link ServiceException}。因为该异常表明请求被服务处理，但处理的结果表明
 * 存在错误。异常中包含了细节的信息，特别是错误代码，可以帮助调用者进行处理。
 * </p>
 * 
 */
public class ClientException extends RuntimeException {

  private static final long serialVersionUID = 1870835486798448798L;

  private String errorMessage;
  private String errorCode;

  /**
   * 构造新实例。
   */
  public ClientException() {
    super();
  }

  /**
   * 用给定的异常信息构造新实例。
   * 
   * @param errorMessage
   *          异常信息。
   */
  public ClientException(String errorMessage) {
    this(errorMessage, ClientErrorCode.UNKNOWN, null);
  }

  /**
   * 用表示异常原因的对象构造新实例。
   * 
   * @param cause
   *          异常原因。
   */
  public ClientException(Throwable cause) {
    this(null, cause);
  }

  /**
   * 用异常消息和表示异常原因的对象构造新实例。
   * 
   * @param errorMessage
   *          异常信息。
   * @param cause
   *          异常原因。
   */
  public ClientException(String errorMessage, Throwable cause) {
    super(null, cause);
    this.errorMessage = errorMessage;
    this.errorCode = ClientErrorCode.UNKNOWN;
  }

  /**
   * 用异常消息构造新实例。
   * 
   * @param errorMessage
   *          异常信息。
   * @param errorCode
   *          错误码。
   */
  public ClientException(String errorMessage, String errorCode) {
    this(errorMessage, errorCode, null);
  }

  /**
   * 用异常消息和表示异常原因的对象构造新实例。
   * 
   * @param errorMessage
   *          异常信息。
   * @param errorCode
   *          错误码。
   * @param cause
   *          异常原因。
   */
  public ClientException(String errorMessage, String errorCode, Throwable cause) {
    this(errorMessage, cause);
    this.errorCode = errorCode;
  }

  /**
   * 获取异常信息。
   * 
   * @return 异常信息。
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * 获取异常的错误码
   * 
   * @return 异常错误码
   */
  public String getErrorCode() {
    return errorCode;
  }

  @Override
  public String getMessage() {
    return getErrorMessage() + "\n[ErrorCode]: " + errorCode != null ? errorCode : "";
  }
}
