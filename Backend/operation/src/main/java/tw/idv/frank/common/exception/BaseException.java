package tw.idv.frank.common.exception;

import tw.idv.frank.common.constant.CommonCode;

public class BaseException extends RuntimeException {

    private CommonCode commonCode;
    private Object[] args;

    public BaseException(CommonCode commonCode, String message, Throwable e) {
        super(message, e);
        this.commonCode = commonCode;
    }

    public BaseException(CommonCode commonCode, String message) {
        super(message);
        this.commonCode = commonCode;
    }

    public BaseException(CommonCode commonCode, Throwable e) {
        super(e);
        this.commonCode = commonCode;
    }

    public BaseException(CommonCode commonCode) {
        super();
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return commonCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public BaseException setArgs(Object... args) {
        this.args = args;
        return this;
    }
}
