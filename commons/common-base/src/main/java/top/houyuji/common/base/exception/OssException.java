package top.houyuji.common.base.exception;


public class OssException extends RuntimeException {
    public OssException() {
        super();
    }

    public OssException(String message) {
        super(message);
    }

    public OssException(String message, Throwable cause) {
        super(message, cause);
    }
}